package com.rb.web2.domain.cargo.dto;
public record CargoRequestDTO(
    String nome,
    String descricao,
    float remuneracao, 
    boolean temporario
) {
}
