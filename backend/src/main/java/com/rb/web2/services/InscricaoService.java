package com.rb.web2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.inscricao.dto.UpdateInscricaoDTO;
import com.rb.web2.domain.inscricao.mapper.RequestInscricaoMapper;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;
import com.rb.web2.repositories.InscricaoRepository;

@Service
public class InscricaoService {

  private final InscricaoRepository inscricaoRepository;
  private final UserService userService;
  private final ProcessoSeletivoService processoSeletivoService;

  // Construtor para injeção de dependências
  public InscricaoService(
      InscricaoRepository inscricaoRepository,
      UserService userService,
      ProcessoSeletivoService processoSeletivoService) {
    this.inscricaoRepository = inscricaoRepository;
    this.userService = userService;
    this.processoSeletivoService = processoSeletivoService;
  }

  public Inscricao create(RequestInscricaoDTO dto) {
    User candidate = userService.getUserById(dto.candidateId());
    if (candidate == null) {
      throw new RuntimeException("User not found");
    }

    ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId());

    Inscricao application = RequestInscricaoMapper.toEntity(dto, candidate, processoSeletivo);
    return inscricaoRepository.save(application);
  }

  public Optional<Inscricao> getInscricaoById(String id) {
    return inscricaoRepository.findById(id);
  }

  public List<Inscricao> getAllInscricaos() {
    List<Inscricao> applications = inscricaoRepository.findAll();
    System.out.println("Applications retrieved: " + applications.size()); // Log para verificar o número de elementos
    return applications;
  }

  public Inscricao updateInscricao(String id, UpdateInscricaoDTO dto) {
    Inscricao existingInscricao = inscricaoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Candidate Application not found with id " + id));

    existingInscricao.setJobPosition(dto.jobPosition());

    return inscricaoRepository.save(existingInscricao);
  }

  // @TODO Implement this method
  public void deleteInscricao(String id) {
    
  }
}
