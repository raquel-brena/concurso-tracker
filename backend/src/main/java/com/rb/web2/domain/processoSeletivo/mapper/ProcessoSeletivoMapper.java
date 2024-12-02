package com.rb.web2.domain.processoSeletivo.mapper;

import java.util.List;

import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.agenda.mapper.AgendaMapper;
import com.rb.web2.domain.criterioAvaliacao.dto.ResponseCriterioDTO;
import com.rb.web2.domain.criterioAvaliacao.mapper.CriterioAvalicaoMapper;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.processoSeletivo.dto.RequestProcessoDTO;
import com.rb.web2.domain.processoSeletivo.dto.ResponseProcessoDTO;
import com.rb.web2.domain.user.dto.ResponseUserDTO;
import com.rb.web2.domain.user.mapper.UserMapper;

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

    public static ResponseProcessoDTO toResponseProcessoDTO(ProcessoSeletivo processo) {

        List<ResponseCriterioDTO> criteriosDTOs 
        = processo.getCriterios()
            .stream()
            .map(criterio -> CriterioAvalicaoMapper.toResponseCriterioDTO(criterio))
            .toList();

        List<ResponseUserDTO> comissaoOrganizadoraDTOs 
        = processo.getParticipantes()
            .stream()
            .map(user -> UserMapper.toResponseUserDTO(user))
            .toList();
        
        List<ResponseUserDTO> participantesDTOs 
            = processo.getComissaoOrganizadora()
                .stream()
                .map(user -> UserMapper.toResponseUserDTO(user))
                .toList();

        AgendaDTO agenda = AgendaMapper.toDTO(processo.getAgenda());

        return new ResponseProcessoDTO(
                processo.getTitulo(),
                processo.getDescricao(),
                processo.getValidadeMeses(),
                processo.isTemporario(),
                processo.getLinkEdital(),
                criteriosDTOs,
                agenda,
                processo.getDocumentosNecessarios(),
                comissaoOrganizadoraDTOs,
                participantesDTOs);
    }
}
