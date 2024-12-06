package com.rb.web2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rb.web2.domain.processoComissao.ProcessoComissao;
import com.rb.web2.domain.processoComissao.ProcessoComissaoId;

@Repository
public interface ProcessoComissaoRepository extends JpaRepository<ProcessoComissao, ProcessoComissaoId> {
    ProcessoComissao findByProcessoSeletivoIdIgnoreCaseAndUserIdIgnoreCase(String processoSeletivoId, String userId);
}
