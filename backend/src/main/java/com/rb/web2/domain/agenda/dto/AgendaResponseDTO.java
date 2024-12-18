package com.rb.web2.domain.agenda.dto;

import java.time.LocalDate;

public record AgendaResponseDTO(
        Long id,
        boolean ativo,
        LocalDate inicioVigencia,
        LocalDate fimVigencia,
        LocalDate inicioInscricao,
        LocalDate fimInscricao,
        LocalDate homologacao,
        LocalDate inicioRecurso,
        LocalDate fimRecurso,
        LocalDate resultadoPreliminar,
        LocalDate resultadoFinal,
        LocalDate prazoConvocacao) {

}
