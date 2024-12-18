package com.rb.web2.domain.documentoInscricao.dto;

public record RequestDocInscricaoDTO (
    Long documentoId, 
    String inscricaoId,
    boolean homologado, 
    String motivoRejeicao
) {
    
}
