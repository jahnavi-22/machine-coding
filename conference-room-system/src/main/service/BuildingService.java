package main.service;

import main.models.Building;
import main.repository.BuildingRepository;

public class BuildingService {

    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void addBuilding(Building building){
        buildingRepository.addBuilding(building);
    }
}
