package com.rb.web2.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.InscricaoRequestDTO;
import com.rb.web2.domain.inscricao.dto.InscricaoResponseDTO;
import com.rb.web2.domain.inscricao.dto.UpdateInscricaoDTO;
import com.rb.web2.domain.inscricao.mapper.InscricaoMapper;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.InscricaoRepository;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.repositories.VagaRepository;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class InscricaoService {

  private AuthorizationUtil authorizationUtil;
  private final InscricaoRepository inscricaoRepository;
  private final UserService userService;
  private final VagaService vagaService;
  private final ProcessoSeletivoRepository processoRepository;
  private VagaRepository vagaRepository;
  private UserRepository userRepository;

  public InscricaoService(
      InscricaoRepository inscricaoRepository,
      UserService userService,
      CriterioAvaliacaoService criterioAvaliacaoService,
      VagaService vagaService, VagaRepository vagaRepository, ProcessoSeletivoRepository processoRepository, UserRepository userRepository) {
    this.inscricaoRepository = inscricaoRepository;
    this.userService = userService;
    this.vagaService = vagaService;
    this.vagaRepository = vagaRepository;
    this.processoRepository = processoRepository;
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    this.authorizationUtil = new AuthorizationUtil(userService);
  }

  // @TODO: Fazer a verificação de permissão de Visualização

  private void verificarPermissaoDeCriacaoOuAlteracao(String userId) {
    authorizationUtil.<String>verificarPermissaoOuComissao(
        userId,
        "EDIT_INSCRICAO",
        id -> userService.getUserById(id),
        (entity, user) -> {
          User usuario = (User) entity;
          return usuario.equals(user);
        });
  }

  private void verificarPermissaoDeLeitura(String inscricaoId) {
    authorizationUtil.<String>verificarPermissaoOuComissao(
        inscricaoId,
        "VIEW_INSCRICAO",
        id -> inscricaoRepository.findById(id),
        (entity, user) -> {
          Inscricao inscricao = (Inscricao) entity;
          var isComissao = inscricao.getVaga().getProcessoSeletivo().getComissaoOrganizadora().contains(user);
          var isParticipante = inscricao.getCandidato().equals(user);
          
          return isComissao || isParticipante;
        });
  }

  private void verificarPermissaoDeLeituraGeralPorCandidato(String candidatoId) {
    authorizationUtil.<String>verificarPermissaoOuComissao(
        candidatoId,
        "VIEW_INSCRICOES",
        id -> userRepository.findById(id),
        (entity, user) -> {
          User usuario = (User) entity;
          var isCandidato = usuario.equals(user);
          return isCandidato;
        });
  }

  private void verificarPermissaoDeLeituraGeralPorProcesso(String processoId) {
    authorizationUtil.<String>verificarPermissaoOuComissao(
        processoId,
        "VIEW_INSCRICOES",
        id -> processoRepository.findById(id),
        (entity, user) -> {
          ProcessoSeletivo processo = (ProcessoSeletivo) entity;
          var isComissao = processo.getComissaoOrganizadora().contains(user);
          return isComissao;
        });
  }

  private void verificarPermissaoDeLeituraGeralPorVaga(Long vagaId) {
    authorizationUtil.<Long>verificarPermissaoOuComissao(
        vagaId,
        "VIEW_INSCRICOES",
        id -> vagaRepository.findById(id),
        (entity, user) -> {
          Vaga vaga = (Vaga) entity;
          var isComissao = vaga.getProcessoSeletivo().getComissaoOrganizadora().contains(user);
          return isComissao;
        });
  }

  public InscricaoResponseDTO create(InscricaoRequestDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao(dto.candidatoId());

    if (this.inscricaoRepository.findByCandidatoIdAndVagaId(dto.candidatoId(), dto.vagaId()).orElse(null) != null) {
      throw new BadRequestException("Inscrição já realizada.");
    }

    User candidato = userService.getUserById(dto.candidatoId());
    Vaga vaga = vagaService.buscarVagaPorId(dto.vagaId());

    Inscricao inscricao = InscricaoMapper
        .toEntity(dto, candidato, vaga);
    inscricaoRepository.save(inscricao);

    return InscricaoResponseDTO.from(inscricao);
  }

  public InscricaoResponseDTO buscarPorId(String id) {
    return inscricaoRepository.findById(id)
        .map((inscricao) -> InscricaoResponseDTO.from(inscricao))
        .orElseThrow(() -> new NotFoundException("Inscrição com id " + id + " não encontrada."));
  }

  public Inscricao buscarInscricaoPorId(String id) {
    return inscricaoRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Inscrição com id " + id + " não encontrada."));
  }

  public InscricaoResponseDTO getResponseInscricaoDTOById(String id) {
    verificarPermissaoDeLeitura(id);
    return inscricaoRepository.findById(id)
        .map((inscricao) -> InscricaoResponseDTO.from(inscricao))
        .orElseThrow(() -> new NotFoundException("Inscrição com id " + id + " não encontrada."));
  }

  // Rotas para ADMIN
  public List<InscricaoResponseDTO> getAllInscricoes() {
    List<Inscricao> inscricoes = inscricaoRepository.findAllByDeletadoEmNull();
    List<InscricaoResponseDTO> applications = inscricoes.stream()
    .map((inscricao) -> InscricaoResponseDTO.from(inscricao))

        .toList();
    return applications;
  }

  // Fecha rotas para ADMIN

  public List<InscricaoResponseDTO> getAllInscricoesPorCandidato(String candidatoId) {
    verificarPermissaoDeLeituraGeralPorCandidato(candidatoId);
    List<Inscricao> inscricoes = inscricaoRepository.findAllByCandidatoId(candidatoId);
    List<InscricaoResponseDTO> applications = inscricoes.stream()
    .map((inscricao) -> InscricaoResponseDTO.from(inscricao))
        .toList();
    return applications;
  }

  public List<InscricaoResponseDTO> getAllInscricoesPorVaga(Long vagaId) {
    verificarPermissaoDeLeituraGeralPorVaga(vagaId);
    List<Inscricao> inscricoes = inscricaoRepository.findAllByVagaId(vagaId);
    List<InscricaoResponseDTO> applications = inscricoes.stream()
    .map((inscricao) -> InscricaoResponseDTO.from(inscricao))
        .toList();
    return applications;
  }



  public InscricaoResponseDTO atualizarInscricao(String id, UpdateInscricaoDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao(id);
    Inscricao existingInscricao = this.buscarInscricaoPorId(id);

    if (dto.vagaId() != null) {
      existingInscricao.setVaga(vagaService.buscarVagaPorId(dto.vagaId()));
    }

    // if (dto.ativo() != null) {
    // existingInscricao.setAtivo(dto.ativo());
    // }

    // if (dto.avaliacoes() != null && !dto.avaliacoes().isEmpty()) {
    // List<CriterioAvaliacao> criterios =
    // criterioAvaliacaoService.buscarCriteriosPorIds(dto.avaliacoes());
    // existingInscricao.setAvaliacoes(criterios);
    // }

    // if (dto.avaliacoes() != null && !dto.avaliacoes().isEmpty()) {
    // List<CriterioAvaliacao> criterios =
    // criterioAvaliacaoService.buscarCriteriosPorIds(dto.avaliacoes());
    // existingInscricao.setAvaliacoes(criterios);
    // }

    inscricaoRepository.save(existingInscricao);
    return InscricaoResponseDTO.from(existingInscricao);
  }

  public void softDelete(String id) {
    verificarPermissaoDeCriacaoOuAlteracao(id);
    Inscricao inscricao = this.buscarInscricaoPorId(id);
    inscricao.setDeletadoEm(LocalDateTime.now());
    inscricaoRepository.save(inscricao);
  }

  public List<Inscricao> findInscricaoByProcesso(String processoId) {
    return inscricaoRepository.findByVagaProcessoSeletivoId(processoId);
  }

  public List<InscricaoResponseDTO> findByProcesso(String processoId) {
    List<Inscricao> inscricoes = inscricaoRepository.findByVagaProcessoSeletivoId(processoId);
    List<InscricaoResponseDTO> applications = inscricoes.stream()
       .map((inscricao) -> InscricaoResponseDTO.from(inscricao))
        .toList();
    return applications;
  }

  public boolean existsByInscricaoId(String inscricaoId) {
    return inscricaoRepository.existsById(inscricaoId);
  }

  public List<InscricaoResponseDTO> getAllInscricoesPorProcessoSeletivo(String processoId) {
    List<Inscricao> inscricoes = inscricaoRepository.findByVagaProcessoSeletivoId(processoId);
    List<InscricaoResponseDTO> applications = inscricoes.stream()
       .map((inscricao) -> InscricaoResponseDTO.from(inscricao))
        .toList();
    return applications;
  }
}
