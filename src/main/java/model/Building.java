package model;

import java.util.ArrayList;

public class Building implements Cloneable{
    public static ArrayList<Building> buildings;
    private int hp;
    ArrayList<InventorySlot> costs;
    String name;
    public enum BuildingType{CastleBuilding, farms, foodProccess, industrial, cityBuildings, weaponBuilding};
    BuildingType buildingType;
    int requiredWorkers;
    int popularityRate;
    Government owner;
    int x, y;

    Building(int hp, ArrayList<InventorySlot> costs, String name, BuildingType buildingType, int requiredWorkers, int popularityRate){
        this.hp = hp;
        this.costs = costs;
        this.name = name;
        this.buildingType = buildingType;
        this.requiredWorkers = requiredWorkers;
        this.popularityRate = popularityRate;
        owner = null;
    }
    public static Building getBuildingByName(String name){
        for(Building building : buildings){
            if(building.name.equals(name)){
                return building;
            }
        }
        return null;
    }

    public Government getOwner() {
        return owner;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public ArrayList<InventorySlot> getCosts() {
        return costs;
    }

    public String getName() {
        return name;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getRequiredWorkers() {
        return requiredWorkers;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
