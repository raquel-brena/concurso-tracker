package com.rb.web2.domain.criterioAvaliacao.dto;

import java.time.LocalDateTime;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;

public record CriterioResponseDTO(
        boolean ativo,
        String nome,
        int peso,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {
    public static CriterioResponseDTO from(CriterioAvaliacao criterio) {
        return new CriterioResponseDTO(
                criterio.isAtivo(),
                criterio.getNome(),
                criterio.getPeso(),
                criterio.getCriadoEm(),
                criterio.getAtualizadoEm());
    }

}
