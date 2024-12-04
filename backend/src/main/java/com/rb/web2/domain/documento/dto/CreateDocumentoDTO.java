package com.rb.web2.domain.documento.dto;
public record CreateDocumentoDTO (
    String nome, 
    String descricao, 
    String userId,
    String processoId) {

}
