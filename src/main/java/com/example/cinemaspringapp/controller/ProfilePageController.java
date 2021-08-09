package com.example.cinemaspringapp.controller;

import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.repository.MovieRepository;
import com.example.cinemaspringapp.services.MovieService;
import com.example.cinemaspringapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfilePageController {

    private final OrderService orderService;
    private final MovieService movieService;
    private final MovieRepository movieRepository;

    @Autowired
    public ProfilePageController (OrderService orderService, MovieService movieService, MovieRepository movieRepository){
        this.orderService = orderService;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/profile-page")
    public String getProfile(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        List<Order> ordersByUserId = orderService.getOrdersByUserId(currentUser.getId());
        List<Movie> moviesByUserId = movieService.getMovieById(currentUser.getId());

        model.addAttribute("user", currentUser);
        model.addAttribute("userOrders", ordersByUserId);
        model.addAttribute("userMovie", moviesByUserId);

        return "user/profile-page";
    }

//    @GetMapping("/schedule")
//    public String index(Model model){
//        return null;
//    }

    @GetMapping("/{id}")
    public String getMovies(@PathVariable("id") long id, Model model){
        Optional<Movie> movies = movieRepository.findById(id);
        return "user/profile-page";
    }

}
