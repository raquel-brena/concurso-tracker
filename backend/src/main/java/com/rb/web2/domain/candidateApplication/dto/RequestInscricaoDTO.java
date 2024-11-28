package com.rb.web2.domain.candidateApplication.dto;

import java.time.LocalDateTime;
import java.util.List;

public record RequestInscricaoDTO(
    String id,
    String candidateId,  // ID do candidato, pode ser o id de 'User'
    String processoSeletivoId,  // ID do processo seletivo, pode ser o id de 'ProcessoSeletivo'
    String jobPosition,
    LocalDateTime applicationDate,
    boolean ativo,
    LocalDateTime criadoEm,
    LocalDateTime atualizadoEm,
    List<String> avaliacoes  // Lista de IDs ou alguma representação de 'CriterioAvaliacao'
) { }
