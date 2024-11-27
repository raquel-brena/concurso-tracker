package com.rb.web2.domain.documento.dto;

import java.time.LocalDateTime;

public record DocumentoResponseDTO (
    boolean ativo,
    String nome,
    String downloadUrl,
    String tipo,
    LocalDateTime criado_em,
    LocalDateTime atualizado_em
) {
    
}
