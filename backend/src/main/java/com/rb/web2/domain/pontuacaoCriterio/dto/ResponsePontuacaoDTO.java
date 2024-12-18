package com.rb.web2.domain.pontuacaoCriterio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;

public record ResponsePontuacaoDTO(
        Long id,
        BigDecimal nota,
        Long criterioId,
        String inscricaoId,
        boolean ehPublico,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {
    public ResponsePontuacaoDTO(PontuacaoCriterio pontuacaoCriterio) {
        this(
                pontuacaoCriterio.getId(),
                pontuacaoCriterio.getNota(),
                pontuacaoCriterio.getCriterio().getId(),
                pontuacaoCriterio.getInscricao().getId(),
                pontuacaoCriterio.isPublico(),
                pontuacaoCriterio.getCriadoEm(),
                pontuacaoCriterio.getAtualizadoEm());
    }
}
