package com.rb.web2.domain.processoSeletivo.mapper;

import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.ProcessoRequestDTO;

public class ProcessoSeletivoMapper {

        public static ProcessoSeletivo toEntity(ProcessoRequestDTO dto) {
                ProcessoSeletivo processo = new ProcessoSeletivo();
                processo.setTitulo(dto.titulo());
                processo.setDescricao(dto.descricao());
                processo.setValidadeMeses(dto.validade());
                processo.setTemporario(dto.temporario());
                // processo.setLinkEdital(dto.linkEdital());
                return processo;
        }
}
