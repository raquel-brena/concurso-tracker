package com.rb.web2.domain.user.dto;

// Já que Role não é mais uma entidade, o que será passado vai ser uma String, não?
public record UpdateUserDTO( String userId, String login, Long roleId) {
}
