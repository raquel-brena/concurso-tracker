package com.rb.web2.domain.agenda.dto;

import java.time.LocalDate;
import java.util.Map;

import com.rb.web2.domain.agenda.Agenda;

public record AgendaResponseDTO(
                Long id,
                boolean ativo,
                LocalDate inicioVigencia,
                LocalDate fimVigencia,
                LocalDate inicioInscricao,
                LocalDate fimInscricao,
                LocalDate inicioHomologacao,
                LocalDate fimHomologacao,
                LocalDate inicioRecurso,
                LocalDate fimRecurso,
                LocalDate resultadoPreliminar,
                LocalDate resultadoFinal,
                LocalDate prazoConvocacao) {
        public static AgendaResponseDTO from(Agenda agenda) {
                return new AgendaResponseDTO(
                                agenda.getId(),
                                agenda.isAtivo(),
                                agenda.getInicioVigencia(),
                                agenda.getFimVigencia(),
                                agenda.getInicioInscricao(),
                                agenda.getFimInscricao(),
                                agenda.getInicioHomologacao(),
                                agenda.getFimHomologacao(),
                                agenda.getInicioRecurso(),
                                agenda.getFimRecurso(),
                                agenda.getResultadoPreliminar(),
                                agenda.getResultadoFinal(),
                                agenda.getPrazoConvocacao());
        }

        public Map<String, Long> getId() {
                return Map.of("id", id);
        }
}
