package com.rb.web2.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        @NotBlank(message = "O campo 'userId' não pode ser vazio.") String userId,

        @NotBlank(message = "O campo 'login' não pode ser vazio.") String login,

        @NotBlank(message = "O campo 'senha' não pode ser vazio.") Long roleId) {
}
