package main.repository;

import main.models.Building;

import java.util.HashMap;
import java.util.Map;

public class BuildingRepository {
    private final Map<String, Building> buildingStore = new HashMap<>();

    public boolean addBuilding(Building building){
        String name = building.getName().toLowerCase();
        if (buildingStore.containsKey(name)) {
            System.out.println("Building already exists");
            return false;
        } else {
            buildingStore.put(name, building);
            return true;
        }
    }

    public Building getBuilding(String name){
        return buildingStore.get(name.toLowerCase());
    }

    public boolean hasBuilding(String name){
        return buildingStore.containsKey(name.toLowerCase());
    }

    public Map<String, Building> getAllBuildings() {
        return buildingStore;
    }

}
