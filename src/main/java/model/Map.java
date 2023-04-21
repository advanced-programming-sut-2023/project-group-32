package model;

import view.GameMenu;

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
}
