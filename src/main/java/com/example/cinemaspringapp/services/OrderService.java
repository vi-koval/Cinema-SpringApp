package com.example.cinemaspringapp.services;


import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.repository.MovieRepository;
import com.example.cinemaspringapp.repository.OrderRepository;
import com.example.cinemaspringapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public final static int PAGE_SIZE = 5;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        MovieRepository movieRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public void saveOrders (List<Order> orders) {
        User user = orders.get(0).getUser();
        List<Movie> movies = new ArrayList<>();

        for (Order order : orders) {
            order.setDate(LocalDateTime.now());

            Movie orderMovie = order.getMovie();
            movies.add(orderMovie);
        }

        userRepository.save(user);
        movieRepository.saveAll(movies);
        orderRepository.saveAll(orders);

    }

    @Transactional
    List <Order> makeOrder (Order order, List<Movie> movies) {
        List<Order> orders = new ArrayList<>();

        for (Movie movie: movies){

            orders.add(Order.builder()
                    .movie(movie)
                    .user(order.getUser())
                    .build());
        }
            return orders;
}

@Transactional
public void createOrder (Order order) {
//TODO create createOrder method
}

    public Page<Order> getPaginated(int pageNo, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        return orderRepository.findAll(pageable);
    }
    public List<Order> getOrdersByUserId(Long id) {
        return orderRepository.findTop5ByUserIdOrderByDateDesc(id);
    }
}
