package com.rb.web2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.enums.Perfil;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.AuthenticatedDTO;
import com.rb.web2.domain.user.dto.RegisterDTO;
import com.rb.web2.domain.user.dto.ResponseLoginDTO;
import com.rb.web2.domain.user.dto.ResponseUserDTO;
import com.rb.web2.domain.user.mapper.UserMapper;
import com.rb.web2.infra.security.TokenService;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserService userService, @Lazy AuthenticationManager authenticationManager,
            TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseLoginDTO login(AuthenticatedDTO data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new ResponseLoginDTO(token);
    }

    public User register(RegisterDTO data) {
        this.userService.checkUserExists(data.login());

        String encryptedPassword = passwordEncoder.encode(data.password());

        return this.userService.create(new User(data.login(), encryptedPassword, Perfil.USER));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userService.loadUserByUsername(username);
    }

    public ResponseUserDTO getUsuarioAutenticado() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(login);

        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.toString())) // Garantir que seja uma String
                .collect(Collectors.toList());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);

        // Imprimir o objeto UserDetails
        System.out.println(userDetails + " foi autenticado");

        ResponseUserDTO userResponse = UserMapper.toResponseUserDTO(user);
        return userResponse;
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
