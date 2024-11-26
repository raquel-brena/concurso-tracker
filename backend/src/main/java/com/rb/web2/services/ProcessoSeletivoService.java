package com.rb.web2.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
import com.rb.web2.domain.vaga.Vaga;
import com.rb.web2.repositories.ProcessoSeletivoRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class ProcessoSeletivoService {

  @Autowired
  private ProcessoSeletivoRepository repository;

  public ProcessoSeletivo create(RequestProcessoDTO dto) {
    var existeProcesso = this.getProcessoSeletivoByTitulo(dto.titulo());

    if(existeProcesso.isPresent()) {
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
        //     List<Vaga> vagas = vagaRepository.findAllById(dto.getVagasIds());
        //     processo.setVagas(vagas);
        // }

        // // Atualizar agenda
        // if (dto.getAgendaId() != null) {
        //     Agenda agenda = agendaRepository.findById(dto.getAgendaId())
        //             .orElseThrow(() -> new NotFoundException("Agenda não encontrada"));
        //     processo.setAgenda(agenda);
        // }

        // Atualizar documentos
        // if (dto.getDocumentosNecessarios() != null) {
        //     processo.setDocumentosNecessarios(dto.getDocumentosNecessarios());
        // }

        // // Atualizar critérios
        // if (dto.getCriteriosIds() != null) {
        //     List<CriterioAvaliacao> criterios = criterioAvaliacaoRepository.findAllById(dto.getCriteriosIds());
        //     processo.setCriterios(criterios);
        // }

        // // Atualizar comissão organizadora
        // if (dto.getComissaoIds() != null) {
        //     List<User> comissao = userRepository.findAllById(dto.getComissaoIds());
        //     processo.setComissaoOrganizadora(comissao);
        // }

        // // Atualizar participantes
        // if (dto.getParticipantesIds() != null) {
        //     List<User> participantes = userRepository.findAllById(dto.getParticipantesIds());
        //     processo.setParticipantes(participantes);
        // }

        return repository.save(processo);
    }
}
