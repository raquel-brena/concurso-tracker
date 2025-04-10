package com.rb.web2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.UpdatePerfilDTO;
import com.rb.web2.domain.user.dto.UpdateUserDTO;
import com.rb.web2.domain.user.dto.UserResponseDTO;
import com.rb.web2.services.UserService;
import com.rb.web2.shared.RestMessage.RestSuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;
    
    @GetMapping
    public ResponseEntity<RestSuccessMessage> getAllUsers() {
        List<UserResponseDTO> users = service.getAllUsers();
        RestSuccessMessage successMessage = new RestSuccessMessage("Usuário encontrado com sucesso", users);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestSuccessMessage> getUser(@PathVariable String id) {
        UserResponseDTO user = this.service.getById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(null);
        }
        RestSuccessMessage successMessage = new RestSuccessMessage("Usuário encontrado com sucesso", user);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<RestSuccessMessage> updateUser(@RequestBody UpdatePerfilDTO user) {
        this.service.editarPerfil(user.userId(), user);
        RestSuccessMessage successMessage = new RestSuccessMessage("Usuário atualizado com sucesso");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RestSuccessMessage> updateUser(@Valid @RequestBody UpdateUserDTO dto) {
        UserResponseDTO user = this.service.updateUser(dto.userId(), dto);

        RestSuccessMessage successMessage = new RestSuccessMessage("Usuario atualizado com sucesso", user);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    public User getUserById(String id) {
        return this.service.getUserById(id);
    }
}
