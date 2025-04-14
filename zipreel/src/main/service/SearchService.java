package main.service;

import main.cache.L1Cache;
import main.cache.L2Cache;
import main.models.Movie;
import main.repository.UserRepository;
import main.repository.MovieRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

    private int l1Hits = 0;
    private int l2Hits = 0;
    private int storeHits = 0;
    private int totalSearches = 0;

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final L1Cache l1Cache;
    private final L2Cache l2Cache;

    public SearchService(MovieRepository movieRepo, UserRepository userRepo, L1Cache l1, L2Cache l2) {
        this.movieRepository = movieRepo;
        this.userRepository = userRepo;
        this.l1Cache = l1;
        this.l2Cache = l2;
    }

    public List<String> search(int userId, String type, String value){
        totalSearches++;
        String cacheKey = type.toUpperCase() + ":" + value;

        //1. L1 Cache
        List<Movie> result = l1Cache.get(userId, cacheKey);
        if(result!=null){
            l1Hits++;
            return formatResult(result, "L1");
        }

        //2. L2 Cache
        result = l2Cache.get(cacheKey);
        if(result!=null){
            l2Hits++;
            l1Cache.put(userId, cacheKey, result);
            return formatResult(result, "L2");
        }

        //3.Primary DB
        result = movieRepository.searchBy(type, value);
        if(result!=null && !result.isEmpty()){
            storeHits++;
            l1Cache.put(userId, cacheKey, result);
            l2Cache.put(cacheKey, result);
            return formatResult(result, "DB");
        }

        return Collections.singletonList("No movies found");
    }

    public List<String> multiSearch(int userId, String genre, int releaseYear, double minRating){
        totalSearches++;
        String cacheKey = "MULTI:" + genre + ":" + releaseYear + ":" + minRating;

        //1. L1 Cache
        List<Movie> result = l1Cache.get(userId, cacheKey);
        if(result!=null){
            l1Hits++;
            return formatResult(result, "L1");
        }

        //2. L2 Cache
        result = l2Cache.get(cacheKey);
        if(result!=null){
            l2Hits++;
            l1Cache.put(userId, cacheKey, result);
            return formatResult(result, "L2");
        }

        //3.Primary DB
        result = movieRepository.multiSearch(genre, releaseYear, minRating);
        if(result!=null && !result.isEmpty()){
            storeHits++;
            l1Cache.put(userId, cacheKey, result);
            l2Cache.put(cacheKey, result);
            return formatResult(result, "DB");
        }

        return Collections.singletonList("No movies found");
    }

    public List<String> formatResult(List<Movie> result, String source){
        return result.stream()
                .map(movie -> movie.getTitle() + "Found in" + source)
                .collect(Collectors.toList());
    }

    public void viewStats() {
        System.out.println("L1 Cache Hits: " + l1Hits);
        System.out.println("L2 Cache Hits: " + l2Hits);
        System.out.println("Primary Store Hits: " + storeHits);
        System.out.println("Total Searches: " + totalSearches);
    }

    public void clearCache(String level) {
        switch (level.toUpperCase()) {
            case "L1" -> {
                l1Cache.clear();
                System.out.println("L1 cache cleared successfully");
            }
            case "L2" -> {
                l2Cache.clear();
                System.out.println("L2 cache cleared successfully");
            }
            default -> System.out.println("Invalid cache level");
        }
    }

}
