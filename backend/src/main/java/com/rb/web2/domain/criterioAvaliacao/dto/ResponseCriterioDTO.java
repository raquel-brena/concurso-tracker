package com.rb.web2.domain.criterioAvaliacao.dto;

import java.time.LocalDateTime;

public record ResponseCriterioDTO (
    String nome, 
    int peso,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
    
) {
    
}
