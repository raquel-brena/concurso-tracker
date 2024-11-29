package com.rb.web2.domain.pontuacaoCriterio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;

public record RequestPontuacaoDTO(
    BigDecimal nota,

    @NotEmpty(message = "CriterioId cannot be empty")
    String criterioId,

    @NotEmpty(message = "CandidateApplicationId cannot be empty")
    String candidateApplicationId
) {
}
