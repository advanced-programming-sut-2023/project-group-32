package model;

import java.util.ArrayList;

public class Tile {
    public ArrayList<Troop> troops;
    public Building building;
    public Resource resource;
    //Dust, DustRock, Rock, Iron, Stone, Meadow, MeadowDense, Grass
    public String tileType;

    Tile(String tileType){
        troops = new ArrayList<>();
        this.tileType = tileType;
    }
}
