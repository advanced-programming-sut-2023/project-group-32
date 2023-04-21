package controller;

import model.*;

import java.util.Objects;

public class GameMenuController {
    Building selectedBuilding;
    Troop selectedTroop;
    public static Government currentGovernment;

    public static Map gameMap;

    public static void showPopularityFactors(){
        System.out.println("Food: " + currentGovernment.getFoodRate() * 4);
        System.out.println("Tax: " + currentGovernment.popularityOfTaxRate());
        System.out.println("Fear: " + (currentGovernment.getFearRate() * -1));
        System.out.println("Buildings: " + currentGovernment.popularityOfBuildings());
    }
    public static void showPopularity(){
        System.out.println(currentGovernment.getPopularity());
    }
    public void showFoodList(){
        Storage granary = null;

        for(Building building : currentGovernment.buildings){
            if(Objects.equals(building.getName(), "Granary")){
                granary = (Storage) building;
            }
        }
        if(granary == null){
            System.out.println("You don't have any food");
            return;
        }
        for(InventorySlot slot : granary.getInventory().Items){
            System.out.println(slot.getItem().getName() + ": " + slot.getAmount());
        }
    }
    public void changeFoodRate(int rateNumber){
        currentGovernment.setFoodRate(rateNumber);
    }
    public void showFoodRate(){
        System.out.println(currentGovernment.getFoodRate());
    }
    public void changeTaxRate(int rateNumber){
        currentGovernment.setTaxRate(rateNumber);
    }
    public void showTaxRate(){
        System.out.println(currentGovernment.getTaxRate());
    }
    public void changeFearRate(int rateNumber){
        currentGovernment.setFearRate(rateNumber);
    }
    public void trade(String content){

    }
    public void tradeList(){

    }
    public void tradeHistory(){

    }

    public void dropBuilding(int x, int y, String type) throws CloneNotSupportedException {
        Building building = Building.getBuildingByName(type);
        if(building == null){
            System.out.println("Invalid Building type");
            return;
        }
        if(x > gameMap.mapSize || x < 0 || y > gameMap.mapSize || y < 0){
            System.out.println("Invalid Coordinates");
            return;
        }
        if(gameMap.tiles[x][y].building != null){
            System.out.println("There is already a building on this tile!");
            return;
        }
        if(Objects.equals(gameMap.tiles[x][y].tileType, "Rock")){
            System.out.println("Can't build building on Rock!");
        }
        if(Objects.equals(building.getName(), "Iron Mine")){
            if(!Objects.equals(gameMap.tiles[x][y].tileType, "Iron")){
                System.out.println("You should build Iron mine on Iron!");
                return;
            }
        }
        if(Objects.equals(building.getName(), "Stone Mine")){
            if(!Objects.equals(gameMap.tiles[x][y].tileType, "Stone")){
                System.out.println("You should build Stone mine on Stone!");
                return;
            }
        }
        if(building.getBuildingType() == Building.BuildingType.farms){
            if(!Objects.equals(gameMap.tiles[x][y].tileType, "MeadowDense") && !Objects.equals(gameMap.tiles[x][y].tileType, "Grass")){
                System.out.println("You should build farms on Grass!");
                return;
            }
        }
        Building newBuilding = (Building) building.clone();

        newBuilding.setOwner(currentGovernment);
        newBuilding.setX(x);
        newBuilding.setY(y);

        currentGovernment.buildings.add(newBuilding);
        gameMap.tiles[x][y].building = newBuilding;
    }
    public void selectBuilding(int x , int y){
        for (Building building : currentGovernment.buildings){
            if(building.getX() == x && building.getY() == y){
                selectedBuilding = building;
                return;
            }
        }
        System.out.println("You don't have any buildings in the given coordinates");
    }
    public void CreateUnit(String troopName, int count) throws CloneNotSupportedException {
        if(!(selectedBuilding instanceof Barracks)){
            System.out.println("Can't Train troops in this building!");
            return;
        }
        Barracks currentBuilding = (Barracks) selectedBuilding;

        Troop troop = Troop.getTroopByName(troopName);

        if(troop == null){
            System.out.println("Invalid Troop name");
            return;
        }

        if(count <= 0){
            System.out.println("Invalid count");
            return;
        }

        if(!currentBuilding.getTrainableTroops().contains(troop)){
            System.out.println("Can't Train troops in this building!");
            return;
        }
        Storage Stockpile = (Storage) currentGovernment.getBuildingByName("Stockpile");

        if(Stockpile == null){
            System.out.println("You should build Stockpile first!");
            return;
        }
        int neededGold = troop.getPrice() * count;

        if(Stockpile.getInventory().getAmount("Gold") < neededGold){
            System.out.println("Not Enough Gold!");
            return;
        }

        if(Objects.equals(selectedBuilding.getName(), "Barracks")){
            Storage Armoury = (Storage) currentGovernment.getBuildingByName("Armoury");

            if(Armoury == null){
                System.out.println("You don't have an Armoury!");
                return;
            }
            if(!Armoury.getInventory().hasItems(troop.getEquipment())){
                System.out.println("You don't have needed equipment to train this troop");
                return;
            }
            Armoury.getInventory().removeItems(troop.getEquipment());
        }
        Stockpile.getInventory().addItem("Gold", -neededGold);

        for(int i = 0; i < count; i++)
        {
            Troop newTroop = (Troop) troop.clone();

            newTroop.setX(selectedBuilding.getX());
            newTroop.setY(selectedBuilding.getY());
            newTroop.setOwner(currentGovernment);

            currentGovernment.troops.add(newTroop);
            gameMap.tiles[currentBuilding.getX()][currentBuilding.getY()].troops.add(newTroop);
        }
    }
    public void repair(){

    }
    public void selectUnit(int x , int y){
        for(Troop troop : currentGovernment.troops){
            if(troop.getX() == x && troop.getY() == y){
                selectedTroop = troop;
                return;
            }
        }
        System.out.println("You don't have any troop in the given coordinates");
    }
    public void moveUnit(int x , int y){

    }
    public void patrolUnit(String content){

    }
    public void set(String content){

    }
    public void attack(int x , int y){

    }
    public void attack(String enemy){

    }
    public void digTunnel(int x , int y){

    }
    public void pourOil(String direction){

    }
    public void disbandUnit() {

    }
    public void build(String equipmentName){

    }

    //Shop
    public static void showPriceList(){
        for(Item item : Item.Items){
            System.out.printf("%s price: %d\n", item.getName(), item.getPrice());
        }
    }
    public static void buy(String name, int amount) {

        Storage Stockpile = (Storage) currentGovernment.getBuildingByName("Stockpile");

        if(Stockpile == null){
            System.out.println("You should build Stockpile first!");
            return;
        }
        Item item = Item.getItemByName(name);

        if(item == null){
            System.out.println("Invalid Item Name");
            return;
        }
        int currentGold = Stockpile.getInventory().getAmount("Gold");

        if(currentGold < item.getPrice() * amount){
            System.out.println("Not Enough Gold!");
            return;
        }
        Stockpile.getInventory().addItem(item, amount);
        Stockpile.getInventory().addItem("Gold", -item.getPrice() * amount);
    }
    public static void sell(String name, int amount){

        Storage Stockpile = (Storage) currentGovernment.getBuildingByName("Stockpile");

        if(Stockpile == null){
            System.out.println("You should build Stockpile first!");
            return;
        }
        Item item = Item.getItemByName(name);

        if(item == null){
            System.out.println("Invalid Item Name");
            return;
        }
        int currentItemAmount = Stockpile.getInventory().getAmount(name);

        if(currentItemAmount < amount){
            System.out.println("Not Enough " + name);
            return;
        }
        Stockpile.getInventory().addItem("Gold", item.getPrice() * amount * 7 / 10);
        Stockpile.getInventory().addItem(item, -amount);
    }
}
