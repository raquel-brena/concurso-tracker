package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;

public record UpdateProcessoDTO(
        String titulo,
        String descricao,
        int validade,
        boolean temporario,
        String linkEdital,
        List<Long> vagasIds,
        List<Long> criteriosIds,
        Long agendaId,
        List<String> documentoNecessarios,
        List<String> comissaoOrganizadoraIds,
        List<String> participantesIds) {
}
