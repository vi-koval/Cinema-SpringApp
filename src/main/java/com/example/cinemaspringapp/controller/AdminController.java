package com.example.cinemaspringapp.controller;

import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.services.MovieService;
import com.example.cinemaspringapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
   public String listMovies (@RequestParam("page") Optional<Integer> pageNo,
                             @RequestParam("sortBy") Optional<String> sortBy,
                             @RequestParam("direction") Optional<String> direction,
                             Model model){

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

   @GetMapping("/new")
   public String newMovie (Model model){

       model.addAttribute("movie", new Movie());
       return "user/admin/orders";
   }


//   @PostMapping("/movies")
//   public String saveMovies (@ModelAttribute ("movie") Movie movie){
//       movieService.saveMovies((List<Movie>) movie);
//       return "redirect:/user/admin/orders";
//   }
}
