package controller;

import model.*;

import java.util.ArrayList;
import java.util.Objects;

public class GameMenuController {
    public static Building selectedBuilding;
    public static Troop selectedTroop;
    User currentUser;
    public static Government currentGovernment;

    static Government[] Players;
    User[] users;

    public static Map gameMap;

    public static void showPopularityFactors(){
        System.out.println("Food: " + currentGovernment.popularityOfFoodRate());
        System.out.println("Tax: " + currentGovernment.popularityOfTaxRate());
        System.out.println("Fear: " + (currentGovernment.getFearRate() * -1));
        System.out.println("Buildings: " + currentGovernment.popularityOfBuildings());
    }
    public static void showPopularity(){
        System.out.println(currentGovernment.getPopularity());
    }
    public static void showFoodList(){
        Storage granary = null;
        Storage stockpile = null;

        for(Building building : currentGovernment.buildings){
            if(Objects.equals(building.getName(), "Granary")){
                granary = (Storage) building;
            }
            if(Objects.equals(building.getName(), "Stockpile")){
                stockpile = (Storage) building;
            }
        }
        if(granary == null){
            System.out.println("You don't have any food");
            return;
        }
        if(stockpile == null){
            System.out.println("You don't have stockpile");
            return;
        }
        for(InventorySlot slot : stockpile.getInventory().Items){
            if(slot.getItem().getItemType() == Item.ItemType.Food)
                System.out.println(slot.getItem().getName() + ": " + slot.getAmount());
        }
    }
    public static void changeFoodRate(int rateNumber){
        currentGovernment.setFoodRate(rateNumber);
    }
    public static void showFoodRate(){
        System.out.println(currentGovernment.getFoodRate());
    }
    public static void changeTaxRate(int rateNumber){
        currentGovernment.setTaxRate(rateNumber);
    }
    public static void showTaxRate(){
        System.out.println(currentGovernment.getTaxRate());
    }
    public static void changeFearRate(int rateNumber){
        currentGovernment.setFearRate(rateNumber);
    }
    public static void trade(String content){
    }
    public static void tradeList(){
    }
    public static void tradeHistory(){
    }

    public static void dropBuilding(int x, int y, String type) throws CloneNotSupportedException {
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
        if(Objects.equals(building.getName(), "IronMine")){
            if(!Objects.equals(gameMap.tiles[x][y].tileType, "Iron")){
                System.out.println("You should build Iron mine on Iron!");
                return;
            }
        }
        if(Objects.equals(building.getName(), "StoneMine")){
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
        currentGovernment.setFreePopulation(currentGovernment.getFreePopulation() - newBuilding.getRequiredWorkers());

        gameMap.tiles[x][y].building = newBuilding;
        if(newBuilding instanceof House){
            currentGovernment.setPopulation(currentGovernment.getPopulation() + ((House) newBuilding).getResidents());
        }
    }
    public static void selectBuilding(int x , int y){
        for (Building building : currentGovernment.buildings){
            if(building.getX() == x && building.getY() == y){
                selectedBuilding = building;
                return;
            }
        }
        System.out.println("You don't have any buildings in the given coordinates");
    }
    public static void CreateUnit(String troopName, int count) throws CloneNotSupportedException {
        if(!(selectedBuilding instanceof Barracks currentBuilding)){
            System.out.println("Can't Train troops in this building!");
            return;
        }
        if(currentGovernment.getFreePopulation() < count){
            System.out.println("Not Enough Peasants");
            return;
        }

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
        currentGovernment.setFreePopulation(currentGovernment.getFreePopulation() - count);
    }
    public static void repair(){
        if(selectedBuilding.getBuildingType() != Building.BuildingType.CastleBuilding){
            System.out.println("You can't repair this building");
            return;
        }
        if(selectedBuilding.getHp() == 100){
            System.out.println("This building's hp is full");
            return;
        }

        Storage StockPile = (Storage) currentGovernment.getBuildingByName("Stockpile");

        if(StockPile == null){
            System.out.println("You should build stockpile first!");
            return;
        }
        int currentStone = StockPile.getInventory().getAmount("Stone");
        int neededStone = selectedBuilding.getCosts().get(0).getAmount() * (selectedBuilding.getFullHp() - selectedBuilding.getHp()) / selectedBuilding.getFullHp();

        if(neededStone > currentStone){
            System.out.println("Not Enough Stone!");
            return;
        }
        StockPile.getInventory().addItem("Stone", -neededStone);
        selectedBuilding.setHp(selectedBuilding.getHp());
    }
    public static void selectUnit(int x , int y){
        for(Troop troop : currentGovernment.troops){
            if(troop.getX() == x && troop.getY() == y){
                selectedTroop = troop;
                return;
            }
        }
        System.out.println("You don't have any troop in the given coordinates");
    }
    public static void moveUnit(int x , int y){
        if(!gameMap.tiles[x][y].reachable){
            System.out.println("Destination is not reachable");
            return;
        }
        if(selectedTroop == null){
            System.out.println("No Troops is selected!");
            return;
        }
        selectedTroop.setDestination(x, y);
    }
    public static void patrolUnit(int x1, int y1, int x2, int y2){
        if(selectedTroop == null){
            System.out.println("You should select a troop first!");
            return;
        }
        if(!gameMap.tiles[x1][y1].reachable){
            System.out.println("Destination is not reachable");
            return;
        }
        if(!gameMap.tiles[x2][y2].reachable){
            System.out.println("Destination is not reachable");
            return;
        }
        selectedTroop.SetPatrolRoute(x1, y1, x2, y2);
    }
    public static void set(int x, int y, String state){
        Troop currentTroop = null;
        for(Troop troop : currentGovernment.troops){
            if(troop.getX() == x && troop.getY() == y){
                currentTroop = troop;
                break;
            }
        }
        if(currentTroop == null){
            System.out.println("There is no troop in given tile!");
            return;
        }
        if(state.equals("Standing")){
            currentTroop.setState(Troop.State.Standing);
            return;
        }
        if(state.equals("Offensive")){
            currentTroop.setState(Troop.State.Offensive);
            return;
        }
        if(state.equals("Defensive")){
            currentTroop.setState(Troop.State.Defensive);
            return;
        }
        System.out.println("Invalid State!");
    }
    public static void attack(int x , int y){
        if(selectedTroop.getTroopType() != Troop.TroopType.Ranged){
            System.out.println("You should select a ranged Troop!");
            return;
        }
        selectedTroop.setTargetX(x);
        selectedTroop.setTargetY(y);
    }
    public static void attack(String enemy){
        if(selectedTroop == null){
            System.out.println("You should select a troop first!");
            return;
        }
        int x = 0;
        int y = 0;

        ArrayList<Troop> troops = gameMap.tiles[x][y].troops;

        for(Troop troop : troops){
            if(troop.getOwner() != currentGovernment){
                selectedTroop.setTargetEnemy(troop);
                break;
            }
        }
    }
    public static void disbandUnit() {
        currentGovernment.troops.remove(selectedTroop);

        int x = selectedBuilding.getX();
        int y = selectedBuilding.getY();

        gameMap.tiles[x][y].troops.remove(selectedTroop);
        selectedTroop = null;
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
    public static void nextTurn(){

        currentGovernment.nextTurn();

        int currentIndex = 0;

        for(int i = 0; i < Players.length; i++){
            if(Players[i] == currentGovernment){
                currentIndex = i;
            }
        }
        currentIndex++;
        if(currentIndex == Players.length){
            currentIndex = 0;
        }
        currentGovernment = Players[currentIndex];
        System.out.println("It is " + currentGovernment.user.getUsername() + "'s turn");
    }
    public static void dropRock(int x , int y , String direction){
        if (CheckCoordinates(x, y)) return;
        gameMap.tiles[x][y].rock = new Rock(x , y , direction);
        System.out.println("Rock added successfully!");
        gameMap.tiles[x][y].reachable = false;
    }
    public static void dropTree(int x , int y , String type){
        if (CheckCoordinates(x, y)) return;
        if(type.equals("DesertShrub") || type.equals("OliveTree") || type.equals("CherryPalm") || type.equals("DatePalm") || type.equals("CoconutPalm")){
            gameMap.tiles[x][y].tree = new Tree(x , y , type);
            System.out.println("Tree added successfully!");
        }
        System.out.println("Invalid type!");
    }
    private static boolean CheckCoordinates(int x, int y) {
        if(x >= gameMap.mapSize || x < 0 || y < 0 || y >= gameMap.mapSize){
            System.out.println("Wrong coordinates!");
            return true;
        }
        if(gameMap.tiles[x][y].rock != null || gameMap.tiles[x][y].building != null || gameMap.tiles[x][y].tree != null){
            System.out.println("This tile is not clear!");
            return true;
        }
        return false;
    }
}
