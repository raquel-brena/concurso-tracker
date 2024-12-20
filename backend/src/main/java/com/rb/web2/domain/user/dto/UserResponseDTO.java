package com.rb.web2.domain.user.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.user.User;

public record UserResponseDTO(
                String id,
                boolean ativo,
                String nome,
                String email,
                String cpf,
                String telefone,
                List<DocumentoResponseDTO> documentos,
                List<String> permissoes) {
        public static UserResponseDTO from(User user) {
                return new UserResponseDTO(
                                user.getId(),
                                user.isAtivo(),
                                user.getNome(),
                                user.getEmail(),
                                user.getCpf(),
                                user.getTelefone(),
                                user.getDocumentos() != null ? 
                                        user.getDocumentos().stream()
                                                .map(DocumentoResponseDTO::from)
                                                .collect(Collectors.toList()) : 
                                        new ArrayList<>(),
                                user.getPerfil().getPermissoes().stream()
                                        .map(permissao -> permissao.toString())
                                        .collect(Collectors.toList()));
        }

        public static String getId(User user) {
                return user.getId();
        }
}
