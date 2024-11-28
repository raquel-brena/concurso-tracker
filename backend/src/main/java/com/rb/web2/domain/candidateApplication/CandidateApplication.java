 
package com.rb.web2.domain.candidateApplication;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.candidateApplication.CandidateApplicationId;
import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapsId;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import java.util.List;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "candidate_applications")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CandidateApplication implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    //@EmbeddedId
    //private CandidateApplicationId id;  // Chave composta

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User candidate;  // Associando com a classe User
    //@MapsId("user_id") // Mapeia a chave primária compartilhada

    @ManyToOne
    @JoinColumn(name = "processo_seletivo_id", referencedColumnName = "id", nullable = false)
    private ProcessoSeletivo processoSeletivo;  // Agora vinculado ao Processo Seletivo
    //@MapsId("processo_seletivo_id") // Mapeia a chave primária compartilhada

    @Column(nullable = false)
    private String jobPosition;  // Cargo desejado

    @Column(nullable = false)
    private LocalDateTime applicationDate;  // Data de inscrição

    @Column(nullable = false)
    private boolean ativo = true; // É definido como true antes de ser salvo no banco de dados

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criado_em;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizado_em;

    @ManyToMany
    @JoinTable(
        name = "pontuacao_criterio",
        joinColumns = {
            @JoinColumn(name = "candidate_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "processo_seletivo_id", referencedColumnName = "processo_seletivo_id")
        },
        inverseJoinColumns = @JoinColumn(name = "criterio_id")
    )
    private List<CriterioAvaliacao> avaliacoes;

    public CandidateApplication(User candidate, String jobPosition, ProcessoSeletivo processoSeletivo) {
        this.candidate = candidate;
        this.jobPosition = jobPosition;
        this.applicationDate = LocalDateTime.now();
        this.processoSeletivo = processoSeletivo;
    }
}