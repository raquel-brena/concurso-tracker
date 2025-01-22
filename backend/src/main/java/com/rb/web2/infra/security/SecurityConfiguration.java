package com.rb.web2.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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

import jakarta.servlet.http.HttpServletResponse;

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
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/api/auth/admin/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/buscar/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/processo/**").hasAuthority("ROLE_VIEW_PROCESSO_SELETIVO")
                        .requestMatchers(HttpMethod.GET, "/api/agendas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cargo/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/vagas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/instituicoes/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()

                        // Rotas de usuário básico (USER role)
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasAuthority("ROLE_VIEW_USER")
                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasAuthority("ROLE_REGISTER_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority("ROLE_EDIT_USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/user/**").hasAuthority("ROLE_EDIT_USERS")

                        .requestMatchers(HttpMethod.GET, "/api/etapa/**").hasAuthority("ROLE_VIEW_ETAPA")
                        .requestMatchers(HttpMethod.POST, "/api/etapa/**").hasAuthority("ROLE_EDIT_ETAPA")
                        .requestMatchers(HttpMethod.PUT, "/api/etapa/**").hasAuthority("ROLE_EDIT_ETAPA")
                        .requestMatchers(HttpMethod.PATCH, "/api/etapa/**").hasAuthority("ROLE_EDIT_ETAPAS")

                        // Rotas relacionadas a processos seletivos
                        .requestMatchers(HttpMethod.POST, "/api/processo/**").hasAuthority("ROLE_EDIT_PROCESSO_SELETIVO")
                        .requestMatchers(HttpMethod.PUT, "/api/processo/**").hasAuthority("ROLE_EDIT_PROCESSO_SELETIVO")

                        // Rotas relacionadas a critérios
                        .requestMatchers(HttpMethod.GET, "/api/criterios/**").hasAuthority("ROLE_VIEW_CRITERIOS")
                        .requestMatchers(HttpMethod.POST, "/api/criterios/**").hasAuthority("ROLE_EDIT_CRITERIOS")
                        .requestMatchers(HttpMethod.PUT, "/api/criterios/**").hasAuthority("ROLE_EDIT_CRITERIOS")

                        // Rotas relacionadas a inscrições
                        .requestMatchers(HttpMethod.GET, "/api/inscricoes/**").hasAuthority("ROLE_VIEW_INSCRICOES")
                        .requestMatchers(HttpMethod.POST, "/api/inscricoes/**").hasAuthority("ROLE_EDIT_INSCRICOES")
                        .requestMatchers(HttpMethod.PUT, "/api/inscricoes/**").hasAuthority("ROLE_EDIT_INSCRICOES")

                        // Rotas relacionadas a agendas
                        .requestMatchers(HttpMethod.POST, "/api/agendas/**").hasAuthority("ROLE_EDIT_AGENDA")
                        .requestMatchers(HttpMethod.PUT, "/api/agendas/**").hasAuthority("ROLE_EDIT_AGENDA")

                        // Rotas relacionadas a documentos
                        .requestMatchers(HttpMethod.GET, "/api/documentos/**").hasAuthority("ROLE_VIEW_DOCUMENTOS")
                        .requestMatchers(HttpMethod.POST, "/api/documentos/**").hasAuthority("ROLE_EDIT_DOCUMENTOS")
                        .requestMatchers(HttpMethod.PUT, "/api/documentos/**").hasAuthority("ROLE_EDIT_DOCUMENTOS")

                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/cpf/**").permitAll()
                        // Rotas relacionadas a cargos
                        .requestMatchers(HttpMethod.POST, "/api/cargo/**").hasAuthority("ROLE_EDIT_CARGOS")
                        .requestMatchers(HttpMethod.PUT, "/api/cargo/**").hasAuthority("ROLE_EDIT_CARGOS")

                        // Rotas relacionadas a vagas
                        .requestMatchers(HttpMethod.POST, "/api/vagas/**").hasAuthority("ROLE_EDIT_VAGAS")
                        .requestMatchers(HttpMethod.PUT, "/api/vagas/**").hasAuthority("ROLE_EDIT_VAGAS")

                        // Rotas relacionadas a instituições
                        .requestMatchers(HttpMethod.POST, "/api/instituicoes/**").hasAuthority("ROLE_EDIT_INSTITUICAO")
                        .requestMatchers(HttpMethod.PUT, "/api/instituicoes/**").hasAuthority("ROLE_EDIT_INSTITUICAO")

                        // Rotas relacionadas aos documentos de inscrição
                        .requestMatchers(HttpMethod.POST, "/api/documentos-inscricao/**")
                        .hasAuthority("ROLE_HOMOLOGAR_DOCUMENTO_INSCRICAO")

                        // Rotas relacionadas a pontuação
                        .requestMatchers(HttpMethod.GET, "/api/pontuacao/**").hasAuthority("ROLE_VIEW_PONTUACOES")
                        .requestMatchers(HttpMethod.POST, "/api/pontuacao/**").hasAuthority("ROLE_EDIT_PONTUACOES")
                        .requestMatchers(HttpMethod.PUT, "/api/pontuacao/**").hasAuthority("ROLE_EDIT_PONTUACOES")
                        .requestMatchers(HttpMethod.DELETE, "/api/pontuacao/**").hasAuthority("ROLE_EDIT_PONTUACOES")

                        // // Rotas administrativas (ADMIN role)
                        // .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("ROLE_ADMIN")
                        // .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_ADMIN")
                        // .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_ADMIN")
                        // .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN")
                        // .requestMatchers(HttpMethod.PATCH, "/api/**").hasAuthority("ROLE_ADMIN")

                        // Qualquer outra rota requer autenticação
                        .anyRequest().permitAll())
                        .exceptionHandling(e -> e.authenticationEntryPoint((req, res, authException) -> {
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        }))
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
