package com.rb.web2.domain.inscricao.dto;

import jakarta.validation.constraints.NotNull;

public record InscricaoRequestDTO(
    @NotNull(message = "O candidato não pode ser vazio.")
    String candidatoId,

    @NotNull(message = "A vaga não pode ser vazia.")
    Long vagaId
){}
