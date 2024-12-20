package com.rb.web2.domain.agenda.mapper;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.agenda.dto.AgendaRequestDTO;

public class AgendaMapper {

    public static Agenda toEntity(AgendaRequestDTO dto) {
        Agenda agenda = new Agenda();
        agenda.setInicioVigencia(dto.inicioVigencia());
        agenda.setFimVigencia(dto.fimVigencia());
        agenda.setInicioInscricao(dto.inicioInscricao());
        agenda.setFimInscricao(dto.fimInscricao());
        agenda.setInicioHomologacao(dto.inicioHomologacao());
        agenda.setFimHomologacao(dto.fimHomologacao());
        agenda.setInicioRecurso(dto.inicioRecurso());
        agenda.setFimRecurso(dto.fimRecurso());
        agenda.setResultadoPreliminar(dto.resultadoPreliminar());
        agenda.setResultadoFinal(dto.resultadoFinal());
        agenda.setPrazoConvocacao(dto.prazoConvocacao());
        
        return agenda;
    }
}
