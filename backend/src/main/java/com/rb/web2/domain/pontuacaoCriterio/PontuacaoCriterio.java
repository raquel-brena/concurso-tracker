package com.rb.web2.domain.pontuacaoCriterio;

import java.util.List;

import com.rb.web2.domain.pontuacaoCriterio.PontuacaoCriterio;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.candidateApplication.CandidateApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "pontuacao_criterio")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PontuacaoCriterio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal nota;
    
    @ManyToOne
    @JoinColumn(name = "criterio_id", nullable = false)
    private CriterioAvaliacao criterio;  // Associando com a classe CriterioAvaliacao

    @ManyToOne
    @JoinColumn(name = "inscricao_id", nullable = false)
    private CandidateApplication candidateApplication;

    @Column(nullable = false)
    private boolean ehPublico = false;

    @Column(nullable = false)
    private boolean ativo = true; // É definido como true antes de ser salvo no banco de dados

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criado_em;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizado_em;

    @PrePersist
    @PreUpdate
    private void validarNota() {
        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 100.");
        }
    }
}