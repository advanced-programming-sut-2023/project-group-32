package model;

import java.util.ArrayList;

public class Map {
    public int mapSize;
    public Tile[][] tiles;
    public Map(int size){
        this.mapSize = size;
        this.tiles = new Tile[mapSize][mapSize];
        for(int i = 0 ; i < mapSize ; i++){
            for (int j = 0 ; j < mapSize ; j++){
                Tile tile = new Tile("Dust");
                this.tiles[i][j] = tile;
            }
        }
    }
    public static float Distance(int x1, int y1, int x2, int y2){
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    ArrayList<Troop> allTroops(){
        ArrayList<Troop> temp = new ArrayList<>();

        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                temp.addAll(tiles[i][j].troops);
            }
        }
        return temp;
    }
}
