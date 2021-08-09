package com.example.cinemaspringapp.services;

import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.model.Order;
import com.example.cinemaspringapp.model.User;
import com.example.cinemaspringapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


import static com.example.cinemaspringapp.services.OrderService.PAGE_SIZE;


@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public final static int PAGE_SIZE = 5;

    @Autowired
    public MovieService (MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }


    public Page<Movie> getPaginatedMovie(int pageNo, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageableMovie = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        return  movieRepository.findAll(pageableMovie);
    }

    public List<Movie> getMovieById(Long id) {
        return movieRepository.findMovieById(id);
    }
}
