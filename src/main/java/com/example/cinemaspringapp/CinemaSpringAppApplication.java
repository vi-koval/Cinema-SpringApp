package com.example.cinemaspringapp;

import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.repository.MovieRepository;
import com.example.cinemaspringapp.repository.OrderRepository;
import com.example.cinemaspringapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;


import static com.example.cinemaspringapp.model.Role.ROLE_ADMIN;
import static com.example.cinemaspringapp.model.Role.ROLE_USER;

@SpringBootApplication
public class CinemaSpringAppApplication {
    @PostConstruct
    public void init (){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CinemaSpringAppApplication.class, args);
    }

@Bean
public CommandLineRunner runApplication(UserRepository userRepository,
                                        MovieRepository movieRepository,
                                        OrderRepository orderRepository) {

    return (args -> {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Random random = new Random();
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

       List<Movie> movies = new ArrayList<>();

       for (int i=0; i < 10; i++) {
           movies.add(Movie.builder()
                           .movieName("Movie name" + i)
                           .director("Director" + i)
                           .country("Country" + i)
                           .year(i)
                           .build());
       }
        movieRepository.saveAll(movies);
        LocalDateTime localDateTime = LocalDateTime.now();

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            orders.add(Order.builder()
                            .seat(i)
                            .date(localDateTime)
                            .movie(movies.get(random.nextInt(movies.size())))
                            .user(users.get(random.nextInt(users.size())))
                            .build());
        }
orderRepository.saveAll(orders);

    }
    );
}

}