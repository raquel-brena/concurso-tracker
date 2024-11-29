package com.rb.web2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;
import com.rb.web2.domain.candidateApplication.CandidateApplication;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;

import java.util.List;
import java.util.Optional;

@Repository
public interface PontuacaoCriterioRepository extends JpaRepository<PontuacaoCriterio, String> {
    List<PontuacaoCriterio> findByCandidateApplication(CandidateApplication candidateApplication);
    List<PontuacaoCriterio> findByCriterio(CriterioAvaliacao criterio);
    Optional<PontuacaoCriterio> findById(String id);
}
