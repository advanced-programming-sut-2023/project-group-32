package view;

import controller.MapMenuController;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.zip.CheckedOutputStream;

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
                MapMenuController.showDetails(Integer.parseInt(matcher.group("x")) , Integer.parseInt(matcher.group("y")));
            else
                System.out.println("Invalid command!");
        }
    }
    //Dust, DustRock, Rock, Iron, Stone, Meadow, MeadowDense, Grass
    /*
    dust: zamin mamuli
    dustRock = zamin ba sangriz
    rock = tackte sang
    stone = sang
    ahan = iron
    chaman = grass
    meadow = alafzar
    meadowdense = alafzar por tarakom
     */
    public static void showTile(Tile tile){
        Building building = tile.building;
        ArrayList<Troop> troops = tile.troops;
        Resource resource = tile.resource;
        String tileType = tile.tileType;
        String COLOUR;
        switch (tileType) {
            case "Dust" -> COLOUR = "\u001B[0m";
            case "DustRock" -> COLOUR = "\u001B[33m";
            case "Rock" -> COLOUR = "\u001B[43m";
            case "Stone" -> COLOUR = "\u001B[41m";
            case "Iron" -> COLOUR = "\u001B[31m";
            case "Grass" -> COLOUR = "\u001B[46m";
            case "Meadow" -> COLOUR = "\u001B[32m";
            case "MeadowDense" -> COLOUR = "\u001B[42m";
        }

    }
}
