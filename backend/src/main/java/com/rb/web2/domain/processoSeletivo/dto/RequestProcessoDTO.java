package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestProcessoDTO(
        @NotNull(message = "O título é obrigatório.") 
        @NotBlank(message = "O título não pode estar vazio.") 
        String titulo,

        @NotNull(message = "A validade é obrigatória.") 
        Integer validade,

        @NotNull(message = "O campo 'temporario' é obrigatório.") 
        boolean temporario,

        String descricao,
        String linkEdital,
        List<Long> criteriosIds,
        Long agendaId,
        List<String> documentoNecessarios,
        List<String> comissaoOrganizadoraIds,
        List<String> participantesIds) {
}
