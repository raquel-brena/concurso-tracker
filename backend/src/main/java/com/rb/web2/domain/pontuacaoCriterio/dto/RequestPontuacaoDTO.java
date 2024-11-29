package com.rb.web2.domain.pontuacaoCriterio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RequestPontuacaoDTO(
        String id,
        BigDecimal nota,
        String criterioId,
        String candidateApplicationId,
        boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
