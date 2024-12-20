package com.rb.web2.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas (não requerem autenticação)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/buscar/**").permitAll()

                        // Rotas de usuário básico (USER role)
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasAuthority("VIEW_USER")
                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasAuthority("REGISTER_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority("EDIT_USER")

                        // Rotas relacionadas a processos seletivos
                        .requestMatchers(HttpMethod.GET, "/api/processo/**").hasAuthority("VIEW_PROCESSO_SELETIVO")
                        .requestMatchers(HttpMethod.POST, "/api/processo/**")
                        .hasAuthority("EDIT_PROCESSO_SELETIVO")
                        .requestMatchers(HttpMethod.PUT, "/api/processo/**").hasAuthority("EDIT_PROCESSO_SELETIVO")

                        // Rotas relacionadas a critérios
                        .requestMatchers(HttpMethod.GET, "/api/criterios/**").hasAuthority("VIEW_CRITERIOS")
                        .requestMatchers(HttpMethod.POST, "/api/criterios/**").hasAuthority("EDIT_CRITERIOS")
                        .requestMatchers(HttpMethod.PUT, "/api/criterios/**").hasAuthority("EDIT_CRITERIOS")

                        // Rotas relacionadas a inscrições
                        .requestMatchers(HttpMethod.GET, "/api/inscricoes/**").hasAuthority("VIEW_INSCRICOES")
                        .requestMatchers(HttpMethod.POST, "/api/inscricoes/**").hasAuthority("EDIT_INSCRICOES")
                        .requestMatchers(HttpMethod.PUT, "/api/inscricoes/**").hasAuthority("EDIT_INSCRICOES")

                        // Rotas relacionadas a agendas
                        .requestMatchers(HttpMethod.GET, "/api/agenda/**").hasAuthority("VIEW_AGENDA")
                        .requestMatchers(HttpMethod.POST, "/api/agenda/**").hasAuthority("EDIT_AGENDA")
                        .requestMatchers(HttpMethod.PUT, "/api/agenda/**").hasAuthority("EDIT_AGENDA")

                        // Rotas relacionadas a documentos
                        .requestMatchers(HttpMethod.GET, "/api/documentos/**").hasAuthority("VIEW_DOCUMENTOS")
                        .requestMatchers(HttpMethod.POST, "/api/documentos/**").hasAuthority("EDIT_DOCUMENTOS")
                        .requestMatchers(HttpMethod.PUT, "/api/documentos/**").hasAuthority("EDIT_DOCUMENTOS")

                        // Rotas administrativas (ADMIN role)
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN")

                        // Qualquer outra rota requer autenticação
                        .anyRequest().authenticated())
                .exceptionHandling(Customizer.withDefaults())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
