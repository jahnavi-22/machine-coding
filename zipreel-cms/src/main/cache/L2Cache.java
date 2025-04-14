package main.cache;

import main.models.Movie;

import java.util.List;

public class L2Cache {
    private static final int MAX_SIZE = 20;
    private final LFUCache<String, List<Movie>> globalCache = new LFUCache<>(MAX_SIZE);

    public List<Movie> get(String key) {
        return globalCache.get(key);
    }

    public void put(String key, List<Movie> movies) {
        globalCache.put(key, movies);
    }

    public void clear() {
        globalCache.clear();
    }
}
