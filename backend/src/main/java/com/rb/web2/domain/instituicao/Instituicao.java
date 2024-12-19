package com.rb.web2.domain.instituicao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "instituicoes")
@Entity(name = "instituicoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Instituicao { 
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; 

    @Column(nullable = false)
    private String nome;

    @Column
    private String local;

    @ManyToMany
    private List<ProcessoSeletivo> processos;

    @Column(nullable = false)
    private boolean ativo = true; // É definido como true antes de ser salvo no banco de dados

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    public Instituicao(String name, String location) {
        this.nome = name;
        this.local = location;
        this.ativo = true;
    }
}