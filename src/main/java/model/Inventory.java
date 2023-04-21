package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Inventory {

    public ArrayList<InventorySlot> Items;
    int capacity;

    Inventory(int capacity){
        this.capacity = capacity;
        Items = new ArrayList<>();
    }

    public void addItem(Item item, int amount){
    }
    public void addItem(String itemName, int amount){
    }
    public void removeItems(ArrayList<Item> items){
    }
    public void buyItem(String itemName, int amount){

    }
    public void buyItem(Item item, int amount){

    }
    public int getAmount(Item item){
        for(InventorySlot inventorySlot : Items){
            if(Objects.equals(inventorySlot.item, item)){
                return inventorySlot.getAmount();
            }
        }
        return 0;
    }
    public int getAmount(String itemName){
        for(InventorySlot inventorySlot : Items){
            if(Objects.equals(inventorySlot.item.getName(), itemName)){
                return inventorySlot.getAmount();
            }
        }
        return 0;
    }
    public boolean hasItems(ArrayList<Item> items){
        for(Item item : items){
            if(getAmount(item) == 0)
                return false;
        }
        return true;
    }
}
