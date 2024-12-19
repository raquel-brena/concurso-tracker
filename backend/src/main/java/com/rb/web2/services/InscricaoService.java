package com.rb.web2.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.InscricaoRequestDTO;
import com.rb.web2.domain.inscricao.dto.InscricaoResponseDTO;
import com.rb.web2.domain.inscricao.dto.UpdateInscricaoDTO;
import com.rb.web2.domain.inscricao.mapper.InscricaoMapper;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.repositories.InscricaoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class InscricaoService {

  private final InscricaoRepository inscricaoRepository;
  private final UserService userService;
  private final VagaService vagaService;

  public InscricaoService(
      InscricaoRepository inscricaoRepository,
      UserService userService,
      CriterioAvaliacaoService criterioAvaliacaoService,
      VagaService vagaService) {
    this.inscricaoRepository = inscricaoRepository;
    this.userService = userService;
    this.vagaService = vagaService;
  }
  
  private void verificarPermissaoDeCriacaoOuAlteracao() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(login);
    }

  public Inscricao create(InscricaoRequestDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao();

    User candidato = userService.getUserById(dto.candidatoId());
    Vaga vaga = vagaService.buscarVagaPorId(dto.vagaId());

    Inscricao inscricao = InscricaoMapper
        .toEntity(dto, candidato, vaga);

    return inscricaoRepository.save(inscricao);
  }

  public Inscricao buscarInscricaoPorId(String id) {
    return inscricaoRepository.findById(id).orElseThrow(() 
    -> new NotFoundException("Inscrição com id " + id + " não encontrada."));
  }
 
  public InscricaoResponseDTO getResponseInscricaoDTOById(String id) {
    return inscricaoRepository.findById(id)
        .map(InscricaoMapper::toDTO)
        .orElseThrow(() -> new NotFoundException("Inscrição com id " + id + " não encontrada."));
  }

  public List<InscricaoResponseDTO> getAllInscricoes() {
    List<Inscricao> inscricoes = inscricaoRepository.findAllByDeletadoEmNull();
    List<InscricaoResponseDTO> applications = inscricoes.stream()
        .map(inscricao -> new InscricaoResponseDTO(
            inscricao.getId(),
            inscricao.getCandidato().getId(),
            inscricao.getVaga().getId(),
            inscricao.getDeletadoEm()))
        .toList();
    return applications;
  }

  public List<InscricaoResponseDTO> getAllInscricoesPorCandidato(String candidatoId) {
    List<Inscricao> inscricoes = inscricaoRepository.findAllByCandidatoId(candidatoId);
    List<InscricaoResponseDTO> applications = inscricoes.stream()
        .map(inscricao -> new InscricaoResponseDTO(
            inscricao.getId(),
            inscricao.getCandidato().getId(),
            inscricao.getVaga().getId(),
            inscricao.getDeletadoEm()))
        .toList();
    return applications;
  }

  public List<InscricaoResponseDTO> getAllInscricoesPorVaga(Long vagaId) {
    List<Inscricao> inscricoes = inscricaoRepository.findAllByVagaId(vagaId);
    List<InscricaoResponseDTO> applications = inscricoes.stream()
        .map(inscricao -> new InscricaoResponseDTO(
            inscricao.getId(),
            inscricao.getCandidato().getId(),
            inscricao.getVaga().getId(),
            inscricao.getDeletadoEm()))
        .toList();
    return applications;
  }

  public Inscricao atualizarInscricao(String id, UpdateInscricaoDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao();
    Inscricao existingInscricao = this.buscarInscricaoPorId(id);

    if (dto.vagaId() != null) {
      existingInscricao.setVaga(vagaService.buscarVagaPorId(dto.vagaId()));
    }

    // if (dto.ativo() != null) {
    //     existingInscricao.setAtivo(dto.ativo());
    // }

    // if (dto.avaliacoes() != null && !dto.avaliacoes().isEmpty()) {
    //     List<CriterioAvaliacao> criterios = criterioAvaliacaoService.buscarCriteriosPorIds(dto.avaliacoes());
    //     existingInscricao.setAvaliacoes(criterios);
    // }

    // if (dto.avaliacoes() != null && !dto.avaliacoes().isEmpty()) {
    //   List<CriterioAvaliacao> criterios = criterioAvaliacaoService.buscarCriteriosPorIds(dto.avaliacoes());
    //   existingInscricao.setAvaliacoes(criterios);
    // }

    return inscricaoRepository.save(existingInscricao);
  }

  public void softDelete(String id) {
    verificarPermissaoDeCriacaoOuAlteracao();
    Inscricao inscricao = this.buscarInscricaoPorId(id);
    inscricao.setDeletadoEm(LocalDateTime.now());
    inscricaoRepository.save(inscricao);
  }

  public List<Inscricao> findByProcesso(String processoId) {
    return inscricaoRepository.findByVagaProcessoSeletivoId(processoId);
  }

  public boolean existsByInscricaoId(String inscricaoId) {
    return inscricaoRepository.existsById(inscricaoId);
  }

  public List<InscricaoResponseDTO> getAllInscricoesPorProcessoSeletivo(String processoId) {
    List<Inscricao> inscricoes = inscricaoRepository.findByVagaProcessoSeletivoId(processoId);
    List<InscricaoResponseDTO> applications = inscricoes.stream()
        .map(inscricao -> new InscricaoResponseDTO(
            inscricao.getId(),
            inscricao.getCandidato().getId(),
            inscricao.getVaga().getId(),
            inscricao.getDeletadoEm()))
        .toList();
    return applications;
  }
}
