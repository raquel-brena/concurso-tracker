package com.rb.web2.domain.agenda.mapper;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.agenda.dto.AgendaResponseDTO;

public class AgendaMapper {

    public static Agenda toEntity(AgendaDTO dto) {
        Agenda agenda = new Agenda();

        agenda.setInicioVigencia(dto.inicioVigencia());
        agenda.setFimVigencia(dto.fimVigencia());
        agenda.setInicioInscricao(dto.inicioInscricao());
        agenda.setFimInscricao(dto.fimInscricao());
        agenda.setHomologacao(dto.homologacao());
        agenda.setInicioRecurso(dto.inicioRecurso());
        agenda.setFimRecurso(dto.fimRecurso());
        agenda.setResultadoPreliminar(dto.resultadoPreliminar());
        agenda.setResultadoFinal(dto.resultadoFinal());
        agenda.setPrazoConvocacao(dto.prazoConvocacao());
        return agenda;
    }

    public static AgendaResponseDTO toDTO(Agenda agenda) {
        if (agenda == null) {
            return null;
        }
        return new AgendaResponseDTO(
                agenda.getId(),
                agenda.isAtivo(),
                agenda.getInicioVigencia(),
                agenda.getFimVigencia(),
                agenda.getInicioInscricao(),
                agenda.getFimInscricao(),
                agenda.getHomologacao(),
                agenda.getInicioRecurso(),
                agenda.getFimRecurso(),
                agenda.getResultadoPreliminar(),
                agenda.getResultadoFinal(),
                agenda.getPrazoConvocacao());
    }
}
