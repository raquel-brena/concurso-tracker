package com.rb.web2.domain.user.dto;

import com.rb.web2.domain.enums.Perfil;

public record UpdatePerfilDTO(String userId, String perfil) {
    public static Perfil getPerfilEnum(String perfil) {
        return Perfil.valueOf(perfil);
    }
}
