package com.rb.web2.domain.cargo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CargoRequestDTO(
        @NotBlank(message = "O nome do cargo não pode ser vazio.") String nome,
        String descricao,

        @NotNull(message = "A remuneração não pode ser nula.") @Positive(message = "A remuneração deve ser um valor positivo.") float remuneracao,

        @NotNull(message = "Cargo temporário não pode ser nulo.") boolean temporario) {
}
