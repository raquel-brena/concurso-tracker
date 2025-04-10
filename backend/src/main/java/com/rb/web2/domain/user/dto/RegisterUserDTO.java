package com.rb.web2.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.rb.web2.domain.enums.Perfil;

public record RegisterUserDTO(

    @NotBlank(message = "O login é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    String login,

    @NotBlank(message = "O nome é obrigatório.")
    String nome,

    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
    String cpf,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    String password,

    

    Perfil perfil,

    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos numéricos.")
    String telefone,

    @Size(max = 50, message = "O cargo deve ter no máximo 50 caracteres.")
    String cargo
) {
    public static RegisterUserDTO fromUserAdmDTO(ReqUserAdmDTO reqUserAdmDTO) {
        Perfil perfil = reqUserAdmDTO.perfil() != null ? Perfil.valueOf(reqUserAdmDTO.perfil()) : null;

        return new RegisterUserDTO(
            reqUserAdmDTO.login(),
            reqUserAdmDTO.nome(),
            null, 
            reqUserAdmDTO.password(),
            perfil,
            null, 
            reqUserAdmDTO.cargo()
        );
    }
    public static RegisterUserDTO fromUserDTO(ReqUserDTO dto) {
        return new RegisterUserDTO(
            dto.login(),
            null,
            null, 
            dto.password(),
            Perfil.CANDIDATO,
            null, 
            null
        );
    }
}
