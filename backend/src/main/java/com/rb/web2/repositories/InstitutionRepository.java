package com.rb.web2.repositories;

import com.rb.web2.domain.institution.Institution;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionReposutory extends JpaRepository<Institution, String> {
    Optional<Institution> findById(String id);
    
    Optional<Institution> findByName(String name);
    
    List<Institution> findByLocation(String location);
    
}
