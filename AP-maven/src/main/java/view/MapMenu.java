package view;

import controller.MapMenuController;
import model.Map;

import java.util.regex.Matcher;

public class MapMenu {
    public static int x;
    public static int y;
    public static void run(Map map){
        String command;
        Matcher matcher;
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*back\\s*$")){
                System.out.println("Exited Map Menu!");
                return;
            }
            if(command.matches("^\\s*show\\s+current\\s+menu\\s*$"))
                System.out.println("Map Menu");
            else if((matcher = Menu.getMatcher(command , "^\\s*show\\s+map\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*$")) != null) {
                MapMenuController.showMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")) , map);
                x = Integer.parseInt(matcher.group("x"));
                y = Integer.parseInt(matcher.group("y"));
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*map\\s+(?<content>.+)\\s*$")) != null)
                MapMenuController.moveMap(x , y , matcher.group("content") , map);
            else if((matcher = Menu.getMatcher(command , "^\\s*show\\s+details\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*$")) != null)
                MapMenuController.showDetails(Integer.parseInt(matcher.group("x")) , Integer.parseInt(matcher.group("y")) , map);
            else
                System.out.println("Invalid command!");
        }
    }
    public static void checkBuildings(int x , int y , Map map){
        int flag;
        for(int i = x - 2 ; i <= x + 2 ; i++){
            if(i >= 0 && i < map.mapSize) {
                String COLOUR = checkColour(map, i, y);
                System.out.print(COLOUR);
                if (map.tiles[i][y].building != null)
                    flag = 1;
                else
                    flag = 0;
                System.out.print("\tBuilding:  " + flag + "   ");
                COLOUR = "\u001B[0m";
                System.out.print("\t" + COLOUR + "|");
            }
            else
                System.out.print("\t\t\t\t\t");
        }
    }
    public static void checkTrees(int x , int y , Map map){
        int flag;
        for(int i = x - 2 ; i <= x + 2 ; i++){
            if(i >= 0 && i < map.mapSize) {
                String COLOUR = checkColour(map, i, y);
                System.out.print(COLOUR);
                if (map.tiles[i][y].tree != null)
                    flag = 1;
                else
                    flag = 0;
                System.out.print("\tTrees:     " + flag);
                COLOUR = "\u001B[0m";
                System.out.print("\t" + COLOUR + "|");
            }
            else
                System.out.print("\t\t\t\t\t");
        }
    }
    public static void checkResources(int x , int y , Map map){
        int flag;
        for(int i = x - 2 ; i <= x + 2 ; i++){
            if(i < map.mapSize && i >= 0) {
                String COLOUR = checkColour(map, i, y);
                System.out.print(COLOUR);
                if (map.tiles[i][y].resource != null)
                    flag = 1;
                else
                    flag = 0;
                System.out.print("\tResources: " + flag);
                COLOUR = "\u001B[0m";
                System.out.print("\t" + COLOUR + "|");
            }
            else
                System.out.print("\t\t\t\t\t");
        }
    }
    public static void checkTroops(int x , int y , Map map){
        for(int i = x - 2 ; i <= x + 2 ; i++){
            if(i >= 0 && i < map.mapSize) {
                String COLOUR = checkColour(map, i, y);
                System.out.print(COLOUR);
                System.out.print("\tTroops:    " + map.tiles[i][y].troops.size());
                COLOUR = "\u001B[0m";
                System.out.print("\t" + COLOUR + "|");
            }
            else
                System.out.print("\t\t\t\t\t");
        }
    }
    public static String checkColour(Map map , int x , int y){
        String tileType = map.tiles[x][y].tileType;
        String COLOUR = null;
        switch (tileType) {
            case "Dust", "DustRock", "Beach" -> COLOUR = "\u001B[43m";
            case "Rock", "Stone" -> COLOUR = "\u001B[45m";
            case "Iron" -> COLOUR = "\u001B[41m";
            case "Grass", "Meadow", "MeadowDense" , "Plain" -> COLOUR = "\u001B[42m";
            case "Sea", "BigPond", "SmallPond", "River" , "ShallowWater" -> COLOUR = "\u001B[44m";
            case "Oil" -> COLOUR = "\u001B[90m";
        }
        return COLOUR;
    }
}
