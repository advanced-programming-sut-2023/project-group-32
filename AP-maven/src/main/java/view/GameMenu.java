package view;

import controller.GameMenuController;
import controller.MapMenuController;
import model.Map;
import model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
public class GameMenu {
    public ArrayList<String> colours = new ArrayList<>();
    {
        colours.add("Red");
        colours.add("Green");
        colours.add("Purple");
        colours.add("White");
        colours.add("Blue");
        colours.add("Yellow");
        colours.add("Orange");
        colours.add("Cyan");
    }
    public static void setGame() throws CloneNotSupportedException {
        String username;
        ArrayList<User> players = new ArrayList<>();
        players.add(Menu.currentUser);
        System.out.println("Type users you want to play with one by one:");
        while (true){
            username = Menu.getScanner().nextLine();
            if(username.equals("-")){
                System.out.println("Entered Main Menu!");
                return;
            }
            else if(username.equals("+")){
                System.out.println("Type the turns of the game: ");
                String mapTurns = Menu.getScanner().nextLine();
                if(mapTurns.matches(".*\\D+.*")){
                    System.out.println("Invalid turns!");
                    return;
                }
                int turns = Integer.parseInt(mapTurns);
                if(turns <= 0){
                    System.out.println("Invalid turns!");
                    return;
                }
                System.out.println("Type the length of the game map: (200 or 400)");
                String mapSize = Menu.getScanner().nextLine();
                if(mapSize.matches(".*\\D+.*")){
                    System.out.println("Invalid size!");
                    return;
                }
                int size = Integer.parseInt(mapSize);
                if(size != 200 && size != 400){
                    System.out.println("Invalid size!");
                    return;
                }
                Map map = new Map(size);
                GameMenu gameMenu = new GameMenu(map , turns , players);
                gameMenu.run();
                return;
            }
            else if(username.equals(Menu.currentUser.getUsername()))
                System.out.println("You don't need to write your name!");
            else if(User.getUserByUsername(username) == null)
                System.out.println("Username " + username + " doesn't exist!");
            else if(getPlayerByName(username , players) != null)
                System.out.println("This player is already in the players list!");
            else {
                players.add(User.getUserByUsername(username));
                System.out.println(username + " added to the list of players!");
            }
            System.out.println("Type \"+\" if you want to go to the next step, type \"-\" if you want to go back to Main Menu.");
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
        GameMenuController.gameMap = map;
    }
    public void run() throws CloneNotSupportedException {
        String validGame = Menu.setColours(players , colours);
        if(validGame.equals("FAIL"))
            return;
        validGame = Menu.setHeadquarters(players , map);
        if(validGame.equals("FAIL"))
            return;
        String command;
        Matcher matcher;
        System.out.println("The game started!");
        currentPLayer = host;
        GameMenuController.currentGovernment = currentPLayer.getGovernment();
        int counter = 0;
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*show\\s+current\\s+player\\s*$"))
                System.out.println(currentPLayer.getUsername() + " is now playing!");
            else if(command.matches("^\\s*show\\s+current\\s+menu\\s*$"))
                System.out.println("Game Menu");
            else if(command.matches("^\\s*show\\s+turns\\s+left\\s*$"))
                System.out.println((turnsCount - counter) + " turns left!");
            else if(command.matches("^\\s*show\\s+my\\s+colour\\s*"))
                System.out.println(currentPLayer.getGovernment().colour);
            else if(command.matches("^\\s*next\\s+turn\\s*$")){
                int i = players.indexOf(currentPLayer);
                int j = (i+1)%players.size();
                currentPLayer = players.get(j);
                GameMenuController.currentGovernment = currentPLayer.getGovernment();
                GameMenuController.nextTurn();
                System.out.println("User " + currentPLayer.getUsername() + " is now playing!");
                if(j == 0)
                    counter++;
                if(counter == turnsCount){
                    System.out.println("The game has ended...!");
                    return;
                }
            }
            else if(command.matches("^\\s*show\\s+all\\s+players\\s*$")){
                int n = 1;
                for (User player : players) {
                    System.out.println(n + ") " + player.getUsername());
                    n++;
                }
            }
            else if(command.matches("^\\s*map\\s+menu\\s*$")){
                System.out.println("Entered Map Menu!");
                MapMenu.run(map);
            }
            else if(command.matches("^\\s*trade\\s+menu\\s*$")) {
                System.out.println("Entered Trade Menu!");
                TradeMenu.run(players , currentPLayer);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*set\\s+texture\\s+(?<content>.+)\\s*$")) != null){
                String content = matcher.group("content");
                MapMenuController.setTexture(content , map);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*clear\\s+-x\\s+(?<x>\\d+)\\d+-y\\s+(?<y>\\d+)\\d*$")) != null) {
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                MapMenuController.clear(x , y);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*drop\\s+rock\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-d\\s+(?<direction>[wsne])\\s*")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                String direction = matcher.group("direction");
                GameMenuController.dropRock(x , y , direction);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*drop\\s+tree\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-t\\s+(?<type>\\S+)\\s*$")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                String type = matcher.group("type");
                GameMenuController.dropTree(x , y , type);
            }
            else if(command.matches("^\\s*show\\s+popularity\\s+factors\\s*$"))
                GameMenuController.showPopularityFactors();
            else if(command.matches("^\\s*show\\s+popularity\\s*$"))
                GameMenuController.showPopularity();
            else if((matcher = Menu.getMatcher(command , "^\\s*food\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*")) != null)
                currentPLayer.getGovernment().setFoodRate(Integer.parseInt(matcher.group("rate")));
            else if((matcher = Menu.getMatcher(command , "^\\s*fear\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*")) != null)
                currentPLayer.getGovernment().setFearRate(Integer.parseInt(matcher.group("rate")));
            else if((matcher = Menu.getMatcher(command , "^\\s*tax\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*")) != null)
                currentPLayer.getGovernment().setTaxRate(Integer.parseInt(matcher.group("rate")));
            else if(command.matches("^\\s*show\\s+food\\s+list\\s*$"))
                GameMenuController.showFoodList();
            else if(command.matches("^\\s*food\\s+rate\\s+show\\s*$"))
                System.out.println(currentPLayer.getGovernment().getFoodRate());
            else if(command.matches("^\\s*tax\\s+rate\\s+show\\s*$"))
                System.out.println(currentPLayer.getGovernment().getTaxRate());
            else if(command.matches("^\\s*fear\\s+rate\\s+show\\s*"))
                System.out.println(currentPLayer.getGovernment().getFearRate());
            else if((matcher = Menu.getMatcher(command , "^\\s*drop\\s+building\\s+-x\\s+(?<x>(-)?\\d+)\\s+-y\\s+(?<y>(-)?\\d+)\\s+-t\\s+(?<type>.+)\\s*$")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                String type = matcher.group("type").trim();
                GameMenuController.dropBuilding(x , y , type);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*select\\s+building\\s+-x\\s+(?<x>(-)?\\d+)\\s+-y\\s+(?<y>(-)?\\d+)\\s*$")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                GameMenuController.selectBuilding(x , y);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*create\\s+unit\\s+-t\\s+(?<type>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*$")) != null){
                String type = matcher.group("type");
                int count = Integer.parseInt(matcher.group("count"));
                GameMenuController.CreateUnit(type , count);
            }
            else if(command.matches("^\\s*show\\s+selected\\s+building\\s*$")) {
                if(GameMenuController.selectedBuilding == null)
                    System.out.println("No building is selected!");
                else
                    System.out.println("Name: " + GameMenuController.selectedBuilding.getName() + " | Owner: " + GameMenuController.selectedBuilding.getOwner().user.getUsername());
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*select\\s+unit\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*$")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                GameMenuController.selectUnit(x , y);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*move\\s+unit\\s+to\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*$")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                GameMenuController.moveUnit(x , y);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*patrol\\s+unit\\s+-x1\\s+(?<x1>\\d+)\\s+-y1\\s+(?<y1>\\d+)\\s+-x2\\s+(?<x2>\\d+)\\s+-y2\\s+(?<y2>\\d+)\\s*$")) != null){
                int x1 = Integer.parseInt(matcher.group("x1"));
                int x2 = Integer.parseInt(matcher.group("x2"));
                int y1 = Integer.parseInt(matcher.group("y1"));
                int y2 = Integer.parseInt(matcher.group("y2"));
                GameMenuController.patrolUnit(x1 , y1 , x2 , y2);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*set\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-s\\s+(?<state>\\S+)\\s*$")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                String state = matcher.group("state");
                GameMenuController.set(x , y , state);
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*attack\\s+-e\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$")) != null){
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                GameMenuController.attack(x , y);
            }
            /*else if((matcher = Menu.getMatcher(command , "^\\s*attack\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*$")) != null){
                int x = Integer.parseInt()
            }*/
            else if(command.matches("^\\s*show\\s+price\\s+list\\s*$")) {
                if(GameMenuController.selectedBuilding.getName().equals("Market"))
                    ShowPriceList();
                else
                    System.out.println("You didn't select the market!");
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*buy\\s+-i\\s+(?<name>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*$")) != null){
                if(GameMenuController.selectedBuilding.getName().equals("Market")) {
                    String name = matcher.group("name");
                    int amount = Integer.parseInt(matcher.group("amount"));
                    GameMenuController.buy(name, amount);
                }
                else
                    System.out.println("You didn't select the market!");
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*sell\\s-i\\s+(?<name>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*$")) != null){
                if(GameMenuController.selectedBuilding.getName().equals("Market")) {
                    String name = matcher.group("name");
                    int amount = Integer.parseInt(matcher.group("amount"));
                    GameMenuController.sell(name, amount);
                }
                else
                    System.out.println("You didn't select the market!");
            }
            else
                System.out.println("Invalid command!");
        }
    }
    public static User getPlayerByName(String name , ArrayList<User> players){
        for (User player : players) {
            if(player.getUsername().equals(name))
                return player;
        }
        return null;
    }
    public static void ShowPriceList(){

    }
}
