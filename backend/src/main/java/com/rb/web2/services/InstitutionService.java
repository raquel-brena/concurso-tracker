package com.rb.web2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.institution.Institution;
import com.rb.web2.repositories.InstitutionRepository;

@Service
public class InstitutionService {

  @Autowired
  private InstitutionRepository institutionRepository;

  public Institution create(Institution institution) {
    return institutionRepository.save(institution);
  }

  public Optional<Institution> getInstitutionById(String id) {
    return institutionRepository.findById(id);
  }

  public List<Institution> getAllInstitutions() {
    return institutionRepository.findAll();
  }

  public Institution updateInstitution(
    String id,
    Institution updatedInstitution
  ) {
    if (checkInstitutionExists(id)) {
      updatedInstitution.setId(id);
      return institutionRepository.save(updatedInstitution);
    }

    return null;
  }

  public boolean deleteInstitution(String id) {
    Optional<Institution> existingInstitution = getInstitutionById(id);
    if (checkInstitutionExists(id)) {
      Institution institution = existingInstitution.get();
      institution.setAtivo(false); // Atualiza o campo ativo para false, marcando como inativa

      institutionRepository.save(institution);
      return true;
    }
    
    return false;
  }

  public boolean checkInstitutionExists(String id) {
    return institutionRepository.existsById(id);
  }
}
