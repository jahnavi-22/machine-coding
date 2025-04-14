package main.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {
    private final int capacity;
    private final Map<K, V> data = new HashMap<>();
    private final Map<K, Integer> frequency = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key){
        if(data.containsKey(key))
            return null;
        frequency.put(key, frequency.getOrDefault(key, 0) + 1);
        return data.get(key);
    }

    public void put(K key, V value) {
        if (capacity == 0) return;

        if (data.containsKey(key)) {
            // update value and bump frequency
            data.put(key, value);
            frequency.put(key, frequency.getOrDefault(key, 0) + 1);
            return;
        }
        if (data.size() >= capacity) {
            // need to evict
            K lfuKey = getLFUKey();
            data.remove(lfuKey);
            frequency.remove(lfuKey);
        }
        data.put(key, value);
        frequency.put(key, 1);
    }

    private K getLFUKey() {
        return frequency.entrySet().stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void clear() {
        data.clear();
        frequency.clear();
    }

}
