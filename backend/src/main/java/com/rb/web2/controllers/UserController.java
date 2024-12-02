package com.rb.web2.controllers;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.UpdateUserDTO;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.services.UserService;
import com.rb.web2.shared.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;

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

    // @PatchMapping("/{id}")
    // public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
    //     User updatedUser = this.service.updateUser(id, user);
    //     if (updatedUser == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                             .body(null);
    //     }
    //     return ResponseEntity.ok(updatedUser);
    // }

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
