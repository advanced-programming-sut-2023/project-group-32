package model;

import java.util.ArrayList;

public class CastleDefence extends Building{
    int damage;

    public CastleDefence(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate, int damage) {
        super(hp, costs, name, buildingType, requiredWorkers, popularityRate);
        this.damage = damage;
    }
}
