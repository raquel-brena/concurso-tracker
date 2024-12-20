package com.rb.web2.domain.processoSeletivo.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rb.web2.domain.agenda.dto.AgendaResponseDTO;
import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.instituicao.dto.InstituicaoResponseDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.dto.UserResponseDTO;
import com.rb.web2.domain.vaga.dto.VagaResponseDTO;

import jakarta.validation.constraints.NotNull;

public record ProcessoResponseDTO(
                @NotNull String id,
                @NotNull String titulo,
                String descricao,
                @NotNull int validade,
                @NotNull boolean temporario,
                List<String> editaisIds,
                AgendaResponseDTO agenda,
                List<String> documentoNecessarios,
                List<String> comissaoOrganizadoraIds,
                List<String> vagasIds,
                List<String> instituicoesIds) {

        public static ProcessoResponseDTO from(ProcessoSeletivo processoSeletivo) {
                return new ProcessoResponseDTO(
                                processoSeletivo.getId(),
                                processoSeletivo.getTitulo(),
                                processoSeletivo.getDescricao(),
                                processoSeletivo.getValidadeMeses(),
                                processoSeletivo.isTemporario(),
                                Optional.ofNullable(processoSeletivo.getEditais())
                                                .orElse(Collections.emptyList()) // Lista vazia se for null
                                                .stream()
                                                .map(DocumentoResponseDTO::getId) // Pegando o ID de cada edital
                                                .collect(Collectors.toList()),
                                // Mapeando a agenda para o DTO
                                Optional.ofNullable(processoSeletivo.getAgenda())
                                                .map(AgendaResponseDTO::from) // Mapeando a agenda para o DTO
                                                .orElse(null),
                                processoSeletivo.getDocumentosNecessarios(),
                                // Mapeando os IDs da comissão organizadora
                                Optional.ofNullable(processoSeletivo.getComissaoOrganizadora())
                                                .orElse(Collections.emptyList()) // Lista vazia se for null
                                                .stream()
                                                .map(UserResponseDTO::getId) // Pegando o ID dos membros da comissão
                                                .collect(Collectors.toList()),
                                // Mapeando os IDs das vagas
                                Optional.ofNullable(processoSeletivo.getVagas())
                                                .orElse(Collections.emptyList()) // Lista vazia se for null
                                                .stream()
                                                .map(vaga -> VagaResponseDTO.from(vaga))
                                                .map(VagaResponseDTO::getId) // Pegando o ID das vagas
                                                .collect(Collectors.toList()),
                                // Mapeando os IDs das instituições
                                Optional.ofNullable(processoSeletivo.getInstituicoes())
                                                .orElse(Collections.emptyList()) // Lista vazia se for null
                                                .stream().map(instituicao -> InstituicaoResponseDTO.from(instituicao))
                                                .map(InstituicaoResponseDTO::getId) // Pegando o ID das instituições
                                                .collect(Collectors.toList())

                );
        }

        public static String getId(ProcessoResponseDTO processo) {
                return processo.id();
        }
}
