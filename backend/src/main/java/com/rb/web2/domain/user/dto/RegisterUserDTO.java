package com.rb.web2.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.rb.web2.domain.enums.Perfil;

public record RegisterUserDTO(

    @NotBlank(message = "O nome é obrigatório.")
    String nome,

    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
    String cpf,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    String senha,

    Perfil perfil,

    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos numéricos.")
    String telefone,

    @Size(max = 50, message = "O cargo deve ter no máximo 50 caracteres.")
    String cargo
) {
    public static RegisterUserDTO fromUserAdmDTO(ReqUserAdmDTO reqUserAdmDTO) {
        Perfil perfil = reqUserAdmDTO.perfil() != null ? Perfil.valueOf(reqUserAdmDTO.perfil()) : null;

        return new RegisterUserDTO(

            reqUserAdmDTO.nome(),
            reqUserAdmDTO.cpf(), 
            reqUserAdmDTO.senha(),
            perfil,
            null, 
            reqUserAdmDTO.cargo()
        );
    }
    
    public static RegisterUserDTO fromUserDTO(ReqUserDTO dto) {
        return new RegisterUserDTO(
            null,
            dto.cpf(), 
            dto.senha(),
            Perfil.CANDIDATO,
            null, 
            null
        );
    }
}
