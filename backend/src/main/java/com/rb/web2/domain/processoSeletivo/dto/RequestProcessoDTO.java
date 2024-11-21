package com.rb.web2.domain.processoSeletivo.dto;

public record RequestProcessoDTO (
    String titulo, 
    String descricao, 
    int validade, 
    boolean temporario, 
    String linkEdital){
    
}
