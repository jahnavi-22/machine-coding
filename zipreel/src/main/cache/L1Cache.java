package main.cache;

import main.models.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class L1Cache {

    private static final int MAX_SIZE = 5;
    private final Map<Integer, LRUCache<String, List<Movie>>> userCache = new HashMap<>();

    public List<Movie> get(int userId, String key){
        if(!userCache.containsKey(userId))
            return null;
        return userCache.get(userId).get(key);
    }

    public void put(int userId, String key, List<Movie> movies){
        userCache.putIfAbsent(userId, new LRUCache<>(MAX_SIZE));
        userCache.get(userId).put(key, movies);
    }

    public void clear(){
        userCache.clear();
    }

    public void clearCacheForUser(int userId){
        userCache.remove(userId);
    }
}
