package com.example.cinemaspringapp.dto;

import javax.validation.constraints.*;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {

    @Size(min = 3, max = 20, message = "{movieName.error}")
    @NotBlank
    private String movieName;

    @Size(min = 3, max = 20, message = "{director.error}")
    @NotBlank
    private String director;

    @Size(min = 3, max = 20, message = "{country.error}")
    @NotBlank
    private String country;

    @Size(min = 4, max = 4, message = "{year.error}")
    @NotBlank
    private String year;
}

