package com.rb.web2.domain.processoSeletivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProcessoRequestDTO(

                String titulo,

              
                Integer validade,

           
                boolean temporario,

                String descricao) {
}
