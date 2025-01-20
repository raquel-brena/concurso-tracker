package com.rb.web2.domain.inscricao.mapper;

import com.rb.web2.domain.inscricao.Inscricao;
import com.rb.web2.domain.inscricao.dto.InscricaoRequestDTO;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.vaga.Vaga;

public class InscricaoMapper {

    public static Inscricao toEntity(
        InscricaoRequestDTO dto,
            User candidato,
            Vaga vaga) {
        Inscricao inscricao = new Inscricao();
        inscricao.setCandidato(candidato);
        inscricao.setVaga(vaga);
        inscricao.setDeletadoEm(null);
        return inscricao;
    }
}
