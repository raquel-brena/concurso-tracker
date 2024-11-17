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
//product, stock, address, role, order, note, actionRecord, user,
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/product").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/product").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_PRODUCT", "ROLE_VIEWER_PRODUCT")

                        .requestMatchers(HttpMethod.POST, "/api/store/**").hasAnyAuthority("ROLE_MANAGER_STORE", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/store/**").hasAnyAuthority("ROLE_MANAGER_STORE", "ROLE_ADMIN", "ROLE_VIEWER_STORE")

                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/roles/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/roles").hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/address").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_ADDRESS")
                        .requestMatchers(HttpMethod.GET, "/api/address").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_ADDRESS", "ROLE_VIEWER_ADDRESS")

                        .requestMatchers(HttpMethod.POST, "/api/order").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_ORDER")
                        .requestMatchers(HttpMethod.GET, "/api/order").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_ORDER", "ROLE_VIEWER_ORDER")

                        .requestMatchers(HttpMethod.POST, "/api/note").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_NOTE")
                        .requestMatchers(HttpMethod.GET, "/api/note").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_NOTE", "ROLE_VIEWER_NOTE")

                        .requestMatchers(HttpMethod.POST, "/api/action").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/action").hasAnyAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/stock/**").hasAnyAuthority("ROLE_MANAGER_STORE", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/stock/**").hasAnyAuthority("ROLE_MANAGER_STORE", "ROLE_ADMIN", "ROLE_VIEWER_STOCK")

                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_USER", "ROLE_VIEWER_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER_USER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(Customizer.withDefaults())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
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
