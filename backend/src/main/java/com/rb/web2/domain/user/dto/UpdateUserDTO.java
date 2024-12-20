package com.rb.web2.domain.user.dto;

import com.rb.web2.domain.enums.Perfil;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
                @NotBlank(message = "O campo 'userId' não pode ser vazio.") String userId,

                @NotBlank(message = "O campo 'login' não pode ser vazio.") String login,
                
                String nome,
                String email,
                String cpf,
                String telefone,

                @NotBlank(message = "O campo 'perfil' não pode ser vazio.") String perfil) {
        public Perfil getPerfilEnum() {
                return Perfil.valueOf(this.perfil);
        }
}
