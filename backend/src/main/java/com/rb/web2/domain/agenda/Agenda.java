package com.rb.web2.domain.agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private boolean ativo = true;

    private LocalDate inicioVigencia;

    private LocalDate fimVigencia;

    private LocalDate inicioInscricao;

    private LocalDate fimInscricao;

    private LocalDate inicioHomologacao;
    private LocalDate fimHomologacao;

    private LocalDate inicioRecurso;

    private LocalDate fimRecurso;

    private LocalDate resultadoPreliminar;
    private LocalDate resultadoFinal;

    private LocalDate prazoConvocacao;

    @OneToOne(mappedBy = "agenda")
    private ProcessoSeletivo processoSeletivo;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

}