package main.repository;

import main.models.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MovieRepository {
    private final HashMap<Integer, Movie> movieStore = new HashMap<>();

    public boolean addMovie(Movie movie){
        if(movieStore.containsKey(movie.getId()))
            return false;
        movieStore.put(movie.getId(), movie);
        return true;
    }


    public List<Movie> searchBy(String type, String value){
        return movieStore.values().stream()
                .filter(movie -> matchesSearch(movie, type, value))
                .collect(Collectors.toList());
    }

    public boolean matchesSearch(Movie movie, String type, String value){
        return switch(type.toUpperCase()){
            case "TITLE" -> movie.getTitle().equalsIgnoreCase(value);
            case "GENRE" -> movie.getGenre().equalsIgnoreCase(value);
            case "RELEASE_YEAR" -> String.valueOf(movie.getReleaseYear()).equals(value);
            default -> false;
        };
    }

    public List<Movie> multiSearch(String genre, int year, double rating){
        return movieStore.values().stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .filter(movie -> movie.getReleaseYear() == year)
                .filter(movie -> movie.getRating() == rating)
                .collect(Collectors.toList());
    }
}
