package com.rb.web2.domain.agenda.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record AgendaRequestDTO(
        @NotNull(message = "A data de início da vigência é obrigatória.") LocalDate inicioVigencia,

        @NotNull(message = "A data de fim da vigência é obrigatória.") LocalDate fimVigencia,

        @NotNull(message = "A data de início das inscrições é obrigatória.") @Future(message = "A data de início das inscrições deve estar no futuro.") LocalDate inicioInscricao,

        @NotNull(message = "A data de término das inscrições é obrigatória.") @Future(message = "A data de término das inscrições deve estar no futuro.") LocalDate fimInscricao,

        @NotNull(message = "A data de homologação é obrigatória.") @Future(message = "A data de homologação deve estar no futuro.") LocalDate inicioHomologacao,

        @NotNull(message = "A data de homologação é obrigatória.") @Future(message = "A data de homologação deve estar no futuro.") LocalDate fimHomologacao,

        @NotNull(message = "A data de início do recurso é obrigatória.") @Future(message = "A data de início do recurso deve estar no futuro.") LocalDate inicioRecurso,

        @NotNull(message = "A data de término do recurso é obrigatória.") @Future(message = "A data de término do recurso deve estar no futuro.") LocalDate fimRecurso,

        @NotNull(message = "A data do resultado preliminar é obrigatória.") @Future(message = "A data do resultado preliminar deve estar no futuro.") LocalDate resultadoPreliminar,

        @NotNull(message = "A data do resultado final é obrigatória.") @Future(message = "A data do resultado final deve estar no futuro.") LocalDate resultadoFinal,

        @NotNull(message = "O prazo de convocação é obrigatório.") @Future(message = "O prazo de convocação deve estar no futuro.") LocalDate prazoConvocacao) {
    public boolean isConsistent() {
        return inicioVigencia.isBefore(fimVigencia) &&
                inicioInscricao.isBefore(fimInscricao) &&
                fimInscricao.isBefore(inicioHomologacao) &&
                inicioHomologacao.isBefore(fimHomologacao) &&
                fimHomologacao.isBefore(inicioRecurso) &&
                inicioRecurso.isBefore(fimRecurso) &&
                fimRecurso.isBefore(resultadoPreliminar) &&
                resultadoPreliminar.isBefore(resultadoFinal) &&
                resultadoFinal.isBefore(prazoConvocacao);
    }
}
