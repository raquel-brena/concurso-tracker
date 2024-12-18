package com.rb.web2.domain.vaga.dto;

import java.time.LocalDateTime;

import com.rb.web2.domain.cargo.Cargo;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.vaga.Vaga;

public record VagaResponseDTO(
    Long id,
    ProcessoSeletivo processoSeletivo,
    Cargo cargo,
    int quantidade,
    String descricao,
    float taxaInscricao,
    boolean ativo,
    LocalDateTime criado_em,
    LocalDateTime atualizado_em
) {
    public static VagaResponseDTO from(Vaga vaga) {
        return new VagaResponseDTO(
            vaga.getId(),
            vaga.getProcessoSeletivo(),
            vaga.getCargo(),
            vaga.getQuantidade(),
            vaga.getDescricao(),
            vaga.getTaxaInscricao(),
            vaga.isAtivo(),
            vaga.getCriadoEm(),
            vaga.getAtualizadoEm()
        );
    }
}
