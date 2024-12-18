package com.rb.web2.domain.vaga.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VagaUpdateDTO {
    @NotNull
    private Long id;

    @NotNull
    private String processoSeletivoId;

    @NotNull
    private Long cargoId;

    @NotNull
    private int quantidade;

    @NotNull
    private String descricao;

    @NotNull
    private float taxaInscricao;

    private boolean ativo;
}
