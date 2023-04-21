package model;

import java.util.ArrayList;

public class Fortification extends Building {

    int fireRange;
    int defendRange;

    public Fortification(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate, int fireRange, int defendRange) {
        super(hp, costs, name, buildingType, requiredWorkers, popularityRate);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }
}
