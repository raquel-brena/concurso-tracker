package com.rb.web2.domain.criterioAvaliacao;

import java.util.List;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.candidateApplication.CandidateApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
public class pontuacaoCriterio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal nota;

    @ManyToOne
    @JoinColumn(name = "criterio_avaliacao_id", nullable = false)
    @MapsId // Mapeia a chave primária compartilhada
    private CriterioAvaliacao criterio;  // Associando com a classe CriterioAvaliacao

    @ManyToOne
    @JoinColumn(name = "candidate_application_id", nullable = false)
    @MapsId // Mapeia a chave primária compartilhada
    private CandidateApplication incricao;  // Agora vinculado ao CandidateApplication

    @Column(nullable = false)
    private boolean ativo = true; // É definido como true antes de ser salvo no banco de dados

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criado_em;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizado_em;
}
