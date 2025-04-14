package main.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    private int id;
    private String title;
    private String genre;
    private int releaseYear;
    private double rating;
}