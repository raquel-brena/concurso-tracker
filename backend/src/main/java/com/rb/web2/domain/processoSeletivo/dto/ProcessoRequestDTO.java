package com.rb.web2.domain.processoSeletivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProcessoRequestDTO(
                @NotBlank(message = "O título não pode estar vazio.") 
                String titulo,

                @NotNull(message = "A validade é obrigatória.") 
                Integer validade,

                @NotNull(message = "O campo 'temporario' é obrigatório.") 
                boolean temporario,

                String descricao) {
}
