package com.rb.web2.controllers;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.UpdateUserDTO;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.services.UserService;
import com.rb.web2.shared.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = this.service.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody @Validated UpdateUserDTO dto) {
        var user = this.service.getUserById(dto.userId());

        var role = user.getRole();
        // if (!(dto.roleId() == null)) {
        //     role = this.roleRepository.findById(dto.roleId()).orElseThrow(() -> new NotFoundException("Role doesnt exist"));
        // }

        if (dto.login() != null) {
            user.setLogin(dto.login());
        }

        user.setRole(role);
        this.service.create(user);

        return ResponseEntity.ok().body("User updated with ID: " + user.getId());
    }

    public User getUserById(String id) {
        return this.service.getUserById(id);
    }
}
