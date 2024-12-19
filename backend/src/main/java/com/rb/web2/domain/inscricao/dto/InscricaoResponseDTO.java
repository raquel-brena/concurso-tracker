package com.rb.web2.domain.inscricao.dto;

import java.time.LocalDateTime;

import com.rb.web2.domain.inscricao.Inscricao;

public record InscricaoResponseDTO(
                String id,
                String candidatoId,
                Long vagaId,
                LocalDateTime deletadoEm) {

        public static InscricaoResponseDTO from(Inscricao inscricao) {
                return new InscricaoResponseDTO(
                                inscricao.getId(),
                                inscricao.getCandidato().getId(),
                                inscricao.getVaga().getId(),
                                inscricao.getDeletadoEm()
                );
        }
}
