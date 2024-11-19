package com.rb.web2.domain.agenda;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

@Entity
@Table(name = "agendas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data de início da vigência é obrigatória.")
    private LocalDate inicioVigencia;

    @NotNull(message = "A data de fim da vigência é obrigatória.")
    private LocalDate fimVigencia;

    @NotNull(message = "A data de início das inscrições é obrigatória.")
    @Future(message = "A data de início das inscrições deve estar no futuro.")
    private LocalDate inicioInscricao;

    @NotNull(message = "A data de término das inscrições é obrigatória.")
    @Future(message = "A data de término das inscrições deve estar no futuro.")
    private LocalDate fimInscricao;

    @NotNull(message = "A data de homologação é obrigatória.")
    @Future(message = "A data de homologação deve estar no futuro.")
    private LocalDate homologacao;

    @NotNull(message = "A data de início do recurso é obrigatória.")
    @Future(message = "A data de início do recurso deve estar no futuro.")
    private LocalDate inicioRecurso;

    @NotNull(message = "A data de término do recurso é obrigatória.")
    @Future(message = "A data de término do recurso deve estar no futuro.")
    private LocalDate fimRecurso;

    @NotNull(message = "A data do resultado preliminar é obrigatória.")
    @Future(message = "A data do resultado preliminar deve estar no futuro.")
    private LocalDate resultadoPreliminar;

    @NotNull(message = "A data do resultado final é obrigatória.")
    @Future(message = "A data do resultado final deve estar no futuro.")
    private LocalDate resultadoFinal;

    @NotNull(message = "O prazo de convocação é obrigatório.")
    @Future(message = "O prazo de convocação deve estar no futuro.")
    private LocalDate prazoConvocacao;

    @OneToOne(mappedBy = "agenda")
    private ProcessoSeletivo processoSeletivo;

    public boolean isConsistent() {
        return inicioVigencia.isBefore(fimVigencia) &&
                inicioInscricao.isBefore(fimInscricao) &&
                fimInscricao.isBefore(homologacao) &&
                homologacao.isBefore(inicioRecurso) &&
                inicioRecurso.isBefore(fimRecurso) &&
                fimRecurso.isBefore(resultadoPreliminar) &&
                resultadoPreliminar.isBefore(resultadoFinal) &&
                resultadoFinal.isBefore(prazoConvocacao);
    }

}