package com.rb.web2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.user.User;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User create(User user) {
        return this.repository.save(user);
    }

    public User getUserById(String userId) {
        return repository.findById(userId).orElseThrow(
            () -> new NotFoundException(userId)
        );
    }
    

    public UserDetails loadUserByUsername(String login) {
        return this.repository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));
    }

    public void checkUserExists(String login) {
        if (this.repository.findByLogin(login).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public List<User> findAllById(List<String> ids){
        List<User> users = this.repository.findAllById(ids);
        if (users.isEmpty()) {
            throw new NotFoundException("Users doesn't exist");
        }
        return users;
    }

}
