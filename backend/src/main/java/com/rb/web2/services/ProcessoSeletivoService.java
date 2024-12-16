package com.rb.web2.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rb.web2.domain.processoComissao.ProcessoComissao;
import com.rb.web2.domain.processoComissao.dto.RequestMembroComissaoDTO;
import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.documento.dto.CreateDocumentoDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.ResponseProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.domain.user.User;
import com.rb.web2.repositories.ProcessoComissaoRepository;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.repositories.AgendaRepository;
import com.rb.web2.repositories.CriterioAvaliacaoRepository;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.repositories.ProcessoComissaoRepository;
import com.rb.web2.repositories.VagaRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class ProcessoSeletivoService {

  @Autowired
  private ProcessoSeletivoRepository repository;

  @Autowired
  private VagaRepository vagaRepository;

  @Autowired
  private AgendaRepository agendaRepository;

  @Autowired
  private CriterioAvaliacaoRepository criterioAvaliacaoRepository;

  @Autowired
  private ProcessoComissaoRepository processoComissaoRepository;

  @Autowired
  private UserService userService;

  public ResponseProcessoDTO create(RequestProcessoDTO dto) {
    if (dto.titulo() == null || dto.validade() == null) {
      throw new NotFoundException("Titulo do processo seletivo não pode ser nulo");
    }
    var existeProcesso = this.getProcessoSeletivoByTitulo(dto.titulo());

    if (existeProcesso.isPresent()) {
      throw new NotFoundException("Processo seletivo com o nome " + dto.titulo() + " já existe");
    }

    ProcessoSeletivo processoCriado = repository.save(ProcessoSeletivoMapper.toEntity(dto));

    return ProcessoSeletivoMapper.toResponseProcessoDTO(processoCriado);
  }

  public ProcessoSeletivo getProcessoSeletivoById(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
  }

  public Optional<ProcessoSeletivo> getProcessoSeletivoByTitulo(String titulo) {
    return repository.findByTitulo(titulo);
  }

  public List<ResponseProcessoDTO> getAllProcessoSeletivos() {
    return repository.findAll().stream()
        .map(ProcessoSeletivoMapper::toResponseProcessoDTO)
        .toList();
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
    User user = userService.getUserById(dto.userId());

    // Adicionar o usuário à comissão organizadora
    if (!processoSeletivo.getComissaoOrganizadora().contains(user)) {
      processoSeletivo.getComissaoOrganizadora().add(user);
      repository.save(processoSeletivo); // Salva a entidade com a atualização
    } else {
      throw new RuntimeException("Usuário já faz parte da comissão organizadora");
    }
  }

  public void removerMembroComissao(RequestMembroComissaoDTO dto) {
    ProcessoComissao processoComissao = processoComissaoRepository
        .findByProcessoSeletivoIdIgnoreCaseAndUserIdIgnoreCase(dto.processoSeletivoId(), dto.userId());

    if (processoComissao == null) {
      throw new NotFoundException("Membro não encontrado");
    }

    if (processoComissao.getDeletedAt() != null) {
      throw new NotFoundException("Membro já removido");
    }

    processoComissao.setDeletedAt(LocalDateTime.now());
    processoComissaoRepository.save(processoComissao);
  }

  public String deleteById(String id) {
    ProcessoSeletivo processo = this.getProcessoSeletivoById(id);
    processo.setDeletadoEm(LocalDateTime.now());
    return repository.save(processo).getId();
  }

  public ProcessoSeletivo atualizar(String id, UpdateProcessoDTO dto) {
    ProcessoSeletivo processo = this.getProcessoSeletivoById(id);

    if (dto.titulo() != null) {
      processo.setTitulo(dto.titulo());
    }

    if (dto.temporario() != processo.isTemporario()) {
      processo.setTemporario(dto.temporario());
    }

    if (dto.descricao() != processo.getDescricao()) {
      processo.setDescricao(dto.descricao());
    }

    if (dto.validade() != processo.getValidadeMeses()) {
      processo.setValidadeMeses(dto.validade());
    }

    // if (dto.linkEdital() != processo.getEditais()) {
    // processo.setLinkEdital(dto.linkEdital());
    // }

    if (dto.vagasIds() != null) {
      List<Vaga> vagas = vagaRepository.findAllById(dto.vagasIds());
      processo.setVagas(vagas);
    }

    if (dto.agendaId() != null) {
      Agenda agenda = agendaRepository.findById(dto.agendaId())
          .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));
      processo.setAgenda(agenda);
    }

    if (dto.documentoNecessarios() != null) {
      processo.setDocumentosNecessarios(dto.documentoNecessarios());
    }

    if (dto.criteriosIds() != null) {
      List<CriterioAvaliacao> criterios = criterioAvaliacaoRepository.findAllById(dto.criteriosIds());
      if (criterios.isEmpty()) {
        throw new NotFoundException("Criterio de avaliação não encontrado");
      }
      processo.setCriterios(criterios);
    }

    if (dto.comissaoOrganizadoraIds() != null) {
      List<User> comissao = userService.findAllById(dto.comissaoOrganizadoraIds());
      processo.setComissaoOrganizadora(comissao);
    }

    if (dto.comissaoOrganizadoraIds() != null) {
      List<User> participantes = userService.findAllById(dto.comissaoOrganizadoraIds());
      processo.setParticipantes(participantes);
    }

    return repository.save(processo);
  }

}
