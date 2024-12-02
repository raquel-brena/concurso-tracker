package com.rb.web2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;

@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long> {
    Optional<CriterioAvaliacao> findById(Long id);
    Optional<CriterioAvaliacao> findByNome(String nome);
    List<CriterioAvaliacao> findAllById(Long id);
}
