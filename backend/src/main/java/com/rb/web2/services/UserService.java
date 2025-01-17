package com.rb.web2.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.enums.Perfil;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.UpdatePerfilDTO;
import com.rb.web2.domain.user.dto.UpdateUserDTO;
import com.rb.web2.domain.user.dto.UserResponseDTO;
import com.rb.web2.infra.util.AuthorizationUtil;
import com.rb.web2.repositories.UserRepository;
import com.rb.web2.shared.exceptions.BadRequestException;
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

    private void verificarPermissaoDeAlterarUsuarios(String userId) {
        authorizationUtil.<String>verificarPermissaoOuComissao(
                userId,
                "EDIT_USERS",
                id -> repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado.")),
                (entity, user) -> {
                    User usuario = (User) entity;
                    var isUserAdmin = usuario.getPerfil().equals(Perfil.ADMIN);
                    var isUserCoordenador = usuario.getPerfil().equals(Perfil.COORDENADOR);
                    return !isUserAdmin && !isUserCoordenador;
                });
    }

    private void verificarPermissaoDeLeitura(String userId) {
        authorizationUtil.<String>verificarPermissaoOuComissao(
                userId,
                "VIEW_USER",
                id -> repository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado.")),
                (entity, user) -> {
                    User usuario = (User) entity;
                    var isUserAdmin = usuario.getPerfil().equals(Perfil.ADMIN);
                    var isUserCoordenador = usuario.getPerfil().equals(Perfil.COORDENADOR);
                    var isUser = usuario.equals(user);
                    return !isUserAdmin && !isUserCoordenador && !isUser;
                });
    }

    public User create(User user) {
      verificarPermissaoDeCriacaoOuAlteracao(null);
        return this.repository.save(user);
    }

    public UserResponseDTO getById(String userId) {
        User user = this.getUserById(userId);
        return UserResponseDTO.from(user);
    }

    public User getUserById(String userId) {
        verificarPermissaoDeLeitura(userId);

        User user = repository.findById(userId).orElseThrow(
                () -> new NotFoundException(userId));

        return user;
    }

    public UserDetails loadUserByUsername(String cpf) {
        return this.repository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public UserResponseDTO findByCPF(String cpf) {
        Optional<User> user = this.repository.findByCpf(cpf);

        if (user.isPresent()) {   
            var userDTO = UserResponseDTO.from(user.get());
            return userDTO;
        }
    
        return null;
    }

    public void checkUserExists(String cpf) {
        if (this.repository.findByCpf(cpf).isPresent()) {
            throw new BadRequestException("Já existe usuário com o login informado.");
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = repository.findAll();
        List<UserResponseDTO> usersResponse = new ArrayList<>();
        for (User user : users) {
            usersResponse.add(UserResponseDTO.from(user));
        }
        return usersResponse;
    }

    public List<User> findAllById(List<String> ids) {
        List<User> users = this.repository.findAllById(ids);
        if (users.isEmpty()) {
            throw new NotFoundException("Nenhum usuário encontrado.");
        }
        return users;
    }

    public UserResponseDTO updateUser(String userId, UpdateUserDTO user) {
        verificarPermissaoDeCriacaoOuAlteracao(userId);

        User userToUpdate = this.getUserById(userId);

        this.verificarLogicaDePerfil(userToUpdate.getPerfil(), user.getPerfilEnum());

        userToUpdate.setCpf(user.login());
        userToUpdate.setNome(user.nome());
        userToUpdate.setEmail(user.email());
        userToUpdate.setCpf(user.cpf());
        userToUpdate.setTelefone(user.telefone());
        userToUpdate.setPerfil(user.getPerfilEnum());
        return UserResponseDTO.from(this.repository.save(userToUpdate));
     
    }

    public void editarPerfil(String id, UpdatePerfilDTO perfilDto) {
        verificarPermissaoDeAlterarUsuarios(null);

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findByCpf(login).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        verificarLogicaDePerfil(user.getPerfil(), UpdatePerfilDTO.getPerfilEnum(perfilDto.perfil()));

        user = this.getUserById(id);

        if (!user.getPerfil().equals(Perfil.ADMIN)) {
            try {
                Perfil novoPerfil = Perfil.valueOf(perfilDto.perfil().toUpperCase());
                user.setPerfil(novoPerfil); 
                this.repository.save(user);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Perfil inválido");
            }
        }
    }

    public void upgradeToCoordenador(String id) {
        User user = this.getUserById(id);

        if (!user.getPerfil().equals(Perfil.ADMIN)) {
            user.setPerfil(Perfil.COORDENADOR);
            this.repository.save(user);
        }
    }

    public void upgradeToAssistente(String id) {
        User user = this.getUserById(id);

        if (!user.getPerfil().equals(Perfil.ADMIN)) {
            user.setPerfil(Perfil.ASSISTENTE);
            this.repository.save(user);
        }
    }

    public void downgradeToUser(String id) {
        User user = this.getUserById(id);

        if (!user.getPerfil().equals(Perfil.ADMIN)) {
            user.setPerfil(Perfil.CANDIDATO);
            this.repository.save(user);
        }
    }

    private void verificarLogicaDePerfil(Perfil perfilEditor, Perfil perfilEditado) {
        if (perfilEditado.equals(Perfil.ADMIN) && !perfilEditor.equals(Perfil.ADMIN)) {
            throw new IllegalArgumentException("Não é possível alterar o perfil de um administrador");
        } else if (perfilEditado.equals(Perfil.COORDENADOR) && !(perfilEditor.equals(Perfil.COORDENADOR) || perfilEditor.equals(Perfil.ADMIN))) {
            throw new IllegalArgumentException("Não é possível alterar o perfil de um coordenador");
        } else if (perfilEditado.equals(Perfil.ASSISTENTE) && !(perfilEditor.equals(Perfil.COORDENADOR) || perfilEditor.equals(Perfil.ADMIN))) {
            throw new IllegalArgumentException("Não é possível alterar o perfil de um assistente");
        } else if (perfilEditado.equals(Perfil.CANDIDATO) && !(perfilEditor.equals(Perfil.COORDENADOR) || perfilEditor.equals(Perfil.ADMIN))) {
            throw new IllegalArgumentException("Não é possível alterar o perfil de um usuário");
        }  else if (perfilEditado.equals(Perfil.CANDIDATO)) {
            throw new IllegalArgumentException("Não é possível alterar o perfil de um candidato");
        } 
    }

}
