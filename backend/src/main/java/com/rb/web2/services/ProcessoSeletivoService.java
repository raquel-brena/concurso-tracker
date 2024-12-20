package com.rb.web2.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.enums.Perfil;
import com.rb.web2.domain.processoComissao.ProcessoComissao;
import com.rb.web2.domain.processoComissao.dto.MembroComissaoRequestDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.ProcessoRequestDTO;
import com.rb.web2.domain.processoSeletivo.dto.ProcessoResponseDTO;
import com.rb.web2.domain.processoSeletivo.dto.UpdateProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.UserResponseDTO;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.AgendaRepository;
import com.rb.web2.repositories.ProcessoComissaoRepository;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.repositories.VagaRepository;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class ProcessoSeletivoService {

  @Autowired
  private ProcessoSeletivoRepository repository;

  @Autowired
  private VagaRepository vagaRepository;

  @Autowired
  private AgendaRepository agendaRepository;

  @Autowired
  private ProcessoComissaoRepository processoComissaoRepository;

  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private DocumentoService documentoService;

  @Autowired
  private AuthenticationService authenticationService;

  private AuthorizationUtil authorizationUtil;

  @PostConstruct
  public void init() {
    this.authorizationUtil = new AuthorizationUtil(userService);
  }

  private void verificarPermissaoDeCriacaoOuAlteracao(String processoId) {
    authorizationUtil.<String>verificarPermissaoOuComissao(
        processoId,
        "EDIT_PROCESSO_SELETIVO",
        id -> repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Processo Seletivo não encontrado.")),
        (entity, user) -> {
          ProcessoSeletivo processo = (ProcessoSeletivo) entity;
          List<User> comissaoOrganizadora = processo.getComissaoOrganizadora();
          return comissaoOrganizadora != null && comissaoOrganizadora.contains(user);
        });
  }

  @Transactional
  public ProcessoResponseDTO create(ProcessoRequestDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao(null);

    if (dto.titulo() == null || dto.validade() == null) {
      throw new NotFoundException("Titulo do processo seletivo não pode ser nulo");
    }

    Optional<ProcessoSeletivo> existeProcessoOpt = repository.findByTitulo(dto.titulo());

    if (existeProcessoOpt.isPresent()) {
      throw new NotFoundException("Processo seletivo com o nome " + dto.titulo() + " já existe");
    }

    UserResponseDTO user = authenticationService.getUsuarioAutenticado();
    userService.upgradeToCoordenador(user.id());
    User userEntity = userService.getUserById(user.id());

    ProcessoSeletivo processoCriado = repository.save(ProcessoSeletivoMapper.toEntity(dto));
    if (processoCriado.getComissaoOrganizadora() == null) {
      processoCriado.setComissaoOrganizadora(new ArrayList<>());
    }

    // Aparentemente usar Transactional torna essa linha desnecessária??
    processoComissaoRepository.save(new ProcessoComissao(processoCriado, userEntity));
    processoCriado.getComissaoOrganizadora().add(userEntity);

    return ProcessoResponseDTO.from(processoCriado);
  }

  public ProcessoResponseDTO getById(String id) {
    return ProcessoResponseDTO.from(repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Processo não encontrado")));
  }

  public ProcessoSeletivo getProcessoSeletivoById(String id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Processo não encontrado"));
  }

  public ProcessoResponseDTO getByTitulo(String titulo) {
    return repository.findByTitulo(titulo)
        .map(ProcessoResponseDTO::from)
        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
  }

  public ProcessoSeletivo getProcessoSeletivoByTitulo(String titulo) {
    return repository.findByTitulo(titulo)
        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
  }

  public List<ProcessoResponseDTO> getAllProcessoSeletivos() {
    return repository.findAll().stream()
        .map(ProcessoResponseDTO::from)
        .toList();
  }

  public ProcessoResponseDTO update(String processoId) {
    verificarPermissaoDeCriacaoOuAlteracao(processoId);

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

    repository.save(processo);
    return ProcessoResponseDTO.from(processo);
  }

  public void adicionarAgenda(Agenda agenda, String processoId) {
    verificarPermissaoDeCriacaoOuAlteracao(processoId);

    ProcessoSeletivo processo = repository.findById(processoId)
        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));

    processo.setAgenda(agenda);
    repository.save(processo);
  }

  public List<ProcessoResponseDTO> buscarProcessos(String termo) {
    List<ProcessoSeletivo> processos = repository
        .findByTituloContainingIgnoreCaseOrDescricaoContainingOrderByAgendaInicioInscricaoDesc(termo,
            termo);

    return processos.stream().map(ProcessoResponseDTO::from).toList();
  }

  public void adicionarMembroComissao(MembroComissaoRequestDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao(dto.processoSeletivoId());

    ProcessoSeletivo processoSeletivo = repository.findById(dto.processoSeletivoId())
        .orElseThrow(() -> new RuntimeException("Processo Seletivo não encontrado"));

    User user = userService.getUserById(dto.userId());

    if (!user.getPerfil().equals(Perfil.COORDENADOR)) {
      userService.upgradeToAssistente(user.getId());
    }

    if (!processoSeletivo.getComissaoOrganizadora().contains(user)) {
      processoSeletivo.getComissaoOrganizadora().add(user);
      repository.save(processoSeletivo);
    } else {
      throw new BadRequestException("Usuário já faz parte da comissão organizadora");
    }
  }

  public void removerMembroComissao(MembroComissaoRequestDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao(dto.processoSeletivoId());

    ProcessoComissao processoComissao = processoComissaoRepository
        .findByProcessoSeletivoIdIgnoreCaseAndUserIdIgnoreCase(dto.processoSeletivoId(), dto.userId());

    if (processoComissao == null) {
      throw new NotFoundException("Membro não encontrado");
    }

    if (processoComissao.getDeletedAt() != null) {
      throw new NotFoundException("Membro já removido");
    }

    User user = userService.getUserById(dto.userId());

    if (!user.getPerfil().equals(Perfil.COORDENADOR)) {
      userService.downgradeToUser(user.getId());
    }

    processoComissao.setDeletedAt(LocalDateTime.now());
    processoComissaoRepository.save(processoComissao);
  }

  public String deleteById(String id) {
    verificarPermissaoDeCriacaoOuAlteracao(id);

    ProcessoSeletivo processo = this.getProcessoSeletivoById(id);
    processo.setDeletadoEm(LocalDateTime.now());
    return repository.save(processo).getId();
  }

  public void homologarDocumentacao() {

  }

  public ProcessoResponseDTO atualizar(String id, UpdateProcessoDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao(id);

    ProcessoSeletivo processo = this.getProcessoSeletivoById(id);

    if (dto.titulo() != null) {
      processo.setTitulo(dto.titulo());
    }

    if (dto.temporario() != processo.isTemporario()) {
      processo.setTemporario(dto.temporario());
    }

    // @TODO Avaliar estratégia (neste caso, se o valor novo for "null" vai
    // atualizar o do bd para "null" também)
    if (dto.descricao() == null ? processo.getDescricao() != null : !dto.descricao().equals(processo.getDescricao())) {
      processo.setDescricao(dto.descricao());
    }

    if (dto.validade() != processo.getValidadeMeses()) {
      processo.setValidadeMeses(dto.validade());
    }

    if (dto.linkEdital() != null) {
      Documento documento = documentoService.getDocumentoByUrl(dto.linkEdital())
          .orElseThrow(() -> new NotFoundException("Documento não encontrado"));

      if (processo.getEditais() == null) {
        processo.setEditais(new ArrayList<>());
      }

      processo.getEditais().add(documento);
    }

    if (dto.vagasIds() != null) {
      List<Vaga> vagas = vagaRepository.findAllById(dto.vagasIds());
      processo.setVagas(vagas);
    }

    if (dto.agendaId() != null) {
      Agenda agenda = agendaRepository.findById(dto.agendaId()).orElse(null);
      if (agenda != null) {
        processo.setAgenda(agenda);
      }
    }

    if (dto.documentoNecessarios() != null) {
      processo.setDocumentosNecessarios(dto.documentoNecessarios());
    }

    // if (dto.criteriosIds() != null) {
    // List<CriterioAvaliacao> criterios =
    // criterioAvaliacaoRepository.findAllById(dto.criteriosIds());
    // if (criterios.isEmpty()) {
    // throw new NotFoundException("Criterio de avaliação não encontrado");
    // }
    // processo.setCriterios(criterios);
    // }

    if (dto.comissaoOrganizadoraIds() != null) {
      List<User> comissao = userService.findAllById(dto.comissaoOrganizadoraIds());
      processo.setComissaoOrganizadora(comissao);
    }

    if (dto.comissaoOrganizadoraIds() != null) {
      List<User> comissao = userService.findAllById(dto.comissaoOrganizadoraIds());
      processo.setComissaoOrganizadora(comissao);
    }

    repository.save(processo);
    return ProcessoResponseDTO.from(processo);
  }

  public List<ProcessoResponseDTO> getProcessoSeletivoByParticipante(String id) {
    try {
      return repository.findByVagasInscricoesCandidatoId(id).stream()
          .map(ProcessoResponseDTO::from)
          .toList();
    } catch (Exception e) {
      throw new NotFoundException("Processo não encontrado");
    }
  }

  public List<ProcessoResponseDTO> getProcessoSeletivoByComissao(String id) {
    try {
      return repository.findByComissaoOrganizadoraId(id).stream()
          .map(ProcessoResponseDTO::from)
          .toList();
    } catch (Exception e) {
      throw new NotFoundException("Processo não encontrado");
    }
  }

}
