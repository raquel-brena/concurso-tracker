package com.rb.web2.services;

import com.rb.web2.domain.user.User;
import com.rb.web2.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    
    private final RoleService roleService;
    private final UserRepository repository;

    public UserService (RoleService roleService, UserRepository repository) { 
        this.roleService = roleService;
        this.repository = repository;
    }

    public User create (String login, String password) { 
        var role = roleService.findRoleByName("USER");

        return this.repository.save(new User(login, password, role));
    }

    public User getUserById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist"));
    }

    public User getUserByLogin(String login) {
        return this.repository.findByLogin(login)
            .orElseThrow(() -> new IllegalArgumentException("User doesn't exist"));
    }

    public void checkUserExists(String login) {
        if (this.repository.findByLogin(login).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
    }

}
