package com.rb.web2.domain.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.user.User;

public record ResponseUserDTO(
        boolean ativo,
        String nome,
        String email,
        String cpf,
        String telefone,
        List<DocumentoResponseDTO> documentos,
        List<String> permissoes) {
  
}
