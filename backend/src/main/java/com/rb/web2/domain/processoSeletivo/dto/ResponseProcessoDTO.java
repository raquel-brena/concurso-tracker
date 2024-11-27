package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.criterioAvaliacao.dto.ResponseCriterioDTO;
import com.rb.web2.domain.user.dto.ResponseUserDTO;

import jakarta.validation.constraints.NotNull;

public record ResponseProcessoDTO(
                @NotNull String titulo,
                String descricao,
                @NotNull int validade,
                @NotNull boolean temporario,
                String linkEdital,
                List<ResponseCriterioDTO> criteriosIds,
                Agenda agenda,
                List<String> documentoNecessarios,
                List<ResponseUserDTO> comissaoOrganizadora,
                List<ResponseUserDTO> participantes) {
}
