package com.rb.web2.domain.inscricao.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.documentoInscricao.dto.DocumentoInscricaoResponseDTO;
import com.rb.web2.domain.inscricao.Inscricao;

public record InscricaoResponseDTO(
                String id,
                String candidatoId,
                Long vagaId,
                List<DocumentoInscricaoResponseDTO> documentoInscricao,
                boolean estaPaga,
                LocalDateTime criadaEm,
                LocalDateTime deletadoEm) {

        public static InscricaoResponseDTO from(Inscricao inscricao) {

                List<DocumentoInscricaoResponseDTO> documentosInscricao = inscricao.getDocumentosInscricoes().stream()
                                .map(documento -> DocumentoInscricaoResponseDTO.from(documento))
                                .toList();

                return new InscricaoResponseDTO(
                                inscricao.getId(),
                                inscricao.getCandidato().getId(),
                                inscricao.getVaga().getId(),
                                documentosInscricao,
                                inscricao.isEstaPaga(),
                                inscricao.getCriadoEm(),
                                inscricao.getDeletadoEm());
        }
}
