package com.rb.web2.domain.documentoInscricao.dto;

import jakarta.validation.constraints.NotBlank;

public record HomologarDocInscricaoRequestDTO (
    @NotBlank(message = "O documento de inscrição não pode ser vazio.")
    Long documentoInscricaoId, 

    @NotBlank(message = "O campo 'homologado' não pode ser vazio.")
    boolean homologado,
    String motivoRejeicao
)  {}
