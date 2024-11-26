package com.rb.web2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.candidateApplication.CandidateApplication;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.repositories.CandidateApplicationRepository;
import com.rb.web2.services.UserService;

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

  public CandidateApplication create(CandidateApplication candidateApplication) {
    return candidateApplicationRepository.save(candidateApplication);
  }

  public Optional<CandidateApplication> getCandidateApplicationById(String id) {
    return candidateApplicationRepository.findById(id);
  }

  public List<CandidateApplication> getAllCandidateApplications() {
    return candidateApplicationRepository.findAll();
  }

  public CandidateApplication updateCandidateApplication(String id, CandidateApplication updatedCandidateApplication) {
    // Check if the CandidateApplication exists
    CandidateApplication existingCandidateApplication = candidateApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate Application not found with id " + id));

      // Update properties of the existing entity
      if (updatedCandidateApplication.getJobPosition() != null) {
          existingCandidateApplication.setJobPosition(updatedCandidateApplication.getJobPosition());
      }

      if (updatedCandidateApplication.getApplicationDate() != null) {
          existingCandidateApplication.setApplicationDate(updatedCandidateApplication.getApplicationDate());
      }

      if (updatedCandidateApplication.getCandidate() != null) {
        User candidate = userService.getUserById(updatedCandidateApplication.getCandidate().getId());
        if (candidate == null) {
          throw new RuntimeException("User not found with id " + updatedCandidateApplication.getCandidate().getId());
      }
      existingCandidateApplication.setCandidate(candidate);
      }

      if (updatedCandidateApplication.getProcessoSeletivo() != null) {
          ProcessoSeletivo processoSeletivo = processoSeletivoService.getProcessoSeletivoById(updatedCandidateApplication.getProcessoSeletivo().getId())
                  .orElseThrow(() -> new RuntimeException("Processo Seletivo not found with id " + updatedCandidateApplication.getProcessoSeletivo().getId()));
          existingCandidateApplication.setProcessoSeletivo(processoSeletivo);
      }

      existingCandidateApplication.setActive(updatedCandidateApplication.isActive());
      return candidateApplicationRepository.save(existingCandidateApplication);
  }

  // @TODO Implement this method
  public void deleteCandidateApplication(String id) {
    return;
  }
}
