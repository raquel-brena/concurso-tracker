package com.rb.web2.domain.criterioAvaliacao.dto;

import java.time.LocalDateTime;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;

public record ResponseCriterioDTO(
        boolean ativo,
        String nome,
        int peso,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {
    public static ResponseCriterioDTO from(CriterioAvaliacao criterio) {
        return new ResponseCriterioDTO(
                criterio.isAtivo(),
                criterio.getNome(),
                criterio.getPeso(),
                criterio.getCriadoEm(),
                criterio.getAtualizadoEm());
    }

}
