package com.rb.web2.domain.candidateApplication.mapper;

import com.rb.web2.domain.candidateApplication.CandidateApplication;
import com.rb.web2.domain.candidateApplication.dto.RequestInscricaoDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.criterioAvaliacao.CriterioAvaliacao;

import java.util.List;
import java.util.stream.Collectors;

public class RequestInscricaoMapper {

    // Método para mapear a entidade CandidateApplication para RequestInscricaoDTO
    public static RequestInscricaoDTO toDTO(CandidateApplication application) {
        return new RequestInscricaoDTO(
            application.getCandidate().getId(),  // Pegando o ID do candidato
            application.getProcessoSeletivo().getId(),  // Pegando o ID do processo seletivo
            application.getJobPosition()
        );
    }

    public static CandidateApplication toEntity(
        RequestInscricaoDTO dto, 
        User candidate, 
        ProcessoSeletivo processoSeletivo) 
    {
        return new CandidateApplication(
            candidate,  // Usando o objeto User
            dto.jobPosition(),
            processoSeletivo
        );
    }
}
