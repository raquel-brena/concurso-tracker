package com.rb.web2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long> {
    // List<CriterioAvaliacao> findByProcessoSeletivo(ProcessoSeletivo processoSeletivo);
    // List<CriterioAvaliacao> findByParticipantes(Inscricao inscricao);
    List<CriterioAvaliacao> findAllByIdIn(List<String> ids);
    List<CriterioAvaliacao> findByIdIn(List<Long> avaliacoes);
}
