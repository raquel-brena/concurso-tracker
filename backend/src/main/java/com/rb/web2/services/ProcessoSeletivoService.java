package com.rb.web2.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.mapper.ProcessoSeletivoMapper;
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
}
