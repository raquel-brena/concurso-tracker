 
package com.rb.web2.domain.inscricao;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;

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

@Table(name = "inscricoes")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Inscricao implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User candidate; 

    @ManyToOne
    @JoinColumn(name = "processo_seletivo_id", referencedColumnName = "id", nullable = false)
    private ProcessoSeletivo processoSeletivo;

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
        joinColumns = @JoinColumn(name = "inscricao_id"),  // Chave estrangeira da inscrição
        inverseJoinColumns = @JoinColumn(name = "criterio_id")  // Chave estrangeira do critério de avaliação
    )
    private List<CriterioAvaliacao> avaliacoes;

    public Inscricao(User candidate, String jobPosition, ProcessoSeletivo processoSeletivo) {
        this.candidate = candidate;
        this.jobPosition = jobPosition;
        this.applicationDate = LocalDateTime.now();
        this.processoSeletivo = processoSeletivo;
    }
}