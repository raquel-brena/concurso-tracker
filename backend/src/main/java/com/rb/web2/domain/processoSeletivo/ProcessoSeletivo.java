package com.rb.web2.domain.processoSeletivo;

import java.util.List;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;
import com.fasterxml.jackson.annotation.JsonManagedReference;
// import com.rb.web2.domain.candidateApplication.CandidateApplication;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore; 

@Entity
@Table(name = "processos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProcessoSeletivo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String titulo;

    private String descricao;

    private int validadeMeses;

    private boolean temporario;

    private String linkEdital;

    private List<String> documentosNecessarios;

    @JsonIgnore
    @OneToMany(mappedBy = "processoSeletivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaga> vagas;

    @OneToMany(mappedBy = "processoSeletivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriterioAvaliacao> criterios;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    private Agenda agenda;

    @ManyToMany
    @JoinTable(name = "processo_comissao", joinColumns = @JoinColumn(name = "processo_seletivo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> comissaoOrganizadora;
 
    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "candidate_applications", joinColumns = @JoinColumn(name = "processo_seletivo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participantes;

    @Column(nullable = false)
    private boolean ativo = true; // É definido como true antes de ser salvo no banco de dados

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criado_em;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizado_em;
}
