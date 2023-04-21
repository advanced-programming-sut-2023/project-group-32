package view;

import controller.ProfileMenuController;
import controller.SignUpMenuController;

import java.util.regex.Matcher;

public class ProfileMenu {
    public static String changeProfileRegex = "^\\s*profile\\s+change\\s+(?<content>.+)\\s*$";
    public static String displayRegex = "^\\s*profile\\s+display\\s+(?<content>.*)\\s*$";
    public static void run() throws InterruptedException {
        String command;
        Matcher matcher;
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*back\\s*$")) {
                System.out.println("Entered Main Menu!");
                return;
            }
            else if(command.matches("^\\s*show\\s+current\\s+menu$"))
                System.out.println("Profile Menu");
            else if((matcher = Menu.getMatcher(command , changeProfileRegex)) != null){
                ProfileMenuController.change(matcher.group("content"));
            }
            else if(command.matches("^\\s*profile\\s+remove\\s+slogan\\s*$")){
                ProfileMenuController.removeSlogan();
            }
            else if((matcher = Menu.getMatcher(command , displayRegex)) != null){
                ProfileMenuController.display(matcher.group("content"));
            }
            else if(command.matches("^\\s*profile\\s+display\\s*$")){
                ProfileMenuController.display(null);
            }
            else
                System.out.println("Invalid command!");
        }
    }
}
