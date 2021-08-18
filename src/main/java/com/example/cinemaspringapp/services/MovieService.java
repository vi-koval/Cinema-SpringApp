package com.example.cinemaspringapp.services;

import com.example.cinemaspringapp.dto.MovieDTO;
import com.example.cinemaspringapp.exception.MovieException;
import com.example.cinemaspringapp.model.Movie;
import com.example.cinemaspringapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.cinemaspringapp.messages.Messages.MOVIE_ALREADY_EXISTS_MSG;


@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public final static int PAGE_SIZE = 5;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Page<Movie> getPaginatedMovie(int pageNo, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageableMovie = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        return movieRepository.findAll(pageableMovie);
    }

    public List<Movie> getMovieById(Long id) {
        return movieRepository.findMovieById(id);
    }

    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    public void saveMovie (MovieDTO movieDTO) throws MovieException {

        if (movieRepository.findMovieByMovieName(movieDTO.getMovieName())
                .isPresent()) {
            throw new MovieException(
                    String.format(MOVIE_ALREADY_EXISTS_MSG, movieDTO.getMovieName())
            );
        }

        Movie newMovie = Movie.builder()
                .movieName(movieDTO.getMovieName())
                .country(movieDTO.getCountry())
                .director(movieDTO.getDirector())
                .year(movieDTO.getYear())
                .build();

        movieRepository.save(newMovie);
    }

    public Movie editMovie (Movie movie) {
        return movieRepository.save(movie);
    }
}
