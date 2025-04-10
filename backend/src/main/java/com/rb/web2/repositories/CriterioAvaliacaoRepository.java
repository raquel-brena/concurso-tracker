package com.rb.web2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.CriterioResponseDTO;

@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long> {
    Optional<CriterioAvaliacao> findAllByIdIn(List<Long> ids);
    Optional<CriterioAvaliacao> findByIdIn(List<Long> avaliacoes);
    Optional<List<CriterioAvaliacao>> findByEtapaProcessoSeletivoId(String processoSeletivoId);
    List<CriterioResponseDTO> findByPontuacoesInscricaoId(String inscricaoId);
}
