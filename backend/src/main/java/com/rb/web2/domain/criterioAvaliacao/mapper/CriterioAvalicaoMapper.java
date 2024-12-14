package com.rb.web2.domain.criterioAvaliacao.mapper;

import java.util.List;

import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;
import com.rb.web2.domain.criterioAvaliacao.dto.ResponseCriterioDTO;
import com.rb.web2.domain.documento.Documento;
import com.rb.web2.domain.user.User;

import io.micrometer.core.ipc.http.HttpSender.Response;

public class CriterioAvalicaoMapper {

    // public static CriterioAvaliacao toEntity(RequestCriterioDTO dto) {
    //     CriterioAvaliacao processo = new CriterioAvaliacao();
    //     processo.setTitulo(dto.titulo());
    //     processo.setDescricao(dto.descricao());
    //     processo.setValidadeMeses(dto.validade());
    //     processo.setTemporario(dto.temporario());
    //     processo.setLinkEdital(dto.linkEdital());
    //     return processo;
    // }

    public static ResponseCriterioDTO toResponseCriterioDTO(CriterioAvaliacao criterio) {
        return new ResponseCriterioDTO(
            criterio.getNome(), 
            criterio.getPeso(), 
            criterio.getCriado_em(), 
            criterio.getAtualizado_em());
    }
}
