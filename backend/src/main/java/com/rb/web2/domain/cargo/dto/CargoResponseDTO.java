package com.rb.web2.domain.cargo.dto;

import java.time.LocalDateTime;

import com.rb.web2.domain.cargo.Cargo;

public record CargoResponseDTO(
        Long id,
        String nome,
        String descricao,
        float remuneracao,
        boolean temporario,
        boolean ativo,
        LocalDateTime criado_em,
        LocalDateTime atualizado_em) {
    public static CargoResponseDTO from(Cargo cargo) {
        return new CargoResponseDTO(
                cargo.getId(),
                cargo.getNome(),
                cargo.getDescricao(),
                cargo.getRemuneracao(),
                cargo.isTemporario(),
                cargo.isAtivo(),
                cargo.getCriadoEm(),
                cargo.getAtualizadoEm());
    }
}
