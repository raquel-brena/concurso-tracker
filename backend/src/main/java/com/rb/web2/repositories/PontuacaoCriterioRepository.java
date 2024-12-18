package com.rb.web2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;

@Repository
public interface PontuacaoCriterioRepository extends JpaRepository<PontuacaoCriterio, Long> {
    List<PontuacaoCriterio> findByCriterioId(Long criterioId);
    List<PontuacaoCriterio> findByInscricaoId(String inscricaoId);
}
