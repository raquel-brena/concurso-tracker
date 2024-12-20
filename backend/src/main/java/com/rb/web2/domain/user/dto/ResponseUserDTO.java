package com.rb.web2.domain.user.dto;

import com.rb.web2.domain.user.User;

public record ResponseUserDTO(
        boolean ativo,
        String nome,
        String email,
        String cpf,
        String telefone,
        String cargo) {
    public static ResponseUserDTO from(User user) {
        return new ResponseUserDTO(
                user.isAtivo(),
                user.getNome(),
                user.getEmail(),
                user.getCpf(),
                user.getTelefone(),
                user.getRole() != null ? user.getRole().name() : null);
    }
}
