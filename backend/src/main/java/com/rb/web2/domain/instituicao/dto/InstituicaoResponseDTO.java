package com.rb.web2.domain.instituicao.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.rb.web2.domain.instituicao.Instituicao;
import com.rb.web2.domain.processoSeletivo.dto.ProcessoResponseDTO;

public record InstituicaoResponseDTO(
    String id,
    String nome,
    String local,
    // String cnpj,
    List<ProcessoResponseDTO> processos,
    boolean ativo,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm
) {
    // Método estático 'from' para converter a entidade 'Instituicao' para o DTO
    public static InstituicaoResponseDTO from(Instituicao instituicao) {
        return new InstituicaoResponseDTO(
                instituicao.getId(),
                instituicao.getNome(),
                instituicao.getLocal(),
                // instituicao.getCnpj(),
                instituicao.getProcessos().stream()
                        .map(ProcessoResponseDTO::from)
                        .collect(Collectors.toList()),
                instituicao.isAtivo(),
                instituicao.getCriadoEm(),
                instituicao.getAtualizadoEm()
        );
    }

    public static String getId(InstituicaoResponseDTO instituicao) {
        return instituicao.id();
    }
}
