package com.example.cinemaspringapp.repository;


import com.example.cinemaspringapp.model.Movie;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository <Movie, Long> {

    Optional<Movie> findById (Long id);
    List<Movie> findMovieById (Long id);
//    Movie findMovieBy (Long id);
    Movie getMovieById(Long id);

    Optional<Movie> findMovieByMovieName(String movieName);

}
