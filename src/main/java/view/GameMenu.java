package view;

import controller.GameMenuController;
import model.Government;
import model.Map;
import model.User;
import java.util.ArrayList;

public class GameMenu {
    public static void setGame(){
        String username;
        ArrayList<User> players = new ArrayList<>();
        players.add(Menu.currentUser);
        System.out.println("Type users you want to play with one by one:");
        while (true){
            username = Menu.getScanner().nextLine();
            if(username.equals("cancel")){
                System.out.println("Entered Main Menu!");
                return;
            }
            else if(username.equals("finish")){
                System.out.println("Type the turns of the game: ");
                int turns = Menu.getScanner().nextInt();
                System.out.println("Type the length of the game map: (200 or 400)");
                int size = Menu.getScanner().nextInt();
                Map map = new Map(size);
                GameMenu gameMenu = new GameMenu(map , turns , players);
                gameMenu.run();
            }
            if(username.equals(Menu.currentUser.getUsername()))
                System.out.println("You don't need to write your name!");
            else if(User.getUserByUsername(username) == null)
                System.out.println("Username " + username + " doesn't exist!");
            else {
                players.add(User.getUserByUsername(username));
                System.out.println(username + " added to the list of players!");
            }
            System.out.println("Type \"finish\" if you want to go to the next step, type \"cancel\" if you want to go back to Main Menu.");
        }
    }
    int turnsCount;

    static ArrayList<User> players;
    static User currentPLayer;
    static User host;
    static Map map;
    public GameMenu(Map map , int turnsCount , ArrayList<User> players){
        this.turnsCount = turnsCount;
        GameMenu.players = players;
        GameMenu.host = Menu.currentUser;
        GameMenu.map = map;
    }
    public void run(){
        String command;
        System.out.println("The game started!");
        command = Menu.getScanner().nextLine();
        currentPLayer = host;
        GameMenuController.currentGovernment = currentPLayer.getGovernment();
        int counter = 0;
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*show\\s+popularity\\s+factors\\s*$"))
                GameMenuController.showPopularityFactors();
            else if(command.matches("^\\s*show\\s+popularity\\s*$"))
                GameMenuController.showPopularity();
            else if(command.matches("^\\s*list\\s+of\\s+players\\s*$")){
                int i = 1;
                for (User player : players) {
                    System.out.println(i + ") " + player.getUsername());
                    i++;
                }
            }
            else if(command.matches("^\\s*show\\s+current\\s+player\\s*$"))
                System.out.println("current player is: " + currentPLayer.getUsername());
            else if(command.matches("^\\s*show\\s+turns\\s+left\\s*$"))
                System.out.println(turnsCount - counter);
            else if(command.matches("^\\s*map\\s+menu\\s*$")){
                System.out.println("Entered Map Menu!");
                MapMenu.run(map);
            }
            else if(command.matches("^\\s*shop\\s+menu\\s*$")){
                System.out.println("Entered Shop Menu!");
                ShopMenu.run();
            }
            else
                System.out.println("Invalid command!");
        }
    }
}
