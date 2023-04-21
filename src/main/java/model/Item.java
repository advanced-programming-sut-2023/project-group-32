package model;

import java.util.ArrayList;
import java.util.Stack;

public class Item {
    public static ArrayList<Item> Items;
    String name;
    int price;
    enum ItemType{Weapon, Material, Food};
    ItemType itemType;

    Item(String name, int price, ItemType itemType){
        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
    public static Item getItemByName(String name){
        for (Item item : Items){
            if(item.getName().equals(name))
                return item;
        }
        return null;
    }
}
