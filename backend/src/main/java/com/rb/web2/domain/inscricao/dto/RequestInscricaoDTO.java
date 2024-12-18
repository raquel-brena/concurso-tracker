package com.rb.web2.domain.inscricao.dto;

public record RequestInscricaoDTO(
    String id, 
    String candidatoId,
    Long vagaId
){}
