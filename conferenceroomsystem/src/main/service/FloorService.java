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

    public String addFloor(String buildingName, Floor floor) {
        return building.addFloor(buildingName, floor);
    }
}
