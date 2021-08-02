package com.example.cinemaspringapp;

import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


import static com.example.cinemaspringapp.model.Role.ROLE_ADMIN;
import static com.example.cinemaspringapp.model.Role.ROLE_USER;

@SpringBootApplication
public class CinemaSpringAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaSpringAppApplication.class, args);
    }

@Bean
public CommandLineRunner runApplication(UserRepository userRepository) {

    return (args -> {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        List<User> users = new ArrayList<>();

        User admin = User.builder()
                .firstName("Vitalii")
                .lastName("Koval")
                .email("admin@gmail.com")
                .password(bCryptPasswordEncoder.encode("admin"))
                .role(ROLE_ADMIN)
                .build();

        User user = User.builder()
                .firstName("Ivan")
                .lastName("Koval")
                .email("user@gmail.com")
                .password(bCryptPasswordEncoder.encode("user"))
                .role(ROLE_USER)
                .build();

       users.add(admin);
       users.add(user);
       userRepository.saveAll(users);

    } );
}

}