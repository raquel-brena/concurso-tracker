package com.rb.web2.domain.processoSeletivo;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.etapa.Etapa;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @OneToMany(mappedBy = "processoSeletivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> editais;

    @ElementCollection
    private List<String> documentosNecessarios;

    @JsonIgnore
    @OneToMany(mappedBy = "processoSeletivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaga> vagas;

    @OneToMany(mappedBy = "processoSeletivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etapa> etapas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    private Agenda agenda;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "processo_comissao", joinColumns = @JoinColumn(name = "processo_seletivo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> comissaoOrganizadora;

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    @Column(name = "deletado_em")
    private LocalDateTime deletadoEm;
}
