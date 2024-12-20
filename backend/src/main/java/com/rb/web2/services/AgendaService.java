package com.rb.web2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.agenda.dto.AgendaRequestDTO;
import com.rb.web2.domain.agenda.dto.AgendaResponseDTO;
import com.rb.web2.domain.agenda.mapper.AgendaMapper;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.AgendaRepository;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class AgendaService {

  @Autowired
  private AgendaRepository repository;

  @Autowired
  private UserService userService;

  @Autowired
  private ProcessoSeletivoService processoSeletivoService;

  private AuthorizationUtil authorizationUtil;

  @PostConstruct
  public void init() {
    this.authorizationUtil = new AuthorizationUtil(userService);
  }

  private void verificarPermissaoDeCriacaoOuAlteracao(Long agendaId) {
    authorizationUtil.verificarPermissaoOuComissao(
        agendaId,
        "EDIT_AGENDA",
        id -> repository.findById(id).orElseThrow(() -> new NotFoundException("Agenda não encontrada.")),
        (entity, user) -> {
          Agenda agenda = (Agenda) entity;
          return agenda.getProcessoSeletivo().getComissaoOrganizadora().contains(user);
        });
  }

  @Transactional
  public AgendaResponseDTO create(AgendaRequestDTO dto) {
    if (dto == null) {
      throw new IllegalArgumentException("AgendaDTO não pode ser nulo.");
    }

    verificarPermissaoDeCriacaoOuAlteracao(null);

    Agenda novaAgenda = AgendaMapper.toEntity(dto);
    this.isConsistent(novaAgenda);
    novaAgenda = repository.save(novaAgenda);
    processoSeletivoService.adicionarAgenda(novaAgenda, dto.processoSeletivoId());
    
    return AgendaResponseDTO.from(novaAgenda);
  }

  public Agenda getAgendaById(Long id) {
    return repository.findById(id).orElseThrow(
        () -> new NotFoundException("Agenda com o ID " + id + " não encontrado."));
  }

  public List<AgendaResponseDTO> getAllAgendas() {
    List<Agenda> agendas = repository.findAllByAtivoTrue();
    List<AgendaResponseDTO> agendasDTO = agendas.stream().map(AgendaResponseDTO::from).collect(Collectors.toList());
    return agendasDTO;
  }

  public void deleteAgenda(Long id) {
    verificarPermissaoDeCriacaoOuAlteracao(id);

    var agenda = getAgendaById(id);
    agenda.setAtivo(false);
    repository.save(agenda);
  }

  public AgendaResponseDTO updateAgenda(Long id, AgendaRequestDTO dto) {
    verificarPermissaoDeCriacaoOuAlteracao(id);

    Agenda agenda = getAgendaById(id);
    Agenda agendaAtualizada = AgendaMapper.toEntity(dto);
    agendaAtualizada.setId(agenda.getId());
    this.isConsistent(agendaAtualizada);

    repository.save(agendaAtualizada);
    AgendaResponseDTO agendaAtualizadaDTO = AgendaResponseDTO.from(agendaAtualizada);
    return agendaAtualizadaDTO;
  }

  public boolean isConsistent(Agenda agenda) {

    if (agenda.getInicioVigencia().isAfter(agenda.getFimVigencia())) {
      throw new BadRequestException("A data de início da vigência deve ser anterior à data de fim da vigência.");
    }

    if (agenda.getInicioInscricao().isAfter(agenda.getFimInscricao())) {
      throw new BadRequestException("A data de início das inscrições deve ser anterior à data de fim das inscrições.");
    }

    if (agenda.getFimInscricao().isAfter(agenda.getInicioHomologacao())) {
      throw new BadRequestException("A data de fim das inscrições deve ser anterior à data de homologação.");
    }

    if (agenda.getInicioHomologacao().isAfter(agenda.getInicioRecurso())) {
      throw new BadRequestException("A data de homologação deve ser anterior à data de início do recurso.");
    }

    if (agenda.getInicioRecurso().isAfter(agenda.getFimRecurso())) {
      throw new BadRequestException("A data de início do recurso deve ser anterior à data de fim do recurso.");
    }

    if (agenda.getFimRecurso().isAfter(agenda.getResultadoPreliminar())) {
      throw new BadRequestException("A data de fim do recurso deve ser anterior ao resultado preliminar.");
    }

    if (agenda.getResultadoPreliminar().isAfter(agenda.getResultadoFinal())) {
      throw new BadRequestException("O resultado preliminar deve ser anterior ao resultado final.");
    }

    if (agenda.getResultadoFinal().isAfter(agenda.getPrazoConvocacao())) {
      throw new BadRequestException("O resultado final deve ser anterior ao prazo de convocação.");
    }

    return true;
  }

  // public Agenda updateAgenda(
  // String id,
  // Agenda updatedAgenda
  // ) {
  // if (checkAgendaExists(id)) {
  // updatedAgenda.setId(id);
  // return repository.save(updatedAgenda);
  // }

  // return null;
  // }

  // public boolean deleteInstituicao(String id) {
  // Optional<Agenda> existingInstituicao = getInstituicaoById(id);
  // if (checkInstituicaoExists(id)) {
  // Instituicao instituicao = existingInstituicao.get();
  // instituicao.setAtivo(false); // Atualiza o campo ativo para false, marcando
  // como inativa

  // repository.save(instituicao);
  // return true;
  // }

  // return false;
  // }
}
