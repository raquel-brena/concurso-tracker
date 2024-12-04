package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;

import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.criterioAvaliacao.dto.ResponseCriterioDTO;
import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.user.dto.ResponseUserDTO;

import jakarta.validation.constraints.NotNull;

public record ResponseProcessoDTO(
    @NotNull String id,
                @NotNull String titulo,
                String descricao,
                @NotNull int validade,
                @NotNull boolean temporario,
                List<DocumentoResponseDTO> editais,
                List<ResponseCriterioDTO> criteriosIds,
                AgendaDTO agenda,
                List<String> documentoNecessarios,
                List<ResponseUserDTO> comissaoOrganizadora,
                List<ResponseUserDTO> participantes) {
}
