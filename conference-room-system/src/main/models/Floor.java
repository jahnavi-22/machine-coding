package main.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Floor {
    private String name;
    private final Map<String, Room> rooms = new HashMap<>();

    public void addRooms(Room room){
        String key = room.getId().toLowerCase();
        if (rooms.containsKey(key)) {
            System.out.println("Room already exists");
        } else {
            rooms.put(key, room);
        }
    }

    public boolean hasRoom(String id){
        return rooms.containsKey(id.toLowerCase());
    }

    public Room getRoom(String id){
        return rooms.get(id.toLowerCase());
    }

    public Map<String, Room> getAllRooms(){
        return rooms;
    }
}
