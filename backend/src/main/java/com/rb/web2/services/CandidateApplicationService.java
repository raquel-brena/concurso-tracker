package com.rb.web2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.candidateApplication.CandidateApplication;
import com.rb.web2.repositories.CandidateApplicationRepository;

@Service
public class CandidateApplicationService {

  @Autowired
  private CandidateApplicationRepository candidateApplicationRepository;

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
    Optional<CandidateApplication> existingCandidateApplication = candidateApplicationRepository.findById(id);
    if (existingCandidateApplication.isPresent()) {
      // Update properties of the existing entity
      CandidateApplication candidateApplication = existingCandidateApplication.get();
      candidateApplication.setCoverLetter(updatedCandidateApplication.getCoverLetter()); // Example field
      // Add other fields as necessary
      return candidateApplicationRepository.save(candidateApplication);
    } else {
      throw new RuntimeException("Candidate Application not found with id " + id);
    }
  }

  // @TODO Implement this method
  public void deleteCandidateApplication(String id) {
    return;
  }
}
