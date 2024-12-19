package com.rb.web2.domain.documentoInscricao.dto;

import jakarta.validation.constraints.NotBlank;

public record DocInscricaoRequestDTO (
    @NotBlank(message = "O documento não pode ser vazio.")
    Long documentoId,

    @NotBlank(message = "A inscrição não pode ser vazia.")
    String inscricaoId,

    @NotBlank(message = "O campo 'homologado' não pode ser vazio.")
    boolean homologado, 
    String motivoRejeicao
) {
    
}
