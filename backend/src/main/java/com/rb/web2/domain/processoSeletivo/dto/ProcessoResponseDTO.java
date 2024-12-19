package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;
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
        List<DocumentoResponseDTO> editais,
        AgendaResponseDTO agenda,
        List<String> documentoNecessarios,
        List<UserResponseDTO> comissaoOrganizadora,
        List<VagaResponseDTO> vagas,
        List<InstituicaoResponseDTO> instituicoes) {

    public static ProcessoResponseDTO from(ProcessoSeletivo processoSeletivo) {
        return new ProcessoResponseDTO(
                processoSeletivo.getId(),
                processoSeletivo.getTitulo(),
                processoSeletivo.getDescricao(),
                processoSeletivo.getValidadeMeses(),
                processoSeletivo.isTemporario(),
                // Mapeando a lista de documentos para o DTO
                processoSeletivo.getEditais().stream()
                        .map(DocumentoResponseDTO::from)
                        .collect(Collectors.toList()),
                // Mapeando a agenda para o DTO
                AgendaResponseDTO.from(processoSeletivo.getAgenda()),
                processoSeletivo.getDocumentosNecessarios(),
                // Mapeando a lista de comissão organizadora para o DTO
                processoSeletivo.getComissaoOrganizadora().stream()
                        .map(UserResponseDTO::from)
                        .collect(Collectors.toList()),
                // Mapeando a lista de vagas para o DTO
                processoSeletivo.getVagas().stream()
                        .map(VagaResponseDTO::from)
                        .collect(Collectors.toList()),
                // Mapeando a lista de instituições para o DTO
                processoSeletivo.getInstituicoes().stream()
                        .map(InstituicaoResponseDTO::from)
                        .collect(Collectors.toList()));
    }
}
