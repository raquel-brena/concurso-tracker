package com.rb.web2.domain.resultado;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.rb.web2.domain.etapa.Etapa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resultados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "etapa_id", nullable = false)
    private Etapa etapa;

    private String status; 

    @Column(name = "divulgado_em", updatable = false)
    @CreationTimestamp
    private LocalDateTime divulgadoEm;

    @Column(name = "observacoes")
    private String observacoes; 
}
