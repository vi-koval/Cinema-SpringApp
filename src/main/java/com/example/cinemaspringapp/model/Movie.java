package com.example.cinemaspringapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "movies") //hibernate will create a table named "movies"
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieName;
    private String country;
    private String director;
    private String year;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Getter(AccessLevel.NONE)
    private LocalDateTime date;

    @Builder
    public Movie (String movieName, String country, String director, String year) {
        this.movieName = movieName;
        this.country = country;
        this.director = director;
        this.year = year;
    }

    @Override
    public String toString(){
        return movieName+" "+country+" "+director;
    }

}
