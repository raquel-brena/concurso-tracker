 
package com.rb.web2.domain.inscricao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inscricoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Inscricao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User candidato;
    
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @ManyToMany
    @JoinTable(
        name = "pontuacao_criterio",
        joinColumns = @JoinColumn(name = "inscricao_id"),
        inverseJoinColumns = @JoinColumn(name = "criterio_id")
    )
    private List<CriterioAvaliacao> avaliacoes;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criado_em;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizado_em;

    @Column(name = "deletado_em")
    private LocalDateTime deletadoEm;
}
