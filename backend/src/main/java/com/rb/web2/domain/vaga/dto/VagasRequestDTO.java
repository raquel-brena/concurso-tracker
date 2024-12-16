package com.rb.web2.domain.vaga.dto;

public record VagasRequestDTO(
    String processoSeletivoId, 
    Long cargoId, 
    int quantidade, 
    String descricao, 
    float taxaInscricao, 
    boolean ativo
) {
}
