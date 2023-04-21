package model;

import java.util.ArrayList;

public class Storage extends Building{

    Inventory inventory;
    int capacity;

    public Storage(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate, Inventory inventory, int capacity) {
        super(hp, costs, name, buildingType, requiredWorkers, popularityRate);
        this.inventory = inventory;
        this.capacity = capacity;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
