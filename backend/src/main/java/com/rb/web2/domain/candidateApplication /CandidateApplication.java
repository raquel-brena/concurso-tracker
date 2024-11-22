package com.rb.web2.domain.candidateApplication;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Table(name = "candidate_applications")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CandidateApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;  // Chave composta

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @MapsId // Mapeia a chave primária compartilhada
    private User candidate;  // Associando com a classe User

    @ManyToOne
    @JoinColumn(name = "processo_seletivo_id", nullable = false)
    @MapsId // Mapeia a chave primária compartilhada
    private ProcessoSeletivo processoSeletivo;  // Agora vinculado ao Processo Seletivo

    @Column(nullable = false)
    private String jobPosition;  // Cargo desejado

    @Column(nullable = false)
    private boolean isActive;  // Indica se a candidatura está ativa

    @Column(nullable = false)
    private LocalDateTime applicationDate;  // Data de inscrição

    public CandidateApplication(User candidate, String jobPosition, String coverLetter, boolean isActive, ProcessoSeletivo processoSeletivo) {
        this.candidate = candidate;
        this.jobPosition = jobPosition;
        this.coverLetter = coverLetter;
        this.isActive = isActive;
        this.applicationDate = LocalDateTime.now();
        this.processoSeletivo = processoSeletivo;
    }
}
