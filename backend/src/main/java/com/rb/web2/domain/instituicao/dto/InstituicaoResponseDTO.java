package com.rb.web2.domain.instituicao.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rb.web2.domain.instituicao.Instituicao;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;

public record InstituicaoResponseDTO(
    String id,
    String nome,
    String local,
    // String cnpj,
    List<String> processosIds,
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
                instituicao.getProcessos() != null ? 
                        instituicao.getProcessos().stream()
                        .map(ProcessoSeletivo::getId) // Mapeando os IDs dos processos
                        .collect(Collectors.toList()) :
                        new ArrayList<>(),
                instituicao.isAtivo(),
                instituicao.getCriadoEm(),
                instituicao.getAtualizadoEm()
        );
    }

    public static String getId(InstituicaoResponseDTO instituicao) {
        return instituicao.id();
    }
}
