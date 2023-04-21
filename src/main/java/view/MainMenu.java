package view;


import model.Map;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenu {
    public static String startGameRegex = "\\s*start\\s+a\\s+game\\s+with\\s+users:\\s*(?<users>.+)\\s+turns\\s+count:\\s+(?<turnsNumber>\\d+)\\s*$";
    public static void run() throws InterruptedException, IOException {
        String command;
        Matcher matcher;
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*user\\s+logout\\s*$")) {
                System.out.println("User logged out successfully!");
                Menu.currentUser = null;
                FileWriter fileWriter = new FileWriter("stayLoggedIn.txt");
                fileWriter.write("");
                fileWriter.close();
                return;
            }
            else if(command.matches("^\\s*profile\\s+menu\\s*$")) {
                System.out.println("Entered Profile Menu!");
                ProfileMenu.run();
            }
            /*else if(command.matches("^\\s*map\\s+menu\\s*$")){
                System.out.println("Entered Map Menu!");
                MapMenu.run(map);
            }*/
            else if(command.matches("^\\s*show\\s+current\\s+menu\\s*$"))
                System.out.println("Main Menu");
            else if(command.matches("^\\s*start\\s+a\\s+new\\s+game\\s*$")){
                GameMenu.setGame();
            }
            else
                System.out.println("Invalid command!");
        }
    }
}
