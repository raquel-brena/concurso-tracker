package com.rb.web2.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ReqUserDTO(
                @NotBlank(message = "O campo 'login' não pode ser vazio.") 
                String cpf,

                @NotBlank(message = "O campo 'senha' não pode ser vazio.") 
                String password) {
}
