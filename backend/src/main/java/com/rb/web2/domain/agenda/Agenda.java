package com.rb.web2.domain.agenda;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criado_em;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizado_em;

}