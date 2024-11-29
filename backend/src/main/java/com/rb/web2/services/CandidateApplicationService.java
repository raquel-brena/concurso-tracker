package com.rb.web2.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.candidateApplication.CandidateApplication;
import com.rb.web2.domain.candidateApplication.dto.RequestInscricaoDTO;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.repositories.CandidateApplicationRepository;
import com.rb.web2.services.UserService;
import com.rb.web2.services.ProcessoSeletivoService;
import com.rb.web2.domain.candidateApplication.mapper.RequestInscricaoMapper;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.candidateApplication.dto.UpdateInscricaoDTO;

@Service
public class CandidateApplicationService {

  private final CandidateApplicationRepository candidateApplicationRepository;
    private final UserService userService;
    private final ProcessoSeletivoService processoSeletivoService;

    // Construtor para injeção de dependências
    public CandidateApplicationService(
            CandidateApplicationRepository candidateApplicationRepository,
            UserService userService,
            ProcessoSeletivoService processoSeletivoService) {
        this.candidateApplicationRepository = candidateApplicationRepository;
        this.userService = userService;
        this.processoSeletivoService = processoSeletivoService;
    }

  public CandidateApplication create(RequestInscricaoDTO dto) {
    User candidate = userService.getUserById(dto.candidateId());
    if (candidate == null) {
        throw new RuntimeException("User not found");
    }

    ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId())
        .orElseThrow(() -> new RuntimeException("Processo Seletivo not found"));

    CandidateApplication application = RequestInscricaoMapper.toEntity(dto, candidate, processoSeletivo);
    return candidateApplicationRepository.save(application);
  }

  public Optional<CandidateApplication> getCandidateApplicationById(String id) {
    return candidateApplicationRepository.findById(id);
  }

  public List<CandidateApplication> getAllCandidateApplications() {
    List<CandidateApplication> applications = candidateApplicationRepository.findAll();
    System.out.println("Applications retrieved: " + applications.size());  // Log para verificar o número de elementos
    return applications;
  }

  public CandidateApplication updateCandidateApplication(String id, UpdateInscricaoDTO dto){
    CandidateApplication existingCandidateApplication = candidateApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate Application not found with id " + id));

    existingCandidateApplication.setJobPosition(dto.jobPosition());

    return candidateApplicationRepository.save(existingCandidateApplication);
}

// Método auxiliar para mapear o DTO para a entidade CandidateApplication
private CandidateApplication updateWithDTO(CandidateApplication existingCandidateApplication, RequestInscricaoDTO dto) {
    if (dto.jobPosition() != null) {
        existingCandidateApplication.setJobPosition(dto.jobPosition());
    }

    if (dto.candidateId() != null) {
        User candidate = userService.getUserById(dto.candidateId());
        if (candidate == null) {
            throw new RuntimeException("User not found with id " + dto.candidateId());
        }
        existingCandidateApplication.setCandidate(candidate);
    }

    if (dto.processoSeletivoId() != null) {
        ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(dto.processoSeletivoId())
                .orElseThrow(() -> new RuntimeException("Processo Seletivo not found with id " + dto.processoSeletivoId()));
        existingCandidateApplication.setProcessoSeletivo(processoSeletivo);
    }

    // @TODO: Atualizando as avaliações (caso necessário)
    /*
    if (dto.avaliacoes() != null && !dto.avaliacoes().isEmpty()) {
        List<CriterioAvaliacao> avaliacoes = criterioAvaliacaoService.getAvaliacoesByIds(dto.avaliacoes());
        existingCandidateApplication.setAvaliacoes(avaliacoes);
    } */

    return existingCandidateApplication;
}

  // @TODO Implement this method
  public void deleteCandidateApplication(String id) {
    return;
  }
}
