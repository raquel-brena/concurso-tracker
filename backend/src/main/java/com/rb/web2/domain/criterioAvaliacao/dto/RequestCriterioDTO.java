package com.rb.web2.domain.criterioAvaliacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestCriterioDTO(
    @NotBlank String nome,
    @NotNull int peso,
    @NotNull boolean ativo,
    @NotNull String processoSeletivoId
) {}
