package com.rb.web2.domain.instituicao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instituicoes")
@Data
@NoArgsConstructor
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(unique = true)
    private String local;

    @ManyToMany
    private List<ProcessoSeletivo> processos;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "instituicao")
    private List<User> usuarios = new ArrayList<>();

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    public Instituicao(String nome, String local) {
        this.nome = nome;
        this.local = local;
        this.ativo = true;
    }
}