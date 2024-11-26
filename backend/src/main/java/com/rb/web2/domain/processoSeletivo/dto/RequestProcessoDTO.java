package com.rb.web2.domain.processoSeletivo.dto;

import java.util.List;

public record RequestProcessoDTO (
    String titulo, 
    String descricao, 
    int validade, 
    boolean temporario, 
    String linkEdital,
    List<String> documentosIds
    ){
}
