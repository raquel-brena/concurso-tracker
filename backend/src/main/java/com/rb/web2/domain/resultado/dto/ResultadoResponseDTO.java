package com.rb.web2.domain.resultado.dto;

import java.time.LocalDateTime;

import com.rb.web2.domain.etapa.dto.EtapaResponseDTO;
import com.rb.web2.domain.resultado.Resultado;

public record ResultadoResponseDTO(
        Long id,
        EtapaResponseDTO etapa,
        String status,
        LocalDateTime divulgadoEm,
        String observacoes) {

    // Método estático para converter a entidade Resultado para o DTO
    public static ResultadoResponseDTO from(Resultado resultado) {
        return new ResultadoResponseDTO(
                resultado.getId(),
                EtapaResponseDTO.from(resultado.getEtapa()), // Converte Etapa para DTO
                resultado.getStatus(),
                resultado.getDivulgadoEm(),
                resultado.getObservacoes()
        );
    }
}
