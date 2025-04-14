package main;

import main.cache.L1Cache;
import main.cache.L2Cache;
import main.models.Movie;
import main.models.User;
import main.repository.MovieRepository;
import main.repository.UserRepository;
import main.service.MovieService;
import main.service.SearchService;
import main.service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZipReelApplication {

    private final MovieService movieService;
    private final UserService userService;
    private final SearchService searchService;

    public ZipReelApplication(MovieService movieService, UserService userService, SearchService searchService) {
        this.movieService = movieService;
        this.userService = userService;
        this.searchService = searchService;
    }

    public void processCommand(String commandLine){
        if(commandLine == null || commandLine.isEmpty()){
            System.out.println("Invalid commandLine");
            return;
        }
        String parts[] = commandLine.trim().split(" ", 2);
        String command = parts[0];
        String params = parts.length > 1 ? parts[1] : "";

        switch (command.toUpperCase()) {
            case "ADD_MOVIE" -> handleAddMovie(params);
            case "ADD_USER" -> handleAddUser(params);
            case "SEARCH" -> handleSearch(params);
            case "SEARCH_MULTI" -> handleSearchMulti(params);
            case "VIEW_CACHE_STATS" -> handleViewCacheStats();
            case "CLEAR_CACHE" -> handleClearCache(params);
            default -> System.out.println("Invalid command");
        }
    }

    public void handleAddMovie(String params){
        try{
            Pattern pattern = Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"\\s+(\\d{4})\\s+([\\d.]+)");
            Matcher matcher = pattern.matcher(params);

            if(matcher.matches()){
                int id = Integer.parseInt(matcher.group(1));
                String title = matcher.group(2);
                String genre = matcher.group(3);
                int releaseYear = Integer.parseInt(matcher.group(4));
                double rating = Double.parseDouble(matcher.group(5));

                Movie movie = new Movie(id, title, genre, releaseYear, rating);
                boolean movieAddedSuccessfully = movieService.addMovie(movie);

                if (movieAddedSuccessfully) {
                    System.out.println("Movie '" + title + "' added successfully");
                } else {
                    System.out.println("Movie with ID " + id + " already exists");
                }
            } else {
                System.out.println("Invalid ADD_MOVIE format");
            }
        }catch (Exception e) {
            System.out.println("Error processing ADD_MOVIE: " + e.getMessage());
        }
    }


    public void handleAddUser(String params){
        try{
            Pattern pattern = Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(params);

            if(matcher.matches()){
                int id = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2);
                String preferredGenre = matcher.group(3);

                User user = new User(id, name, preferredGenre);
                boolean userAddedSuccessfully = userService.addUser(user);

                if(userAddedSuccessfully){
                    System.out.println("User '" + name + "' added successfully");
                }else{
                    System.out.println("User with ID " + id + " already exists");
                }
            }else{
                System.out.println("Invalid ADD_USER format");
            }
        }catch (Exception e){
            System.out.println("Error processing ADD_USER: " + e.getMessage());
        }
    }

    private void handleSearch(String args) {
        try {
            // Pattern: userId searchType "searchValue"
            Pattern pattern = Pattern.compile("(\\d+)\\s+(TITLE|GENRE|RELEASE_YEAR)\\s+\"?([^\"]+)\"?");
            Matcher matcher = pattern.matcher(args);

            if (matcher.matches()) {
                int userId = Integer.parseInt(matcher.group(1));
                String searchType = matcher.group(2).toUpperCase();
                String searchValue = matcher.group(3);

                List<String> results = searchService.search(userId, searchType, searchValue);
                if (results.isEmpty()) {
                    System.out.println("No results found");
                } else {
                    results.forEach(System.out::println);
                }
            } else {
                System.out.println("Invalid SEARCH format");
            }

        } catch (Exception e) {
            System.out.println("Error processing SEARCH: " + e.getMessage());
        }
    }
    private void handleSearchMulti(String args) {
        try {
            // Pattern: userId genre releaseYear minRating
            Pattern pattern = Pattern.compile("(\\d+)\\s+\"([^\"]+)\"\\s+(\\d{4})\\s+([\\d.]+)");
            Matcher matcher = pattern.matcher(args);

            if (matcher.matches()) {
                int userId = Integer.parseInt(matcher.group(1));
                String genre = matcher.group(2);
                int releaseYear = Integer.parseInt(matcher.group(3));
                double minRating = Double.parseDouble(matcher.group(4));

                List<String> results = searchService.multiSearch(userId, genre, releaseYear, minRating);
                if (results.isEmpty()) {
                    System.out.println("No results found");
                } else {
                    results.forEach(System.out::println);
                }
            } else {
                System.out.println("Invalid SEARCH_MULTI format");
            }

        } catch (Exception e) {
            System.out.println("Error processing SEARCH_MULTI: " + e.getMessage());
        }
    }

    private void handleViewCacheStats() {
        try {
            searchService.viewStats(); // internally prints everything
        } catch (Exception e) {
            System.out.println("Error processing VIEW_CACHE_STATS: " + e.getMessage());
        }
    }

    private void handleClearCache(String args) {
        try {
            searchService.clearCache(args.trim());
        } catch (Exception e) {
            System.out.println("Error processing CLEAR_CACHE: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        System.out.println("Started ZipReel Application!");

        MovieRepository movieRepo = new MovieRepository();
        UserRepository userRepo = new UserRepository();

        L1Cache l1Cache = new L1Cache();
        L2Cache l2Cache = new L2Cache();

        MovieService movieService = new MovieService(movieRepo);
        UserService userService = new UserService(userRepo);
        SearchService searchService = new SearchService(movieRepo, userRepo, l1Cache, l2Cache);

        ZipReelApplication app = new ZipReelApplication(movieService, userService, searchService);

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String command = sc.nextLine();
            app.processCommand(command);
        }
    }
}