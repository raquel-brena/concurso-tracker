package com.rb.web2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, String> {
    List<CriterioAvaliacao> findByProcessoSeletivo(ProcessoSeletivo processoSeletivo);
    List<CriterioAvaliacao> findByParticipantes(Inscricao inscricao);
    Optional<CriterioAvaliacao> findById(String id);
    List<CriterioAvaliacao> findAllById(List<Long> criteriosIds);
}
