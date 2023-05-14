package model;

import java.util.ArrayList;

public class Government {
    int fearRate;
    int popularity;
    int foodRate;
    int taxRate;
    int population;
    int freePopulation;
    public User user;

    public String colour;
    public ArrayList<Building> buildings;
    public ArrayList<Troop> troops;
    public Building headquarter;

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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getFreePopulation() {
        return freePopulation;
    }

    public void setFreePopulation(int freePopulation) {
        this.freePopulation = freePopulation;
    }

    public Building getBuildingByName(String name){
        for(Building building : buildings){
            if(building.getName().equals(name))
                return building;
        }
        return null;
    }
    public int popularityOfTaxRate(){
        if (taxRate < 0)
        {
            Storage stockPile = (Storage) getBuildingByName("Stockpile");
            float unitTax = ((0.6f) + ((0.2f) * (taxRate + 1)));
            int totalTax = (int) (unitTax * population);

            if(stockPile.getInventory().getAmount("Gold") < totalTax){
                System.out.println("You don't have enough gold, tax rate reset to 0");
                taxRate = 0;
                return 1;
            }
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
            rate += building.getPopularityRate();
        }
        return rate;
    }
    public  int popularityOfFoodRate(){
        int foodTypes = getFoodTypes();
        return (foodRate * 4) + foodTypes - 1;
    }
    public void nextTurn()
    {
        updateBuildings();
        updateTroops();
        updateTax();
        updatePopularity();
    }
    void updatePopularity(){
        int popularityRate = popularityOfBuildings() + popularityOfTaxRate() + popularityOfFoodRate() - fearRate;
        popularity += popularityRate;
    }
    void updateTax(){
        float unitTax = 0;
        if(taxRate < 0){
            unitTax = (-0.6f) - ((0.2f) * (taxRate + 1));
        }
        if(taxRate > 0){
            unitTax = (0.6f) + ((0.2f) * (taxRate - 1));
        }
        int totalTax = (int) (unitTax * population);

        Storage StockPile = (Storage) getBuildingByName("Stockpile");
        StockPile.getInventory().addItem("Gold", totalTax);
    }
    void updateBuildings()
    {
        Storage Stockpile = (Storage) getBuildingByName("Stockpile");
        Inventory inventory = Stockpile.getInventory();

        for(Building building : buildings){
            if(building instanceof ProductionBuilding productionBuilding){
                productionBuilding.update(inventory);
            }
            if(building instanceof ResourceBuilding resourceBuilding){
                resourceBuilding.update(inventory);
            }
        }
        consumeFood(Stockpile);
    }
    void consumeFood(Storage Stockpile){
        updateFoodRate();
        int foodNeeded = foodNeeded();
        //Consume Food
        for(InventorySlot inventorySlot : Stockpile.getInventory().Items){
            if(inventorySlot.getItem().getItemType() == Item.ItemType.Food){
                int currentFood = inventorySlot.getAmount();

                if(currentFood >= foodNeeded){
                    inventorySlot.setAmount(inventorySlot.getAmount() - foodNeeded);
                    break;
                }
                inventorySlot.setAmount(0);
                foodNeeded -= currentFood;
            }
        }
    }
    void updateFoodRate(){
        //Check if needed food is more than the government is giving and then fix it
        int foodPower;
        int foodAmount = getFoodAmount();

        if(foodAmount < population * 0.5f) foodPower = -2;
        else if(foodAmount < population) foodPower = -1;
        else if(foodAmount < population * 1.5f) foodPower = 0;
        else if(foodAmount < population * 2) foodPower = 1;
        else foodPower = 2;

        if(foodPower < foodRate){
            System.out.println("You don't have enough food, food rate set to " + foodPower);
            foodRate = foodPower;
        }
    }
    int getFoodTypes(){
        int foodTypes = 0;

        Storage Stockpile = (Storage) getBuildingByName("Stockpile");
        Inventory inventory = Stockpile.getInventory();

        for(InventorySlot inventorySlot : inventory.Items){
            if(inventorySlot.getItem().getItemType() == Item.ItemType.Food){
                if(inventorySlot.getAmount() > 0){
                    foodTypes++;
                }
            }
        }
        return foodTypes;
    }
    int getFoodAmount(){
        int foodAmount = 0;

        Storage Stockpile = (Storage) getBuildingByName("Stockpile");
        Inventory inventory = Stockpile.getInventory();

        for(InventorySlot inventorySlot : inventory.Items){
            if(inventorySlot.getItem().getItemType() == Item.ItemType.Food){
                foodAmount += inventorySlot.getAmount();
            }
        }
        return foodAmount;
    }
    int foodNeeded(){
        float unitFood = 1;
        if(foodRate == -2) unitFood = 0;
        if(foodRate == -1) unitFood = 0.5f;
        if(foodRate == 1) unitFood = 1.5f;
        if(foodRate == 2) unitFood = 2;

        return (int) (unitFood * population);
    }
    void updateTroops(){
        for(Troop troop : troops){
            troop.update();
        }
    }
}
