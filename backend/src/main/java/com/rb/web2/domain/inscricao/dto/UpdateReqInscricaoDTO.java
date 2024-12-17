package com.rb.web2.domain.inscricao.dto;

import java.util.List;

public record UpdateReqInscricaoDTO(
    Long vagaId,
    String processoSeletivoId,
    Boolean ativo,
    List<Long> avaliacoes
) {
}