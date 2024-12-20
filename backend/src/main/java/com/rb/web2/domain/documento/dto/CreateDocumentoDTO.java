package com.rb.web2.domain.documento.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDocumentoDTO (
    @NotBlank(message = "O nome do documento não pode ser vazio.")
    String nome,
    String descricao, 

    @NotBlank(message = "O usuário não pode ser vazio.")
    String userId,

    @NotBlank(message = "O processo não pode ser vazio.")
    String processoId) {

}
