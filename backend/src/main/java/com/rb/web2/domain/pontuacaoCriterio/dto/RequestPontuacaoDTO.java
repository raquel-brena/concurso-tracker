package com.rb.web2.domain.pontuacaoCriterio.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;

public record RequestPontuacaoDTO(
    BigDecimal nota,

    @NotEmpty(message = "CriterioId cannot be empty")
    String criterioId,

    @NotEmpty(message = "InscricaoId cannot be empty")
    String inscricaoId
) {
}
