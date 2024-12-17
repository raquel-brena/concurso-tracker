package com.rb.web2.domain.pontuacaoCriterio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.inscricao.Inscricao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "pontuacao_criterio")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PontuacaoCriterio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal nota;

    @ManyToOne
    @JoinColumn(name = "criterio_id", nullable = false)
    private CriterioAvaliacao criterio;

    @ManyToOne
    @JoinColumn(name = "inscricao_id", nullable = false)
    private Inscricao inscricao;

    @Column(nullable = false)
    private boolean publico = false;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    @PrePersist
    @PreUpdate
    private void validarNota() {
        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 100.");
        }
    }
}