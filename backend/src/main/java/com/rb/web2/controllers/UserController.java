package com.rb.web2.controllers;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.UpdateUserDTO;
import com.rb.web2.repositories.RoleRepository;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(getUserById(id));
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody @Validated UpdateUserDTO dto) {
        var user = this.service.getUserById(dto.userId());

        var role = user.getRole();
        if (!(dto.roleId() == null)) {
            role = this.roleRepository.findById(dto.roleId()).orElseThrow(() -> new IllegalArgumentException("Role doesnt exist"));
        }

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
