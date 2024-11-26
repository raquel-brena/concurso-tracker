package com.rb.web2.domain.vaga;

import com.rb.web2.domain.formacao.Formacao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

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
@Table(name = "vagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    private ProcessoSeletivo processoSeletivo;
    
    //mudar para cargo
    @ManyToOne
    @JoinColumn(name = "formacao_id", nullable = false)
    private Formacao formacao;

    private int quantidade;

    private boolean reserva;

    private float remuneracao;

    private String descricao;
    
    private float taxaInscricao;
}

