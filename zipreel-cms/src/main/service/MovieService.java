package main.service;

import main.models.Movie;
import main.repository.MovieRepository;

import java.util.List;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public boolean addMovie(Movie movie){
        return movieRepository.addMovie(movie);
    }

    public List<Movie> searchBy(String type, String value) {
        return movieRepository.searchBy(type, value);
    }

    public List<Movie> multiSearch(String genre, int year, double minRating) {
        return movieRepository.multiSearch(genre, year, minRating);
    }

}
