package com.rb.web2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rb.web2.domain.enums.Perfil;
import com.rb.web2.domain.user.dto.AuthenticatedDTO;
import com.rb.web2.domain.user.dto.RegisterUserDTO;
import com.rb.web2.domain.user.dto.ReqUserAdmDTO;
import com.rb.web2.domain.user.dto.ReqUserDTO;
import com.rb.web2.domain.user.dto.UserResponseDTO;
import com.rb.web2.services.AuthenticationService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<RestSuccessMessage> login(@RequestBody @Valid AuthenticatedDTO data) {
        var token = this.authService.login(data);
        RestSuccessMessage successMessage = new RestSuccessMessage("Login realizado com sucesso", token);
        return ResponseEntity.ok(successMessage);
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<RestSuccessMessage> register(@RequestBody @Valid ReqUserDTO data) {

        UserResponseDTO newUser = this.authService.register(RegisterUserDTO.fromUserDTO(data));

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.id())
                .toUri();

        RestSuccessMessage successMessage = new RestSuccessMessage("Usuário criado com sucesso", newUser.id());
        return ResponseEntity.created(location).body(successMessage);
    }

    // @Transactional
    @PostMapping("/admin/register")
    public ResponseEntity<RestSuccessMessage> registerAdmin(@RequestBody @Valid ReqUserAdmDTO data) {

        UserResponseDTO newUser = this.authService.register(RegisterUserDTO.fromUserAdmDTO(data));

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.id())
                .toUri();

        RestSuccessMessage successMessage = new RestSuccessMessage("Usuário administrativo registrado com sucesso",
                newUser.id());
        return ResponseEntity.created(location).body(successMessage);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<RestSuccessMessage> verificarCadastro(@PathVariable String cpf) {
        var usuarioEncontrado = this.authService.findUserByCPF(cpf);

        RestSuccessMessage successMessage = new RestSuccessMessage("Usuário encontrado com sucesso",
                usuarioEncontrado.id());
        return ResponseEntity.ok().body(successMessage);
    }

    @PostMapping("/logout")
    public ResponseEntity<RestSuccessMessage> logout() {
        this.authService.logout();
        RestSuccessMessage successMessage = new RestSuccessMessage("Logout realizado com sucesso");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successMessage);
    }
}
