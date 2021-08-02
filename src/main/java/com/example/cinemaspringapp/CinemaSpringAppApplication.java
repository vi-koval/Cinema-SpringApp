package com.example.cinemaspringapp;

import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

import static com.example.cinemaspringapp.model.Role.ROLE_ADMIN;

@SpringBootApplication
public class CinemaSpringAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaSpringAppApplication.class, args);
    }

@Bean
public CommandLineRunner runApplication(UserRepository userRepository) {

    return (args -> {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User admin = User.builder()
                .firstName("Vitalii")
                .lastName("Koval")
                .email("admin@gmail.com")
                .password(bCryptPasswordEncoder.encode("admin"))
                .role(ROLE_ADMIN)
                .build();

        userRepository.save(admin);
    } );
}

}