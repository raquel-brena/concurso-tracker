package com.rb.web2.domain.candidateApplication;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CandidateApplicationId implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "processo_seletivo_id")
    private String processoSeletivoId;
}
