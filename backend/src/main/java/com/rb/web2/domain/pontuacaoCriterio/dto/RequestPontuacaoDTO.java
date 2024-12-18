package com.rb.web2.domain.pontuacaoCriterio.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

public record RequestPontuacaoDTO(
        @NotEmpty(message = "A nota não pode ser vazia.") @DecimalMin(value = "0.0", message = "A nota não pode ser menor que 0.") @DecimalMax(value = "100.0", message = "A nota não pode ser maior que 100.") BigDecimal nota,

        @NotEmpty(message = "O critério não pode ser vazio.") Long criterioId,

        @NotEmpty(message = "A inscrição não pode ser vazia.") String inscricaoId) {
}
