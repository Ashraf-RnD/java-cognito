package com.ashraful.javacognito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class JavaCognitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCognitoApplication.class, args);
    }

}
