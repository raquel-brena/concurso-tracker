package com.rb.web2.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReqUserAdmDTO(
                @NotBlank(message = "O campo 'cpf' não pode ser vazio.") 
                String cpf,

                @NotBlank(message = "O nome é obrigatório.")
                String nome,

                @NotBlank(message = "O campo 'senha' não pode ser vazio.") 
                String senha,

                String perfil,
                
                @Size(max = 50, message = "O cargo deve ter no máximo 50 caracteres.")
                String cargo
                ) {
}
