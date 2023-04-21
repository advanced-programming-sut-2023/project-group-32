package model;

import java.util.ArrayList;

public class Government {
    int fearRate;
    int popularity;
    int foodRate;
    int taxRate;

    public ArrayList<Building> buildings;
    public ArrayList<Troop> troops;

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public Building getBuildingByName(String name){
        for(Building building : buildings){
            if(building.getName().equals(name))
                return building;
        }
        return null;
    }
    public int popularityOfTaxRate(){
        if (taxRate < 0){
            return -taxRate * 2 + 1;
        }
        else if(taxRate == 0){
            return 1;
        }
        else if(taxRate < 5){
            return -taxRate * 2;
        }
        else{
            return -taxRate * 2 - (taxRate - 4) * 2;
        }
    }
    public int popularityOfBuildings(){
        int rate = 0;

        for(Building building : buildings){
            rate += building.popularityRate;
        }
        return rate;
    }
}
