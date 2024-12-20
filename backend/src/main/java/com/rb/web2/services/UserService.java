package com.rb.web2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.enums.Perfil;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.UpdateUserDTO;
import com.rb.web2.domain.user.dto.UserResponseDTO;
import com.rb.web2.domain.user.mapper.UserMapper;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private AuthorizationUtil authorizationUtil;

    @PostConstruct
    public void init() {
        this.authorizationUtil = new AuthorizationUtil(this);
    }

    private void verificarPermissaoDeCriacaoOuAlteracao(String userId) {
        authorizationUtil.<String>verificarPermissaoOuComissao(
                userId,
                "EDIT_USER",
                id -> repository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Usuário não encontrado.")),
                (entity, user) -> {
                    User usuario = (User) entity;
                    return usuario.equals(user);
                });
    }

    public User create(User user) {
        verificarPermissaoDeCriacaoOuAlteracao(null);
        return this.repository.save(user);
    }

    public UserResponseDTO getById(String userId) {
        User user = this.getUserById(userId);
        return UserMapper.toResponseUserDTO(user);
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

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repository.findAll();
        List<UserResponseDTO> usersResponse = new ArrayList<>();
        for (User user : users) {
            usersResponse.add(UserMapper.toResponseUserDTO(user));
        }
        return usersResponse;
    }

    public List<User> findAllById(List<String> ids) {
        List<User> users = this.repository.findAllById(ids);
        if (users.isEmpty()) {
            throw new NotFoundException("Users doesn't exist");
        }
        return users;
    }

    public UserResponseDTO updateUser(String userId, UpdateUserDTO user) {
        verificarPermissaoDeCriacaoOuAlteracao(userId);
        User userToUpdate = this.getUserById(userId);
        userToUpdate.setLogin(user.login());
        userToUpdate.setNome(user.nome());
        userToUpdate.setEmail(user.email());
        userToUpdate.setCpf(user.cpf());
        userToUpdate.setTelefone(user.telefone());
        userToUpdate.setPerfil(user.getPerfilEnum());
        return UserMapper.toResponseUserDTO(this.repository.save(userToUpdate));
    }

    public void upgradeToCoordenador(String id) {
        User user = this.getUserById(id);

        if (!user.getPerfil().equals(Perfil.ADMIN)) {
            user.setPerfil(Perfil.COORDENADOR);
            this.repository.save(user);
        }

    }

}
