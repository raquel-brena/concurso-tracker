package com.rb.web2.domain.documento.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rb.web2.domain.documento.Documento;

public record DocumentoResponseDTO(
        String nome,
        String descricao,
        String downloadUrl,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime criadoEm,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime atualizadoEm) {

    public static DocumentoResponseDTO from(Documento documento) {
        return new DocumentoResponseDTO(
                documento.getNome(),
                documento.getDescricao(),
                documento.getDownloadUrl(),
                documento.getCriadoEm(),
                documento.getAtualizadoEm());
    }

    public static String getId(Documento documento) {
        return documento.getId().toString();
    }
}