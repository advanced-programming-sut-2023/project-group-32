package model;

import java.util.ArrayList;

public class ProductionBuilding extends Building{
    ArrayList<InventorySlot> outputs;
    ArrayList<InventorySlot> inputs;

    public ProductionBuilding(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate, ArrayList<InventorySlot> outputs, ArrayList<InventorySlot> inputs) {
        super(hp, costs, name, buildingType, requiredWorkers, popularityRate);
        this.outputs = outputs;
        this.inputs = inputs;
    }
}
