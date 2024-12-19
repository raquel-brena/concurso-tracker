package com.rb.web2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class Web2Application implements CommandLineRunner {

    @Value("${server.port:8080}")
    private int port;

    @Autowired
    private HikariDataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(Web2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Imprime no terminal onde a aplicação está rodando
        System.out.println("Aplicação rodando em: http://localhost:" + port);
        System.out.println("Swagger UI disponível em: http://localhost:" + port + "/swagger-ui/index.html");
        System.out.println("API Docs disponíveis em: http://localhost:" + port + "/v3/api-docs");
    }

    @PreDestroy
    public void onExit() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
