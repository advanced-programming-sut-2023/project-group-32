package model;

public class Tree {
    int x , y;
    public String treeType; // {DesertShrub , CherryPalm , OliveTree , CoconutPalm , DatePalm}
    public Tree(int x , int y , String treeType){
        this.x = x;
        this.y = y;
        this.treeType = treeType;
    }
}
