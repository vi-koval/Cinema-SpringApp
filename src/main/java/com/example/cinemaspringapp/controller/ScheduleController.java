package com.example.cinemaspringapp.controller;

import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.repository.MovieRepository;
import com.example.cinemaspringapp.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ScheduleController {


    private final MovieService movieService;

    public ScheduleController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/schedule")
    public String getSchedule(@RequestParam("page") Optional<Integer> pageNo,
                              @RequestParam("sortBy") Optional<String> sortBy,
                              @RequestParam("direction") Optional<String> direction,
                              Model model) {

        int currentPage = pageNo.orElse(1);
        String sort = sortBy.orElse("id");
        String dir = "asc".equalsIgnoreCase(direction.orElse("asc")) ? "asc" : "desc";

        Page<Movie> page = movieService.getPaginatedMovie(currentPage, sort, dir);
        List<Movie> movies = page.getContent();


        model.addAttribute("movies", movies);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sort);
        model.addAttribute("direction", dir);
        model.addAttribute("reverseDirection", dir.equals("asc") ? "desc" : "asc");

        return "/schedule";

    }
}
