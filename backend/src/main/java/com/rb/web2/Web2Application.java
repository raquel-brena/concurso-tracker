package com.rb.web2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class Web2Application {

    @Autowired
    private HikariDataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(Web2Application.class, args);
    }

    @PreDestroy
    public void onExit() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
