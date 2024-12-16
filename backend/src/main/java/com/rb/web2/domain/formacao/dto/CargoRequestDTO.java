package com.rb.web2.domain.formacao.dto;
public record CargoRequestDTO(
    String nome,
    String descricao,
    float remuneracao, 
    boolean temporario
) {
}
