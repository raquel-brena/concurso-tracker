package com.rb.web2.domain.documentoInscricao.dto;

public record RequestHomologarDocInscricaoDTO (
    Long documentoInscricaoId, 
    boolean homologado, 
    String motivoRejeicao
)  {}
