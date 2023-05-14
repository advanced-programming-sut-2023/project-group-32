package view;

import controller.Controller;
import model.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

public class TradeMenu {
    static int id = 1;
    public static ArrayList<String> trades = new ArrayList<>();
    public static void run(ArrayList<User> players , User currentPlayer){
        String command;
        Matcher matcher;
        for (String trade : trades) {
            if(Controller.getPart(trade , "receiver:") != null || Controller.getPart(trade , "sender:") != null)
                System.out.println(trade);
        }
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*show\\s+current\\s+menu\\s*$"))
                System.out.println("Trade Menu");
            else if(command.matches("^\\s*back\\s*$")){
                System.out.println("Exited Trade Menu!");
                return;
            }
            else if(command.matches("^\\s*show\\s+all\\s+players\\s*$")){
                int i = 1;
                for (User player : players) {
                    System.out.println(i + ") " + player.getUsername());
                    i++;
                }
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*trade\\s+-p\\s+(?<player>[\\s\\w]+)\\s+-t\\s+(?<type>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s+-p\\s+(?<price>\\d+)\\s+-m\\s+(?<message>.+)\\s*$")) != null){
                String type = matcher.group("type");
                int amount = Integer.parseInt(matcher.group("amount"));
                int price = Integer.parseInt(matcher.group("price"));
                String message = matcher.group("message").trim();
                String player = matcher.group("player");
                trade(player , type , amount , price , message);
            }
            else if(command.matches("^\\s*trade\\s+list\\s*$")){
                for (String trade : trades) {
                    System.out.println(trade);
                }
            }
            else if((matcher = Menu.getMatcher(command , "^\\s*trade\\s+accept\\s+-i\\s+(?<id>\\d+)\\s*$")) != null){
                String id = (matcher.group("id"));
                for (String trade : trades) {
                    if(Objects.requireNonNull(Controller.getPart(trade, "id:")).equals(id))
                        tradeAccept(trade);
                }
            }
            else
                System.out.println("Invalid command!");
        }
    }
    public static void trade(String user , String type , int amount , int price , String message){
        User receiver = User.getUserByUsername(user);
        if(receiver == null){
            System.out.println("No user with this name found!");
            return;
        }
        trades.add("sender: " + GameMenu.currentPLayer.getUsername() + " receiver: " + receiver.getUsername() + " type: " + type + " amount: " + amount + " price: " + price + " message: " + message + " id: " + id);
        System.out.println("Your request sent!");
        id++;
    }
    public static void tradeAccept(String trade){
        User sender = User.getUserByUsername(Controller.getPart(trade , "sender:"));
        User receiver = User.getUserByUsername(Controller.getPart(trade , "receiver:"));

    }
}
