package com.rb.web2.domain.inscricao.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record UpdateInscricaoDTO(
    @NotBlank(message = "O candidato não pode ser vazio.")
    Long vagaId,
    List<Long> avaliacoes
) {
}