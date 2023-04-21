package controller;

import model.Map;
import model.Tile;
import view.MapMenu;
import view.Menu;

public class MapMenuController {
    public static void showMap(int x , int y , Map map){
        for(int i = y + 2 ; i >= y - 2 ; i--){
            if(i >= 0 && i < 200) {
                for (int j = x - 2; j <= x + 2; j++) {
                    if(j >= 0 && j < 199)
                        MapMenu.showTile(map.tiles[j][i]);
                }
            }
        }
    }
    public static void showDetails(int x , int y){

    }
    public void setTexture(String content){
        String singleX = Controller.getPart(content , "-x");
        String singleY = Controller.getPart(content , "-y");
        String doubleX1 = Controller.getPart(content , "-x1");
        String doubleX2 = Controller.getPart(content , "-x2");
        String doubleY1 = Controller.getPart(content , "-y1");
        String doubleY2 = Controller.getPart(content , "-y2");
        String type = Controller.getPart(content , "-t");
        if(singleX != null && singleY != null && type != null){
            if(singleX.matches(".*[^\\d].*") || singleY.matches(".*[^\\d].*")){
                System.out.println("Invalid coordinates!");
                return;
            }
            int x = Integer.parseInt(singleX);
            int y = Integer.parseInt(singleY);
            if(x > GameMenuController.gameMap.mapSize || y > GameMenuController.gameMap.mapSize){
                System.out.println("Invalid coordinates!");
                return;
            }
            //Dust, DustRock, Rock, Iron, Stone, Meadow, MeadowDense, Grass
            if(!type.equals("Dust") && !type.equals("DustRock") && !type.equals("Rock") && !type.equals("Iron") && !type.equals("Stone") && !type.equals("Meadow") && !type.equals("MeadowDense") && !type.equals("Grass")){
                System.out.println("Invalid Type!");
                return;
            }
            GameMenuController.gameMap.tiles[x][y].tileType = type;
            return;
        }
        else if(doubleX1 != null && doubleX2 != null && doubleY1 != null && doubleY2 != null && type != null){
            if(doubleX1.matches(".*[^\\d].*") || doubleX2.matches(".*[^\\d].*") || doubleY1.matches(".*[^\\d].*") || doubleY2.matches(".*[^\\d].*")){
                System.out.println("Invalid coordinates!");
                return;
            }
            int x1 = Integer.parseInt(doubleX1);
            int x2 = Integer.parseInt(doubleX2);
            int y1 = Integer.parseInt(doubleY1);
            int y2 = Integer.parseInt(doubleY2);
            if(x1 > GameMenuController.gameMap.mapSize || y1 > GameMenuController.gameMap.mapSize || x2 > GameMenuController.gameMap.mapSize || y2 > GameMenuController.gameMap.mapSize){
                System.out.println("Invalid coordinates!");
                return;
            }
            if(x1 >= x2 || y1 >= y2){
                System.out.println("Invalid coordinates!");
                return;
            }
            //Dust, DustRock, Rock, Iron, Stone, Meadow, MeadowDense, Grass
            if(!type.equals("Dust") && !type.equals("DustRock") && !type.equals("Rock") && !type.equals("Iron") && !type.equals("Stone") && !type.equals("Meadow") && !type.equals("MeadowDense") && !type.equals("Grass")){
                System.out.println("Invalid Type!");
                return;
            }
            for(int i=x1 ; i<=x2 ; i++){
                for(int j=y1 ; j<=y2 ; j++){
                    GameMenuController.gameMap.tiles[i][j].tileType = type;
                }
            }
        }
        else{
            System.out.println("Invalid command!");
            return;
        }
    }
    public void clear(int x , int y){

    }
    public void dropRock(int x , int y , String direction){

    }
    public void dropTree(int x , int y , String type){

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
