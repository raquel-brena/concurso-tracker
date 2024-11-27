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

    @Column(nullable = false)
    private boolean ativo;

    private LocalDate inicioVigencia;

    private LocalDate fimVigencia;

    private LocalDate inicioInscricao;

    private LocalDate fimInscricao;

    private LocalDate homologacao;

    private LocalDate inicioRecurso;

    private LocalDate fimRecurso;

    private LocalDate resultadoPreliminar;
    private LocalDate resultadoFinal;

    private LocalDate prazoConvocacao;

    @OneToOne(mappedBy = "agenda")
    private ProcessoSeletivo processoSeletivo;

    @Column(updatable = false)
    private LocalDate criadoEm;

    private LocalDate atualizadoEm;

}