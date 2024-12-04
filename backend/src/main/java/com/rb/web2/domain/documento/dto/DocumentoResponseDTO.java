package com.rb.web2.domain.documento.dto;

import java.time.LocalDateTime;

public record DocumentoResponseDTO (
    String nome,
    String downloadUrl,
    String descricao,
    LocalDateTime criado_em,
    LocalDateTime atualizado_em
) {
    
}
