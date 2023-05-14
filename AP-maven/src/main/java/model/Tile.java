package model;

import java.util.ArrayList;

public class Tile {
    public int x , y;
    public int pathSetter;
    public ArrayList<Troop> troops;
    public Building building;
    public Resource resource;
    public Tree tree;
    public Rock rock;
    //Dust, DustRock, Rock, Iron, Stone, Meadow, MeadowDense, Grass , River , SmallPond , BigPond , Sea , Beach , ShallowWater , Plain , Oil
    public String tileType;
    public boolean reachable;

    Tile(String tileType){
        troops = new ArrayList<>();
        this.tileType = tileType;
        resource = null;
        tree = null;
        rock = null;
        building = null;
        pathSetter = 0;
        reachable = true;
    }
}
