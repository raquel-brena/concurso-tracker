package com.rb.web2.domain.inscricao.mapper;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;

public class RequestInscricaoMapper {

    // Método para mapear a entidade Inscricao para RequestInscricaoDTO
    public static RequestInscricaoDTO toDTO(Inscricao application) {
        return new RequestInscricaoDTO(
            application.getCandidate().getId(),  // Pegando o ID do candidato
            application.getProcessoSeletivo().getId(),  // Pegando o ID do processo seletivo
            application.getJobPosition()
        );
    }

    public static Inscricao toEntity(
        RequestInscricaoDTO dto, 
        User candidate, 
        ProcessoSeletivo processoSeletivo) 
    {
        return new Inscricao(
            candidate,  // Usando o objeto User
            dto.jobPosition(),
            processoSeletivo
        );
    }
}
