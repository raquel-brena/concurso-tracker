package com.rb.web2.domain.processoSeletivo;

import java.util.List;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.vaga.Vaga;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private List<Vaga> vagas;

    @OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "agenda_id", referencedColumnName = "id")
private Agenda agenda;
}

