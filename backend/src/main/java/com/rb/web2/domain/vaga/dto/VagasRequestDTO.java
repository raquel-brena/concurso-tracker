package com.rb.web2.domain.vaga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VagasRequestDTO(
    @NotBlank(message = "O campo 'processoSeletivo' não pode ser vazio.")
    String processoSeletivoId, 

    @NotNull(message = "O campo 'cargo' não pode ser vazio.")
    Long cargoId, 

    @NotNull(message = "O campo 'quantidade' não pode ser vazio.") @Positive(message = "O campo 'quantidade' deve ser maior que zero.")
    int quantidade,

    String descricao, 

    @NotNull(message = "O campo 'taxaInscricao' não pode ser vazio.")
    float taxaInscricao
) {
}
