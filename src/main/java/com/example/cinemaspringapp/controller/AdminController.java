package com.example.cinemaspringapp.controller;

import com.example.cinemaspringapp.dto.MovieDTO;
import com.example.cinemaspringapp.dto.UserDTO;
import com.example.cinemaspringapp.exception.MovieException;
import com.example.cinemaspringapp.exception.UserException;
import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.repository.MovieRepository;
import com.example.cinemaspringapp.repository.OrderRepository;
import com.example.cinemaspringapp.services.MovieService;
import com.example.cinemaspringapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user/admin")
public class AdminController {

    private final OrderService orderService;
    private final MovieService movieService;

    @Autowired
    public AdminController(OrderService orderService,
                           MovieService movieService) {
        this.orderService = orderService;
        this.movieService = movieService;

    }

    @GetMapping("/all-orders")
    public String getAllOrders(@RequestParam("page") Optional<Integer> pageNo,
                               @RequestParam("sortBy") Optional<String> sortBy,
                               @RequestParam("direction") Optional<String> direction,
                               Model model) {

        int currentPage = pageNo.orElse(1);
        String sort = sortBy.orElse("id");
        String dir = "asc".equalsIgnoreCase(direction.orElse("asc")) ? "asc" : "desc";

        Page<Order> page = orderService.getPaginated(currentPage, sort, dir);
        List<Order> orders = page.getContent();

        model.addAttribute("orders", orders);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sort);
        model.addAttribute("direction", dir);
        model.addAttribute("reverseDirection", dir.equals("asc") ? "desc" : "asc");

        return "user/admin/orders";
    }

    @GetMapping("/movies")
    public String listMovies(@RequestParam("page") Optional<Integer> pageNo,
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

        return "user/admin/movies";
    }


    @GetMapping("movies/new")
    public String createNewMovie(@ModelAttribute("movie") MovieDTO movieDTO, Model model) {
        model.addAttribute("exception", "");
        return "user/admin/create-movie";

    }

    @PostMapping("/movies")
    public String saveNewMovie(
            @ModelAttribute("movie") @Valid MovieDTO movieDTO,
            BindingResult bindingResult,
            Model model) {

        model.addAttribute("exception", "");

        if (bindingResult.hasErrors()) {
            return "/error";
        }

            try {
                movieService.saveMovie(movieDTO);
            } catch (MovieException e) {
                model.addAttribute("exception", movieDTO.getMovieName());
                return "user/admin/movies";
            }
            return "redirect:/user/admin/movies";
        }


        @GetMapping("movies/delete")
        public String deleteMovie ( @RequestParam("id") long id){
            movieService.deleteMovieById(id);
            return "redirect:/user/admin/movies";
        }


        @GetMapping("/movies/edit/{id}")
        public String editMovieForm (@PathVariable Long id, Model model) {
            model.addAttribute("movie", movieService.getMovieById(id));
            return "/user/admin/edit-movie";
        }


        @PostMapping("/movies/{id}")
        public String editMovie (@PathVariable Long id,
                @ModelAttribute("movie") Movie movie, Model model){

            Movie existingMovie = (Movie) movieService.getMovieById(id);

            existingMovie.setId(id);
            existingMovie.setMovieName(movie.getMovieName());
            existingMovie.setDirector(movie.getDirector());
            existingMovie.setCountry(movie.getCountry());
            existingMovie.setYear(movie.getYear());


            movieService.editMovie(existingMovie);
            return "redirect:/user/admin/movies";

        }
    }

