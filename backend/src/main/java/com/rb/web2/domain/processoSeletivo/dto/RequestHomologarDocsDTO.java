package com.rb.web2.domain.processoSeletivo.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestHomologarDocsDTO(
        @NotBlank(message = "O processo seletivo não pode ser vazio") String processoSeletivoID) {

}
