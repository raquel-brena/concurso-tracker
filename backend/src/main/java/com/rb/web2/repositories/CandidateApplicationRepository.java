package com.rb.web2.repositories;

import com.rb.web2.domain.candidateApplication.CandidateApplication;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateApplicationRepository extends JpaRepository<CandidateApplication, String> {
    
}