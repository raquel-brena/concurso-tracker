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
                        .requestMatchers(HttpMethod.POST, "/api/auth/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/buscar/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/processo/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/agendas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cargo/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/vagas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/instituicoes/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()

                        // Rotas de usuário básico (USER role)
                        .requestMatchers(HttpMethod.GET, "/api/user").hasAuthority("VIEW_USERS")
                        .requestMatchers(HttpMethod.GET, "/api/user/{id}").hasAuthority("VIEW_USER")
                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasAuthority("REGISTER_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority("EDIT_USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/user/**").hasAuthority("EDIT_USERS")

                        // Rotas relacionadas a processos seletivos
                        .requestMatchers(HttpMethod.POST, "/api/processo/**").hasAuthority("EDIT_PROCESSO_SELETIVO")
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
                        .requestMatchers(HttpMethod.POST, "/api/agendas/**").hasAuthority("EDIT_AGENDA")
                        .requestMatchers(HttpMethod.PUT, "/api/agendas/**").hasAuthority("EDIT_AGENDA")

                        // Rotas relacionadas a documentos
                        .requestMatchers(HttpMethod.GET, "/api/documentos/**").hasAuthority("VIEW_DOCUMENTOS")
                        .requestMatchers(HttpMethod.POST, "/api/documentos/**").hasAuthority("EDIT_DOCUMENTOS")
                        .requestMatchers(HttpMethod.PUT, "/api/documentos/**").hasAuthority("EDIT_DOCUMENTOS")

                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/cpf/**").permitAll()
                        // Rotas relacionadas a cargos
                        .requestMatchers(HttpMethod.POST, "/api/cargo/**").hasAuthority("EDIT_CARGOS")
                        .requestMatchers(HttpMethod.PUT, "/api/cargo/**").hasAuthority("EDIT_CARGOS")

                        // Rotas relacionadas a vagas
                        .requestMatchers(HttpMethod.POST, "/api/vagas/**").hasAuthority("EDIT_VAGAS")
                        .requestMatchers(HttpMethod.PUT, "/api/vagas/**").hasAuthority("EDIT_VAGAS")

                        // Rotas relacionadas a instituições
                        .requestMatchers(HttpMethod.POST, "/api/instituicoes/**").hasAuthority("EDIT_INSTITUICAO")
                        .requestMatchers(HttpMethod.PUT, "/api/instituicoes/**").hasAuthority("EDIT_INSTITUICAO")

                        // Rotas relacionadas aos documentos de inscrição
                        .requestMatchers(HttpMethod.POST, "/api/documentos-inscricao/**")
                        .hasAuthority("HOMOLOGAR_DOCUMENTO_INSCRICAO")

                        // Rotas relacionadas a pontuação
                        .requestMatchers(HttpMethod.GET, "/api/pontuacao/**").hasAuthority("VIEW_PONTUACOES")
                        .requestMatchers(HttpMethod.POST, "/api/pontuacao/**").hasAuthority("EDIT_PONTUACOES")
                        .requestMatchers(HttpMethod.PUT, "/api/pontuacao/**").hasAuthority("EDIT_PONTUACOES")
                        .requestMatchers(HttpMethod.DELETE, "/api/pontuacao/**").hasAuthority("EDIT_PONTUACOES")

                        // Rotas administrativas (ADMIN role)
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasAuthority("ROLE_ADMIN")

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
                .allowedOrigins("http://localhost:5173") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
            }
        };
    }
}
