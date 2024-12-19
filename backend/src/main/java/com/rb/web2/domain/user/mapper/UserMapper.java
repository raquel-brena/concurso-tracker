package com.rb.web2.domain.user.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;
import com.rb.web2.domain.documento.mapper.DocumentoMapper;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.ResponseUserDTO;

public class UserMapper {

    // public static CriterioAvaliacao toEntity(RequestCriterioDTO dto) {
    // CriterioAvaliacao processo = new CriterioAvaliacao();
    // processo.setTitulo(dto.titulo());
    // processo.setDescricao(dto.descricao());
    // processo.setValidadeMeses(dto.validade());
    // processo.setTemporario(dto.temporario());
    // processo.setLinkEdital(dto.linkEdital());
    // return processo;
    // }

    public static ResponseUserDTO toResponseUserDTO(User user) {
        if (user == null) {
            return null;
        }
        List<DocumentoResponseDTO> documentos = user.getDocumentos()
                .stream()
                .map(documento -> DocumentoMapper.toDocumentoResponseDTO(documento))
                .toList();

        return new ResponseUserDTO(
                user.isAtivo(),
                user.getNome(),
                user.getEmail(),
                user.getCpf(),
                user.getTelefone(),
                documentos,
                user.getPerfil().getPermissoes().stream()
                        .map(permissao -> permissao.toString())
                        .collect(Collectors.toList()));
    }
}
