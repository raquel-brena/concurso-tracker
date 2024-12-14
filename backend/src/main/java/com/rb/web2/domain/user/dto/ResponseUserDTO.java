package com.rb.web2.domain.user.dto;

import java.util.List;

import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;

public record ResponseUserDTO (
    boolean ativo, 
    String nome,
    String email,
    String cpf,
    String telefone,
    String cargo,
    List<DocumentoResponseDTO> documentos
) {
    
}
