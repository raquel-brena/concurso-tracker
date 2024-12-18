package com.rb.web2.domain.inscricao.mapper;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.RequestInscricaoDTO;
import com.rb.web2.domain.inscricao.dto.ResponseInscricaoDTO;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;

public class InscricaoMapper {

    public static ResponseInscricaoDTO toDTO(Inscricao inscricao) {
        return new ResponseInscricaoDTO(
                inscricao.getId(),
                inscricao.getCandidato().getId(),
                inscricao.getVaga().getId(),
                inscricao.getDeletadoEm()
        );
    }

    public static Inscricao toEntity(
        RequestInscricaoDTO dto,
            User candidato,
            Vaga vaga) {
        Inscricao inscricao = new Inscricao();
        inscricao.setCandidato(candidato);
        inscricao.setVaga(vaga);
        inscricao.setDeletadoEm(null);
        return inscricao;
    }
}
