package com.rb.web2.domain.criterioAvaliacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestCriterioDTO(
    @NotBlank(message="O nome do critério não pode ser vazio.") 
    String nome,

    @NotNull(message="O peso do critério não pode ser nulo.") 
    int peso,

    @NotNull(message="O processo seletivo não pode ser nulo.")
    String processoSeletivoId
) {}
