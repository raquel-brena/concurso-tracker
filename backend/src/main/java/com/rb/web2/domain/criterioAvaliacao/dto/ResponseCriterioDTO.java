package com.rb.web2.domain.criterioAvaliacao.dto;

import java.time.LocalDateTime;

public record ResponseCriterioDTO (
    boolean ativo,
    String nome, 
    int peso,
    LocalDateTime criado_em,
    LocalDateTime atualizado_em
    
) {
    
}
