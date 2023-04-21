package model;

import java.util.ArrayList;

public class ResourceBuilding extends Building{
    ArrayList<InventorySlot> output;

    public ResourceBuilding(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate, ArrayList<InventorySlot> output) {
        super(hp, costs, name, buildingType, requiredWorkers, popularityRate);
        this.output = output;
    }
}
