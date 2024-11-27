package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record RequestProcessoDTO(
        @NotNull String titulo,
        String descricao,
        @NotNull int validade,
        @NotNull boolean temporario,
        String linkEdital,
        List<Long> criteriosIds,
        Long agendaId,
        List<String> documentoNecessarios,
        List<String> comissaoOrganizadoraIds,
        List<String> participantesIds) {
}
