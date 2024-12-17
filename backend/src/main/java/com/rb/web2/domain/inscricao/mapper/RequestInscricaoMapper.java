package com.rb.web2.domain.inscricao.mapper;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.processoSeletivo.ProcessoSeletivo;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;

public class RequestInscricaoMapper {

    public static RequestInscricaoDTO toDTO(Inscricao inscricao) {
        return new RequestInscricaoDTO(
                inscricao.getCandidato().getId(),
                inscricao.getCandidato().getId(),
                inscricao.getProcessoSeletivo().getId(),
                inscricao.getVaga().getId());
    }

    public static Inscricao toEntity(
            RequestInscricaoDTO dto,
            User candidato,
            ProcessoSeletivo processoSeletivo,
            Vaga vaga) {
        Inscricao inscricao = new Inscricao();
        inscricao.setCandidato(candidato);
        inscricao.setProcessoSeletivo(processoSeletivo);
        inscricao.setVaga(vaga);
        inscricao.setAtivo(true);
        return inscricao;
    }
}
