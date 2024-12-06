package com.rb.web2.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.processoComissao.ProcessoComissao;
import com.rb.web2.domain.processoComissao.dto.RequestMembroComissaoDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.domain.user.User;
import com.rb.web2.repositories.ProcessoComissaoRepository;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class ProcessoSeletivoService {

  @Autowired
  private ProcessoSeletivoRepository repository;

  @Autowired
  private ProcessoComissaoRepository processoComissaoRepository;

  @Autowired
  private UserService userService;

  public ProcessoSeletivo create(RequestProcessoDTO dto) {
    var existeProcesso = this.getProcessoSeletivoByTitulo(dto.titulo());

    if (existeProcesso.isPresent()) {
      throw new NotFoundException("Processo seletivo com o nome " + dto.titulo() + " já existe");
    }

    ProcessoSeletivo processo = ProcessoSeletivoMapper.toEntity(dto);
    return repository.save(processo);
  }

  public Optional<ProcessoSeletivo> getProcessoSeletivoById(String id) {
    return repository.findById(id);
  }

  public Optional<ProcessoSeletivo> getProcessoSeletivoByTitulo(String titulo) {
    return repository.findByTitulo(titulo);
  }

  public List<ProcessoSeletivo> getAllProcessoSeletivos() {
    return repository.findAll();
  }

  public ProcessoSeletivo update(String processoId) {
    ProcessoSeletivo processo = repository.findById(processoId)
        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));

    // Atualizar vagas
    // if (dto.getVagasIds() != null) {
    // List<Vaga> vagas = vagaRepository.findAllById(dto.getVagasIds());
    // processo.setVagas(vagas);
    // }

    // // Atualizar agenda
    // if (dto.getAgendaId() != null) {
    // Agenda agenda = agendaRepository.findById(dto.getAgendaId())
    // .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));
    // processo.setAgenda(agenda);
    // }

    // Atualizar documentos
    // if (dto.getDocumentosNecessarios() != null) {
    // processo.setDocumentosNecessarios(dto.getDocumentosNecessarios());
    // }

    // // Atualizar critérios
    // if (dto.getCriteriosIds() != null) {
    // List<CriterioAvaliacao> criterios =
    // criterioAvaliacaoRepository.findAllById(dto.getCriteriosIds());
    // processo.setCriterios(criterios);
    // }

    // // Atualizar comissão organizadora
    // if (dto.getComissaoIds() != null) {
    // List<User> comissao = userRepository.findAllById(dto.getComissaoIds());
    // processo.setComissaoOrganizadora(comissao);
    // }

    // // Atualizar participantes
    // if (dto.getParticipantesIds() != null) {
    // List<User> participantes =
    // userRepository.findAllById(dto.getParticipantesIds());
    // processo.setParticipantes(participantes);
    // }

    return repository.save(processo);
  }

  public List<ProcessoSeletivo> buscarProcessos(String termo) {
    return repository.findByTituloContainingIgnoreCaseOrDescricaoContainingOrderByAgendaInicioInscricaoDesc(termo,
        termo);
  }

  public void adicionarMembroComissao(RequestMembroComissaoDTO dto) {
    // Buscar o Processo Seletivo
    ProcessoSeletivo processoSeletivo = repository.findById(dto.processoSeletivoId())
        .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado"));

    // Buscar o usuário
    User user = userService.getUserById(dto.userId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    // Adicionar o usuário à comissão organizadora
    if (!processoSeletivo.getComissaoOrganizadora().contains(user)) {
      processoSeletivo.getComissaoOrganizadora().add(user);
      repository.save(processoSeletivo); // Salva a entidade com a atualização
    } else {
      throw new RuntimeException("Usuário já faz parte da comissão organizadora");
    }
  }

  public void removerMembroComissao(RequestMembroComissaoDTO dto) {
    ProcessoComissao processoComissao = processoComissaoRepository.findByProcessoSeletivoIdIgnoreCaseAndUserIdIgnoreCase(dto.processoSeletivoId(), dto.userId());

    if (processoComissao == null) {
      throw new NotFoundException("Membro não encontrado");
    }

    if (processoComissao.getDeletedAt() != null) {
      throw new NotFoundException("Membro já removido");
    }

    processoComissao.setDeletedAt(LocalDateTime.now());
    processoComissaoRepository.save(processoComissao);
  }


}
