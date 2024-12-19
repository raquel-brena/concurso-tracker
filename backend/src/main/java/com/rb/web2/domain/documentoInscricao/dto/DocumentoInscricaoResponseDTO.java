package com.rb.web2.domain.documentoInscricao.dto;

import java.time.LocalDateTime;

import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.documentoInscricao.DocumentoInscricao;
import com.rb.web2.domain.inscricao.dto.InscricaoResponseDTO;

public record DocumentoInscricaoResponseDTO(
        Long id,
        DocumentoResponseDTO documento,
        InscricaoResponseDTO inscricao,
        boolean homologado,
        String motivoRejeicao,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {

    // Método estático para converter a entidade DocumentoInscricao em DTO
    public static DocumentoInscricaoResponseDTO from(DocumentoInscricao documentoInscricao) {
        return new DocumentoInscricaoResponseDTO(
                documentoInscricao.getId(),
                DocumentoResponseDTO.from(documentoInscricao.getDocumento()),
                InscricaoResponseDTO.from(documentoInscricao.getInscricao()),
                documentoInscricao.isHomologado(),
                documentoInscricao.getMotivoRejeicao(),
                documentoInscricao.getCriadoEm(),
                documentoInscricao.getAtualizadoEm()
        );
    }
}
