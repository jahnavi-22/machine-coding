package main.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Building {
    private String name;
    private final Map<String, Floor> floors = new HashMap<>();

    public void addFloor(Floor floor){
        String key = floor.getName().toLowerCase();
        if (floors.containsKey(key)) {
            System.out.println("Floor already exists");
        } else {
            floors.put(floor.getName(), floor);
        }
    }

    public boolean hasFloor(String name){
        return floors.containsKey(name.toLowerCase());
    }

    public Floor getFloor(String name){
        return floors.get(name.toLowerCase());
    }
}
