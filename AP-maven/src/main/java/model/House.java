package model;

import java.util.ArrayList;

public class House extends Building{
    int residents;


    House(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate, int residents) {
        super(hp, costs, name, buildingType, requiredWorkers, popularityRate);
        this.residents = residents;
    }
    public int getResidents(){
        return residents;
    }
}
