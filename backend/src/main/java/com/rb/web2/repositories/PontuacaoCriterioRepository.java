package com.rb.web2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;

@Repository
public interface PontuacaoCriterioRepository extends JpaRepository<PontuacaoCriterio, String> {
    List<PontuacaoCriterio> findByCriterioId(String criterioId);
    List<PontuacaoCriterio> findByInscricaoId(String inscricaoId);
}
