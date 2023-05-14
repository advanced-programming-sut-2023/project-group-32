package controller;

import model.Map;
import model.Tile;
import model.Troop;
import view.MapMenu;

import static java.util.Objects.requireNonNull;

public class MapMenuController {
    public static void showMap(int x , int y , Map map){
        System.out.println("-----------------------------------------------------------------------------------------------------");
        for(int i = y + 2 ; i >= y - 2 ; i--){
            if(i >= 0 && i < map.mapSize) {
                MapMenu.checkBuildings(x , i , map);
                System.out.println();
                MapMenu.checkTrees(x , i , map);
                System.out.println();
                MapMenu.checkResources(x , i , map);
                System.out.println();
                MapMenu.checkTroops(x , i , map);
                System.out.println();
                System.out.println("-----------------------------------------------------------------------------------------------------");
            }
            else {
                System.out.println("\n\n\n");
                System.out.println("-------------------------------------------------------------------------------------------------");

            }
        }
    }
    public static void showDetails(int x , int y , Map map){
        Tile tile = map.tiles[x][y];
        System.out.println("TileType: " + tile.tileType);
        if(tile.resource != null)
            System.out.println("Resources: " + tile.resource.type + "\tamount: " + tile.resource.amount);
        if(tile.tree != null)
            System.out.println("Trees: " + tile.tree.treeType);
        if(tile.rock != null)
            System.out.println("Rocks: " + tile.rock.direction);
        if(tile.troops.size() != 0) {
            System.out.println("Troops: ");
            for (Troop troop : tile.troops) {
                System.out.println(troop.getTroopName());
            }
        }
        if(tile.building != null)
            System.out.println("Building: " + tile.building.getName() + "| Owner: " + tile.building.getOwner().user.getUsername());
    }
    public static void setTexture(String content , Map map){
        String singleX = Controller.getPart(content , "-x");
        String singleY = Controller.getPart(content , "-y");
        String doubleX1 = Controller.getPart(content , "-x1");
        String doubleX2 = Controller.getPart(content , "-x2");
        String doubleY1 = Controller.getPart(content , "-y1");
        String doubleY2 = Controller.getPart(content , "-y2");
        String type = Controller.getPart(content , "-t");
        if(singleX != null && singleY != null && type != null){
            if(singleX.matches(".*\\D.*") || singleY.matches(".*\\D.*")){
                System.out.println("Invalid coordinates!");
                return;
            }
            int x = Integer.parseInt(singleX);
            int y = Integer.parseInt(singleY);
            if(x > map.mapSize || y > map.mapSize || x < 0 || y < 0){
                System.out.println("Invalid coordinates!");
                return;
            }
            //Dust, DustRock, Rock, Iron, Stone, Meadow, MeadowDense, Grass , River , SmallPond , BigPond , Sea , Beach , ShallowWater , Plain , Oil
            if (checkTileType(type)) return;
            if(map.tiles[x][y].building != null){
                System.out.println("The tile is not clear!");
                return;
            }
            map.tiles[x][y].tileType = type;
            System.out.println("The tile changes successfully!");
        }
        else if(doubleX1 != null && doubleX2 != null && doubleY1 != null && doubleY2 != null && type != null){
            if(doubleX1.matches(".*\\D.*") || doubleX2.matches(".*\\D.*") || doubleY1.matches(".*\\D.*") || doubleY2.matches(".*\\D.*")){
                System.out.println("Invalid coordinates!");
                return;
            }
            int x1 = Integer.parseInt(doubleX1);
            int x2 = Integer.parseInt(doubleX2);
            int y1 = Integer.parseInt(doubleY1);
            int y2 = Integer.parseInt(doubleY2);
            if(x1 > map.mapSize || y1 > map.mapSize || x2 > map.mapSize || y2 > map.mapSize){
                System.out.println("Invalid coordinates!");
                return;
            }
            if(x1 > x2 || y1 > y2){
                System.out.println("Invalid coordinates!");
                return;
            }
            //Dust, DustRock, Rock, Iron, Stone, Meadow, MeadowDense, Grass , ShallowWater , Plain , Oil
            if (checkTileType(type)) return;
            for(int i = x1 ; i <= x2 ; i++){
                for(int j = y1 ; j <= y2 ; j++){
                    if(map.tiles[i][j].building != null){
                        System.out.println("Tiles are not clear!");
                        return;
                    }
                }
            }
            for(int i=x1 ; i<=x2 ; i++){
                for(int j=y1 ; j<=y2 ; j++){
                    map.tiles[i][j].tileType = type;
                }
            }
            System.out.println("The types changed successfully!");
        }
        else
            System.out.println("Invalid command!");
    }

    private static boolean checkTileType(String type) {
        if(!type.equals("Dust") && !type.equals("DustRock") && !type.equals("Rock") && !type.equals("Iron") && !type.equals("Stone") && !type.equals("Meadow") && !type.equals("MeadowDense") && !type.equals("Grass") && !type.equals("River") && !type.equals("SmallPond") && !type.equals("BogPond") && !type.equals("Sea") && !type.equals("Beach") && !type.equals("Oil") && !type.equals("Plain") && !type.equals("ShallowWater")){
            System.out.println("Invalid Type!");
            return true;
        }
        return false;
    }

    public static void clear(int x , int y){
        Tile tile = GameMenuController.gameMap.tiles[x][y];
        tile.tileType = "Dust";
        tile.building = null;
        tile.troops = null;
        tile.tree = null;
        tile.rock = null;
    }
    public static void moveMap(int x , int y , String content , Map map){
        String[] directions = content.split("\\s+");
        for (String direction : directions) {
            switch (direction) {
                case "up" -> y += 1;
                case "down" -> y -= 1;
                case "left" -> x -= 1;
                case "right" -> x += 1;
                default -> {
                    System.out.println("Invalid direction!");
                    return;
                }
            }
        }
        if(y >= 200 || x >= 200 || x < 0 || y < 0){
            System.out.println("Invalid move!");
            return;
        }
        MapMenu.x = x;
        MapMenu.y = y;
        showMap(x , y , map);
    }
}
