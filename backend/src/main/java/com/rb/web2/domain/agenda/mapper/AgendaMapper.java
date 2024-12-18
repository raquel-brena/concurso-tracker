package com.rb.web2.domain.agenda.mapper;

import com.rb.web2.domain.agenda.Agenda;
import com.rb.web2.domain.agenda.dto.AgendaDTO;
import com.rb.web2.domain.agenda.dto.AgendaResponseDTO;

public class AgendaMapper {

    public static Agenda toEntity(AgendaDTO dto) {
        Agenda agenda = new Agenda();

        if (dto.inicioVigencia() != null) {
            agenda.setInicioVigencia(dto.inicioVigencia());
        }

        if (dto.fimVigencia() != null) {
            agenda.setFimVigencia(dto.fimVigencia());
        }

        if (dto.inicioInscricao() != null) {
            agenda.setInicioInscricao(dto.inicioInscricao());
        }

        if (dto.fimInscricao() != null) {
            agenda.setFimInscricao(dto.fimInscricao());
        }

        if (dto.homologacao() != null) {
            agenda.setInicioHomologacao(dto.homologacao());
        }

        

        if (dto.inicioRecurso() != null) {
            agenda.setInicioRecurso(dto.inicioRecurso());
        }

        if (dto.fimRecurso() != null) {
            agenda.setFimRecurso(dto.fimRecurso());
        }

        if (dto.resultadoPreliminar() != null) {
            agenda.setResultadoPreliminar(dto.resultadoPreliminar());
        }

        if (dto.resultadoFinal() != null) {
            agenda.setResultadoFinal(dto.resultadoFinal());
        }

        if (dto.prazoConvocacao() != null) {
            agenda.setPrazoConvocacao(dto.prazoConvocacao());
        }
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
                agenda.getInicioHomologacao(),
                agenda.getInicioRecurso(),
                agenda.getFimRecurso(),
                agenda.getResultadoPreliminar(),
                agenda.getResultadoFinal(),
                agenda.getPrazoConvocacao());
    }
}
