package com.rb.web2.domain.formacao.dto;

import java.time.LocalDateTime;

public record FormacaoRequestDTO(
    Long id,
    String nome,
    String descricao,
    boolean ativo,
    LocalDateTime criado_em,
    LocalDateTime atualizado_em
) {
}
