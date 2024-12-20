package com.rb.web2.domain.processoComissao.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestMembroComissaoDTO(
        @NotBlank(message = "O membro da comissão não pode ser vazio.") String processoSeletivoId,

        @NotBlank(message = "O usuário não pode ser vazio.") String userId) {
}
