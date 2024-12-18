package com.rb.web2.domain.inscricao.dto;

import java.time.LocalDateTime;

public record ResponseInscricaoDTO(
        String id,
        String candidatoId,
        Long vagaId,
        LocalDateTime deletadoEm) {

}
