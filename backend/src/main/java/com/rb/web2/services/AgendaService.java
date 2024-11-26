package com.rb.web2.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.agenda.mapper.AgendaMapper;
import com.rb.web2.repositories.AgendaRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class AgendaService {

  @Autowired
  private AgendaRepository repository;

  public Agenda create(AgendaDTO dto) {
    if (dto == null) {
      throw new IllegalArgumentException("AgendaDTO não pode ser nulo.");
    }
    var novaAgenda = AgendaMapper.toEntity(dto);

    return repository.save(novaAgenda);
  }

  public Agenda getAgendaById(Long id) {
    return repository.findById(id).orElseThrow(
        () -> new NotFoundException("Agenda com o ID " + id + " não encontrado."));
  }

  public List<Agenda> getAllAgendas() {
    return repository.findAllByAtivoTrue();
  }

  public void deleteAgenda(Long id) {
    var agenda = getAgendaById(id);
    agenda.setAtivo(false);
    repository.save(agenda);
  }

  public AgendaDTO updateAgenda(Long id, AgendaDTO dto) {
    Agenda agenda = getAgendaById(id);
    Agenda agendaAtualizada = AgendaMapper.toEntity(dto);
    agendaAtualizada.setId(agenda.getId());

    repository.save(agendaAtualizada);
    AgendaDTO agendaAtualizadaDTO = AgendaMapper.toDTO(agendaAtualizada);
    return agendaAtualizadaDTO;
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

  // public boolean deleteInstitution(String id) {
  // Optional<Agenda> existingInstitution = getInstitutionById(id);
  // if (checkInstitutionExists(id)) {
  // Institution institution = existingInstitution.get();
  // institution.setAtivo(false); // Atualiza o campo ativo para false, marcando
  // como inativa

  // repository.save(institution);
  // return true;
  // }

  // return false;
  // }
}
