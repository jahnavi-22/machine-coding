package main.service;

import main.models.Building;
import main.models.Floor;
import main.repository.BuildingRepository;

public class FloorService {
    private final BuildingRepository buildingRepository;
    private final Building building;

    public FloorService(BuildingRepository buildingRepository, Building building) {
        this.buildingRepository = buildingRepository;
        this.building = building;
    }

    public void addFloor(Floor floor) {
        if (buildingRepository.hasBuilding(building.getName())) {
            building.addFloor(floor);
        } else {
            System.out.println("Building does not exist");
        }
    }

    public boolean hasFloor(String name) {
        return building.hasFloor(name);
    }

    public Floor getFloor(String name) {
        return building.getFloor(name);
    }
}