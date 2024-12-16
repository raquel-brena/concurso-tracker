package com.rb.web2.domain.documento.mapper;

import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.documento.dto.DocumentoResponseDTO;

public class DocumentoMapper {

    // public static CriterioAvaliacao toEntity(RequestCriterioDTO dto) {
    //     CriterioAvaliacao processo = new CriterioAvaliacao();
    //     processo.setTitulo(dto.titulo());
    //     processo.setDescricao(dto.descricao());
    //     processo.setValidadeMeses(dto.validade());
    //     processo.setTemporario(dto.temporario());
    //     processo.setLinkEdital(dto.linkEdital());
    //     return processo;
    // }

    public static DocumentoResponseDTO toDocumentoResponseDTO(Documento documento) {

        return new DocumentoResponseDTO(
            documento.getNome(),
            documento.getDescricao(),
            documento.getDownloadUrl(),
            documento.getCriado_em(),
            documento.getAtualizado_em()

        );
    }
}
