package com.rb.web2.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rb.web2.domain.enums.Role;
import com.rb.web2.domain.user.User;
import com.rb.web2.domain.user.dto.AuthenticatedDTO;
import com.rb.web2.domain.user.dto.RegisterDTO;
import com.rb.web2.domain.user.dto.ResponseLoginDTO;
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


        return this.userService.create(new User (data.login(), encryptedPassword, Role.USER));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userService.loadUserByUsername(username);
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
