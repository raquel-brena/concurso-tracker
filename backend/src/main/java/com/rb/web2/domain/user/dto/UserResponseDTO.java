package com.rb.web2.domain.user.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.documento.mapper.DocumentoMapper;
import com.rb.web2.domain.inscricao.dto.InscricaoResponseDTO;
import com.rb.web2.domain.user.User;

public record UserResponseDTO(
                String id,
                boolean ativo,
                String nome,
                String email,
                String cpf,
                String telefone,
                List<DocumentoResponseDTO> documentos,
                List<InscricaoResponseDTO> inscricoes,
                List<String> permissoes) {
        public static UserResponseDTO from(User user) {
                List<DocumentoResponseDTO> documentos = user.getDocumentos()
                                .stream()
                                .map(documento -> DocumentoMapper.toDocumentoResponseDTO(documento))
                                .toList();

                List<InscricaoResponseDTO> inscricoes = user.getInscricoes()
                                .stream()
                                .map(inscricao -> InscricaoResponseDTO.from(inscricao))
                                .toList();

                return new UserResponseDTO(
                                user.getId(),
                                user.isAtivo(),
                                user.getNome(),
                                user.getEmail(),
                                user.getCpf(),
                                user.getTelefone(),
                                documentos,
                                inscricoes,
                                user.getPerfil().getPermissoes() != null ? user.getPerfil().getPermissoes().stream()
                                                .map(permissao -> permissao.toString())
                                                .collect(Collectors.toList()) : List.of());
        }

        public static String getId(User user) {
                return user.getId();
        }
}
