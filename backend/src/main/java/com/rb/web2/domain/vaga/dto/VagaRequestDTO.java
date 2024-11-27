package com.rb.web2.domain.vaga.dto;

public record VagaRequestDTO(
    String processoSeletivoId, 
    Long formacaoId, 
    int quantidade, 
    boolean reserva, 
    float remuneracao, 
    String descricao, 
    float taxaInscricao, 
    boolean ativo
) {
}
