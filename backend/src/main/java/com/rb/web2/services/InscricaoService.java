package com.rb.web2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.inscricao.dto.UpdateReqInscricaoDTO;
import com.rb.web2.domain.inscricao.mapper.RequestInscricaoMapper;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.repositories.InscricaoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class InscricaoService {

  private final InscricaoRepository inscricaoRepository;
  private final UserService userService;
  private final ProcessoSeletivoService processoSeletivoService;
  private final VagaService vagaService;

  // Construtor para injeção de dependências
  public InscricaoService(
      InscricaoRepository inscricaoRepository,
      UserService userService,
      ProcessoSeletivoService processoSeletivoService,
      VagaService vagaService) {
    this.inscricaoRepository = inscricaoRepository;
    this.userService = userService;
    this.processoSeletivoService = processoSeletivoService;
    this.vagaService = vagaService;
  }

  public Inscricao create(RequestInscricaoDTO dto) {
    User candidato = userService.getUserById(dto.candidatoId());
    ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId());
    Vaga vaga = vagaService.buscarVagaPorId(dto.vagaId());

    Inscricao inscricao = RequestInscricaoMapper
        .toEntity(dto, candidato, processoSeletivo, vaga);

    return inscricaoRepository.save(inscricao);
  }

  public Inscricao getInscricaoById(String id) {
    return inscricaoRepository.findById(id).orElseThrow(() 
    -> new NotFoundException("Inscrição com id " + id + " não encontrada."));
  }

  public List<Inscricao> getAllInscricaos() {
    List<Inscricao> applications = inscricaoRepository.findAll();
    System.out.println("Applications retrieved: " + applications.size()); // Log para verificar o número de elementos
    return applications;
  }

  public Inscricao atualizarInscricao(String id, UpdateReqInscricaoDTO dto) {
    Inscricao existingInscricao = this.getInscricaoById(id);

    if (dto.vagaId() != null) {
        existingInscricao.setVaga(vagaService.buscarVagaPorId(dto.vagaId()));
    }

    if (dto.processoSeletivoId() != null) {
        existingInscricao.setProcessoSeletivo(processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId()));
    }

    if (dto.ativo() != null) {
        existingInscricao.setAtivo(dto.ativo());
    }

    // if (dto.avaliacoes() != null && !dto.avaliacoes().isEmpty()) {
    //     List<CriterioAvaliacao> criterios = criterioAvaliacaoService.buscarCriteriosPorIds(dto.avaliacoes());
    //     existingInscricao.setAvaliacoes(criterios);
    // }

    return inscricaoRepository.save(existingInscricao);
}


  // @TODO Implement this method
  public void deleteInscricao(String id) {

  }
}
