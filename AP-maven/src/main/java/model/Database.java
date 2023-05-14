package model;

import controller.Controller;
import view.MainMenu;
import view.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static void run() throws IOException, InterruptedException, CloneNotSupportedException {
        InitializeDatabase();
        String file = "players.json";
        StringBuilder lines = new StringBuilder();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null)
                lines.append(line).append("\n");
            Controller.getData(lines.toString());
        } catch (IOException e){
            System.out.println("ERROR!");
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader("stayLoggedIn.txt"));
        String name = bufferedReader.readLine();
        bufferedReader.close();
        if(name != null){
            Menu.currentUser = User.getUserByUsername(name);
            MainMenu.run();
        }
    }
    public static void update() throws IOException {
        FileWriter fileWriter = new FileWriter("players.json");
        fileWriter.write("");
        fileWriter.close();
        for (User user : User.getUsers()) {
            User.addUserToFile(user);
        }
    }
    public static void InitializeDatabase(){
        InitializeItems();
        InitializeTroops();
        InitializeBuildings();
    }
    private static void InitializeItems(){
        Item.Items.add(new Item("Apple" , 1 , Item.ItemType.Food));
        Item.Items.add(new Item("Wheat" , 1 , Item.ItemType.Material));
        Item.Items.add(new Item("Oat" , 1 , Item.ItemType.Material));
        Item.Items.add(new Item("Beer" , 1 , Item.ItemType.Material));
        Item.Items.add(new Item("Wood" , 1 , Item.ItemType.Material));
        Item.Items.add(new Item("Bread" , 1 , Item.ItemType.Food));
        Item.Items.add(new Item("Cheese" , 1 , Item.ItemType.Food));
        Item.Items.add(new Item("Meat" , 1 , Item.ItemType.Food));
        Item.Items.add(new Item("Gold" , 2 , Item.ItemType.Material));
        Item.Items.add(new Item("Stone" , 2 , Item.ItemType.Material));
        Item.Items.add(new Item("Iron" , 2 , Item.ItemType.Material));
        Item.Items.add(new Item("Bitumen" , 1 , Item.ItemType.Material));
        Item.Items.add(new Item("Armor" , 2 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Bow" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Ladder" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Spear" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Sword" , 2 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Horse" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Sling" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Hook" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Grenade" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Cudgel" , 1 , Item.ItemType.Weapon));
        Item.Items.add(new Item("Torch" , 1 , Item.ItemType.Weapon));
    }
    private static void InitializeBuildings(){
        Building.buildings.add(new ProductionBuilding(100, new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood"), 5))), "WheatFarm", Building.BuildingType.farms, 1, 0, new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wheat"), 2))), new ArrayList<>()));
        Building.buildings.add(new CastleDefence(100 , new ArrayList<>() , "SmallStoneGateHouse" , Building.BuildingType.CastleBuilding
                , 0 ,  0, 5));
        Building.buildings.add(new CastleDefence(100 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 20))) , "LargeStoneGateHouse"
                , Building.BuildingType.CastleBuilding , 0 , 0,  5));
        Building.buildings.add(new CastleDefence(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 10))) , "DrawBridge"
                , Building.BuildingType.CastleBuilding , 0 , 0,  0));
        Building.buildings.add(new Fortification(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 10))) , "LookoutTower"
                , Building.BuildingType.CastleBuilding , 0 , 0,  10 , 10));
        Building.buildings.add(new Fortification(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 10))) , "PerimeterTower"
                , Building.BuildingType.CastleBuilding , 0 ,0 ,  5 , 5));
        Building.buildings.add(new Fortification(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 15))) , "DefenceTurret"
                , Building.BuildingType.CastleBuilding , 0 , 0,  4 , 5));
        Building.buildings.add(new Fortification(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 35))) , "SquareTower"
                , Building.BuildingType.CastleBuilding , 0 , 0,  5 , 5));
        Building.buildings.add(new Fortification(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 40))) , "RoundTower"
                , Building.BuildingType.CastleBuilding , 0 , 0 , 5 , 5));
        Building.buildings.add(new Storage(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 5))) , "Armoury"
                , Building.BuildingType.CastleBuilding , 0 , 0 ,new Inventory(1000), 1000));
        Building.buildings.add(new Barracks(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 15))) , "Barracks"
                , Building.BuildingType.CastleBuilding , 0 , 0 , new ArrayList<>(List.of(Troop.getTroopByName("Archer") , Troop.getTroopByName("Crossbowmen")
                , Troop.getTroopByName("Spearmen"), Troop.getTroopByName("Pikemen") , Troop.getTroopByName("Macemen") , Troop.getTroopByName("Swordsmen")
                , Troop.getTroopByName("Knight") , Troop.getTroopByName("Tunneler") , Troop.getTroopByName("Laddermen") , Troop.getTroopByName("BlackMonk")))));
        Building.buildings.add(new Barracks(60 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 10))) , "MercenaryPost"
                , Building.BuildingType.CastleBuilding , 0 ,0  , new ArrayList<>(List.of(Troop.getTroopByName("ArcherBow") , Troop.getTroopByName("Slaves")
                , Troop.getTroopByName("Slingers") , Troop.getTroopByName("Assassins") , Troop.getTroopByName("HorseArcher") , Troop.getTroopByName("ArabianSwordsmen") , Troop.getTroopByName("FireThrowers")))));
        Building.buildings.add(new Barracks(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 10) , new InventorySlot(Item.getItemByName("Gold") , 100))) , "EngineerGuild"
                , Building.BuildingType.CastleBuilding , 0 ,2 , new ArrayList<>(List.of(Troop.getTroopByName("Engineer") , Troop.getTroopByName("Worker")))));
        Building.buildings.add(new CastleDefence(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 6))) , "KillingPit"
                , Building.BuildingType.CastleBuilding , 0 , 0 , 5));
        Building.buildings.add(new ProductionBuilding(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Iron") , 10) , new InventorySlot(Item.getItemByName("Gold") , 100))) , "OilSmelter"
                , Building.BuildingType.CastleBuilding , 1 ,0 ,   new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Iron") , 5)))
                , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Ladder") , 20) , new InventorySlot(Item.getItemByName("Grenade") , 20)))));
        Building.buildings.add(new CastleDefence(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Bitumen") , 2))) , "PitchDitch"
                , Building.BuildingType.CastleBuilding , 0 , 0, 5));
        Building.buildings.add(new CastleDefence(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 10) , new InventorySlot(Item.getItemByName("Gold") , 100))) , "CageWarDogs"
                , Building.BuildingType.CastleBuilding , 0 , 0 , 5));
        Building.buildings.add(new CastleDefence(50 , null , "SiegeTent"
                , Building.BuildingType.CastleBuilding , 0 , 0 , 5));
        Building.buildings.add(new ProductionBuilding(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 20) , new InventorySlot(Item.getItemByName("Gold") , 400))) , "Stable"
                , Building.BuildingType.cityBuildings , 0  , 0 ,new ArrayList<>() , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Horse") , 20)))));
        Building.buildings.add(new ProductionBuilding(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 20) , new InventorySlot(Item.getItemByName("Gold") , 100))) , "Inn"
                , Building.BuildingType.foodProcess, 1 , 0 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Oat") , 5))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Beer") , 5)))));
        Building.buildings.add(new ProductionBuilding(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 20))) , "Mill"
                , Building.BuildingType.foodProcess, 3 ,0  , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wheat") , 5))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Flour") , 5)))));
        Building.buildings.add(new ProductionBuilding(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 10))) , "Bakery"
                , Building.BuildingType.foodProcess, 1 ,0  , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Flour") , 20))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Bread") , 20)))));
        Building.buildings.add(new ProductionBuilding(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 10))) , "Brewer"
                , Building.BuildingType.foodProcess, 1 ,4  , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Oat") , 20))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Beer") , 20)))));
        Building.buildings.add(new Storage(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 5))) , "Granary" , Building.BuildingType.foodProcess, 0 ,2 ,  new Inventory(1000), 1000));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 20))) , "IronMine"
                , Building.BuildingType.industrial , 2 , 2, new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Iron") , 20)))));
        Building.buildings.add(new Building(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 5))) , "Market"
                , Building.BuildingType.industrial , 1 , 2));
        Building.buildings.add(new ProductionBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 5))) , "OxTether"
                , Building.BuildingType.industrial , 1  ,0  , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 12))) , new ArrayList<>()));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 20))) , "PitchDig"
                , Building.BuildingType.industrial , 1 , 0 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Bitumen") , 10)))));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 20))) , "Quarry"
                , Building.BuildingType.industrial , 3 , 2 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Stone") , 20)))));
        Building.buildings.add(new Storage(40 , new ArrayList<>() , "Stockpile"
                , Building.BuildingType.industrial , 0 , 0 ,new Inventory(1000) ,  1000));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 3))) , "WoodCutter"
                , Building.BuildingType.industrial , 1 , 2, new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 20)))));
        Building.buildings.add(new Building(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 6))) , "Hovel"
                , Building.BuildingType.cityBuildings , 0 ,  7));
        Building.buildings.add(new Building(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Gold") , 250))) , "Church"
                , Building.BuildingType.cityBuildings , 0 , 10));
        Building.buildings.add(new Building(30 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Gold") , 1000))) , "Cathedral"
                , Building.BuildingType.cityBuildings , 0 , 0));
        Building.buildings.add(new ProductionBuilding(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Gold") , 100) , new InventorySlot(Item.getItemByName("Wood") , 20))) , "Armourer"
                , Building.BuildingType.weaponBuilding , 1 ,0 ,   new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Iron") , 20))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Armor") , 20)))));
        Building.buildings.add(new ProductionBuilding(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Gold") , 100) , new InventorySlot(Item.getItemByName("Wood") , 20))) , "Blacksmith"
                , Building.BuildingType.weaponBuilding , 1 ,0 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Iron") , 20))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Sword")
                , 20) , new InventorySlot(Item.getItemByName("Sling") , 20) , new InventorySlot(Item.getItemByName("Cudgel") , 20) , new InventorySlot(Item.getItemByName("Torch") , 20) , new InventorySlot(Item.getItemByName("Hook") , 20)))));
        Building.buildings.add(new ProductionBuilding(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Gold") , 100) , new InventorySlot(Item.getItemByName("Wood") , 20))) , "Fletcher"
                , Building.BuildingType.weaponBuilding , 1 , 0 ,  new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("wood") , 5))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Bow") , 5)))));
        Building.buildings.add(new ProductionBuilding(50 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Gold") , 100) , new InventorySlot(Item.getItemByName("Wood") , 10))) , "PoleTurner"
                , Building.BuildingType.weaponBuilding , 1 ,0  , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Iron") , 5))) , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Spear") , 20)))));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 5))) , "AppleOrchard"
                , Building.BuildingType.farms , 1 , 0 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("apple") , 20)))));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 10))) , "DiaryFarmer"
                , Building.BuildingType.farms , 1 , 0 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Cheese") , 20)))));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 15))) , "HopsFarmer"
                , Building.BuildingType.farms , 1 , 0 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Oat") , 20)))));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 5))) , "HunterPost"
                , Building.BuildingType.farms , 1 ,0 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Meat") , 20)))));
        Building.buildings.add(new ResourceBuilding(40 , new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wood") , 15))) , "WheatFarmer"
                , Building.BuildingType.farms , 1 , 0, new ArrayList<>(List.of(new InventorySlot(Item.getItemByName("Wheat") , 10)))));
        Building.buildings.add(new CastleDefence(10 , new ArrayList<>() , "Wall" , Building.BuildingType.CastleBuilding , 0 , 0 , 0));
        Building.buildings.add(new CastleDefence(5 , new ArrayList<>() , "Stairs" , Building.BuildingType.CastleBuilding , 0 , 0 , 0));
    }
    private static void InitializeTroops(){
        Troop.Troops.add(new Troop("ArcherBow" , 5 , new ArrayList<>(List.of(Item.getItemByName("Bow")))  , 3 , 3 , 7 , 5 , 10 , true , Troop.TroopType.Ranged));
        Troop.Troops.add(new Troop("Slaves"  ,5 , new ArrayList<>(List.of(Item.getItemByName("Torch"))) , 1 , 1 , 7 , 0 ,5 ,  true , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("Slingers" , 5 , new ArrayList<>(List.of(Item.getItemByName("Sling"))) , 3 , 1 , 7 , 5 , 5 , true , Troop.TroopType.Ranged));
        Troop.Troops.add(new Troop("Assassins"  , 5 , new ArrayList<>(List.of(Item.getItemByName("Hook"))) , 5 , 5 , 5 , 0 , 10 , true , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("HorseArcher"  , 5 , new ArrayList<>(List.of(Item.getItemByName("Horse") , Item.getItemByName("Bow"))) , 3 , 5 , 7 , 10 , 10 , true , Troop.TroopType.Ranged));
        Troop.Troops.add(new Troop("ArabianSwordsmen" ,10  , new ArrayList<>(List.of(Item.getItemByName("Sword"))) , 7 , 7 , 9 , 0 , 5 , true , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("FireThrowers" ,5 , new ArrayList<>(List.of(Item.getItemByName("Grenade"))) , 7 , 3 , 9 , 10 , 5 ,  true , Troop.TroopType.Ranged));
        Troop.Troops.add(new Troop("Worker" , 5 , new ArrayList<>() , 0 , 0 , 0 , 0 , 5 ,  false , Troop.TroopType.Engineer));
        Troop.Troops.add(new Troop("Archer", 5 , new ArrayList<>(List.of(Item.getItemByName("Bow"))) , 3 , 3 , 7 , 10 ,  5 , false , Troop.TroopType.Ranged));
        Troop.Troops.add(new Troop("Crossbowmen" , 5 , new ArrayList<>(List.of(Item.getItemByName("Bow"))) , 3 , 5 , 3 , 10 , 5 ,  false , Troop.TroopType.Ranged));
        Troop.Troops.add(new Troop("Pikemen"  , 5 , new ArrayList<>(List.of(Item.getItemByName("Spear") , Item.getItemByName("Armor"))) , 5 , 7 , 3 , 0 , 5 ,  false , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("Macemen"  , 5 , new ArrayList<>(List.of(Item.getItemByName("Sword") , Item.getItemByName("Ladder"))) , 7 , 5 , 5 , 0 , 5 ,  false , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("Swordsmen" , 5 , new ArrayList<>(List.of(Item.getItemByName("Sword") , Item.getItemByName("Armor"))) , 9 , 1 , 1 , 0 , 5 , false , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("Knight" , 20  , new ArrayList<>(List.of(Item.getItemByName("Sword") , Item.getItemByName("Armor") , Item.getItemByName("Horse"))) , 9 , 7 , 9 , 0 , 20 ,  false , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("Tunneler" , 5 , new ArrayList<>() , 5 , 1 , 7 , 0 , 10 , false , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("Laddermen"  , 5 , new ArrayList<>(List.of(Item.getItemByName("Ladder"))) , 0 , 1 , 7 , -1 , 10 ,  false , Troop.TroopType.Engineer));
        Troop.Troops.add(new Troop("Engineer"  , 5 , new ArrayList<>(), 0 , 1 , 5 , -1 , 10 , false , Troop.TroopType.Engineer));
        Troop.Troops.add(new Troop("BlackMonk" , 5  , new ArrayList<>(List.of(Item.getItemByName("Cudgel"))) , 5 , 5 , 3 , 2 , 10 , false , Troop.TroopType.Melee));
        Troop.Troops.add(new Troop("Spearmen", 5, new ArrayList<>(List.of(Item.getItemByName("Spear"))), 2, 3, 2, 2, 10, false, Troop.TroopType.Melee));
    }
}
