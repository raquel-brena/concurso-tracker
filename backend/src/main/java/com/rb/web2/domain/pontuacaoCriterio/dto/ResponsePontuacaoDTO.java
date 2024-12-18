package com.rb.web2.domain.pontuacaoCriterio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;

public record ResponsePontuacaoDTO(
        String id,
        BigDecimal nota,
        String criterioId,
        String inscricaoId,
        boolean ehPublico,
        boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
    public ResponsePontuacaoDTO(PontuacaoCriterio pontuacaoCriterio) {
        this(
            pontuacaoCriterio.getId(),
            pontuacaoCriterio.getNota(),
            pontuacaoCriterio.getCriterio().getId(),
            pontuacaoCriterio.getInscricao().getId(),
            pontuacaoCriterio.isEhPublico(),
            pontuacaoCriterio.isAtivo(),
            pontuacaoCriterio.getCriado_em(),
            pontuacaoCriterio.getAtualizado_em()
        );
    }
}
