package com.rb.web2.domain.processoSeletivo.mapper;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;

public class ProcessoSeletivoMapper {

    public static ProcessoSeletivo toEntity(RequestProcessoDTO dto) {
        ProcessoSeletivo processo = new ProcessoSeletivo();
        processo.setTitulo(dto.titulo());
        processo.setDescricao(dto.descricao());
        processo.setValidadeMeses(dto.validade());
        processo.setTemporario(dto.temporario());
        processo.setLinkEdital(dto.linkEdital());
        return processo;
    }

    public static RequestProcessoDTO toDTO(ProcessoSeletivo processo) {
        return new RequestProcessoDTO(
            processo.getTitulo(),
            processo.getDescricao(),
            processo.getValidadeMeses(),
            processo.isTemporario(),
            processo.getLinkEdital()
        );
    }
}
