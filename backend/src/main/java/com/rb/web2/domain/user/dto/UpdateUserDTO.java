package com.rb.web2.domain.user.dto;

import com.rb.web2.domain.enums.Perfil;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
                @NotBlank(message = "O campo 'userId' não pode ser vazio.") String userId,

                @NotBlank(message = "O campo 'login' não pode ser vazio.") String login,

                @NotBlank(message = "O campo 'nome' não pode ser vazio.") String nome,

                @NotBlank(message = "O campo 'email' não pode ser vazio.") String email,

                @NotBlank(message = "O campo 'cpf' não pode ser vazio.") String cpf,

                @NotBlank(message = "O campo 'telefone' não pode ser vazio.") String telefone,

                @NotBlank(message = "O campo 'perfil' não pode ser vazio.") String perfil) {
        public Perfil getPerfilEnum() {
                return Perfil.valueOf(this.perfil);
        }
}
