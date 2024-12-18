package com.rb.web2.domain.instituicao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "instituicaos")
@Entity(name = "instituicaos")
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
    private String name;

    @Column
    private String location;

    @Column(nullable = false)
    private boolean ativo = true; // É definido como true antes de ser salvo no banco de dados

    @Column(name = "criado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    public Instituicao(String name, String location) {
        this.name = name;
        this.location = location;
        this.ativo = true;
    }
}