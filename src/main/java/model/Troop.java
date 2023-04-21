package model;

import java.util.ArrayList;

public class Troop implements Cloneable{
    public static ArrayList<Troop> Troops;
    String troopName;
    int price;
    ArrayList<Item> equipment;

    int attackPower, defencePower, speed;

    boolean mercenary;

    int x, y;
    Government owner;

    public Troop(String troopName, int price, ArrayList<Item> equipment, int attackPower, int defencePower, int speed, boolean mercenary) {
        this.troopName = troopName;
        this.price = price;
        this.equipment = equipment;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
        this.speed = speed;
        this.mercenary = mercenary;
        owner = null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Government getOwner() {
        return owner;
    }

    public String getTroopName() {
        return troopName;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<Item> getEquipment() {
        return equipment;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefencePower() {
        return defencePower;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isMercenary() {
        return mercenary;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }
    public static Troop getTroopByName(String troopName){
        for(Troop troop : Troops){
            if(troop.getTroopName().equals(troopName)){
                return troop;
            }
        }
        return null;
    }
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
