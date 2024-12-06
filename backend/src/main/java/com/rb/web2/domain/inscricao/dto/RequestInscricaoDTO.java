package com.rb.web2.domain.inscricao.dto;

public record RequestInscricaoDTO(
    String candidateId,  // ID do candidato, pode ser o id de 'User'
    String processoSeletivoId,  // ID do processo seletivo, pode ser o id de 'ProcessoSeletivo'
    String jobPosition  // Cargo
){}
