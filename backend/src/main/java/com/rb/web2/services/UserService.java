package com.rb.web2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.ResponseUserDTO;
import com.rb.web2.domain.user.mapper.UserMapper;
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
        User user = repository.findById(userId).orElseThrow(
            () -> new NotFoundException(userId));
       
        return user;
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

    public List<ResponseUserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        List<ResponseUserDTO> usersResponse = new ArrayList<>();
        for (User user : users) { 
            usersResponse.add(UserMapper.toResponseUserDTO(user));
        }
        return usersResponse;
    }

    public List<User> findAllById(List<String> ids){
        List<User> users = this.repository.findAllById(ids);
        if (users.isEmpty()) {
            throw new NotFoundException("Users doesn't exist");
        }
        return users;
    }

}
