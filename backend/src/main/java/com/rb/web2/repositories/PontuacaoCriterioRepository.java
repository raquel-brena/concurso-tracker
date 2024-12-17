package com.rb.web2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;

@Repository
public interface PontuacaoCriterioRepository extends JpaRepository<PontuacaoCriterio, Long> {
    List<PontuacaoCriterio> findByInscricao(Inscricao inscricao);
    List<PontuacaoCriterio> findByCriterio(CriterioAvaliacao criterio);
}
