package com.rb.web2.domain.etapa.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.rb.web2.domain.criterioAvaliacao.dto.CriterioResponseDTO;
import com.rb.web2.domain.etapa.Etapa;
import com.rb.web2.domain.processoSeletivo.dto.ProcessoResponseDTO;
import com.rb.web2.domain.resultado.dto.ResultadoResponseDTO;

public record EtapaResponseDTO(
        Long id,
        String nome,
        ProcessoResponseDTO processoSeletivo,
        List<CriterioResponseDTO> criterios,
        List<ResultadoResponseDTO> resultados,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {

    // Método estático para converter a entidade Etapa para o DTO
    public static EtapaResponseDTO from(Etapa etapa) {
        return new EtapaResponseDTO(
                etapa.getId(),
                etapa.getNome(),
                ProcessoResponseDTO.from(etapa.getProcessoSeletivo()), // Converte ProcessoSeletivo para DTO
                etapa.getCriterios().stream()
                        .map(CriterioResponseDTO::from) // Converte cada CriterioAvaliacao para DTO
                        .collect(Collectors.toList()),
                etapa.getResultados().stream()
                        .map(ResultadoResponseDTO::from) // Converte cada Resultado para DTO
                        .collect(Collectors.toList()),
                etapa.getCriadoEm(),
                etapa.getAtualizadoEm()
        );
    }
}
