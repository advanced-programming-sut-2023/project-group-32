package model;

import java.util.ArrayList;

public class Barracks extends Building{
    ArrayList<Troop> trainableTroops;

    public Barracks(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate, ArrayList<Troop> trainableTroops) {
        super(hp, costs, name, buildingType, requiredWorkers, popularityRate);
        this.trainableTroops = trainableTroops;
    }

    public ArrayList<Troop> getTrainableTroops() {
        return trainableTroops;
    }
}
