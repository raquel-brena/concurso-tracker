package com.rb.web2.domain.agenda.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;

public record AgendaDTO (
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