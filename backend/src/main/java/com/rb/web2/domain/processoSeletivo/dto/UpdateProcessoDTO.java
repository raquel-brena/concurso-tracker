package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProcessoDTO(
        @NotBlank(message = "O título do processo seletivo não pode ser vazio.")
        String titulo,

        String descricao,

        @NotNull(message = "A validade do processo seletivo é obrigatória.")
        int validade,

        @NotNull(message = "O campo 'temporario' é obrigatório.")
        boolean temporario,
        String linkEdital,
        List<Long> vagasIds,
        List<Long> criteriosIds,
        Long agendaId,
        List<String> documentoNecessarios,
        List<String> comissaoOrganizadoraIds,
        List<String> participantesIds) {
}
