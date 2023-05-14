package view;

import controller.Controller;
import controller.ProfileMenuController;
import model.User;
import view.messages.ProfileMenuMessages;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;

import static view.messages.ProfileMenuMessages.*;

public class ProfileMenu {
    public static String changeProfileRegex = "^\\s*profile\\s+change\\s+(?<content>.+)\\s*$";
    public static String displayRegex = "^\\s*profile\\s+display\\s+(?<content>.*)\\s*$";
    public static void run() throws InterruptedException, IOException {
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
                change(matcher.group("content"));
            }
            else if(command.matches("^\\s*profile\\s+remove\\s+slogan\\s*$")){
                removeSlogan();
            }
            else if((matcher = Menu.getMatcher(command , displayRegex)) != null){
                display(matcher.group("content"));
            }
            else if(command.matches("^\\s*profile\\s+display\\s*$")){
                display(null);
            }
            else
                System.out.println("Invalid command!");
        }
    }
    public static void change(String content) throws IOException, InterruptedException{
        ProfileMenuMessages messages = ProfileMenuController.change(content);
        if(Objects.equals(messages, BAD_USERNAME_FORMAT))
            System.out.println("Incorrect format for username!");
        else if(Objects.equals(messages , SAME_USERNAME)){
            System.out.println("New username cannot be the same as old!");
            System.out.println("You can set your username as: " + User.suggestUsername(Controller.getPart(content , "-u")));
        }
        else if(Objects.equals(messages , DUPLICATE_USERNAME)){
            System.out.println("Username already exists!");
            System.out.println("You can set your username as: " + User.suggestUsername(Controller.getPart(content , "-u")));
        }
        else if(Objects.equals(messages , USERNAME_CHANGED))
            System.out.println("Username changed successfully to: " + Controller.getPart(content , "-u"));
        else if(Objects.equals(messages , NICKNAME_CHANGED))
            System.out.println("Nickname changed successfully to: " + Controller.getPart(content , "-n"));
        else if(Objects.equals(messages , INCORRECT_PASSWORD))
            System.out.println("Current password is incorrect!");
        else if(Objects.equals(messages , WEAK_PASSWORD))
            System.out.println("Your new password is weak!");
        else if(Objects.equals(messages , DUPLICATE_PASSWORD))
            System.out.println("New password can not be the same as old!");
        else if(Objects.equals(messages , PASSWORD_CONFIRM))
            System.out.println("Password changed successfully!");
        else if(Objects.equals(messages , DUPLICATE_EMAIL))
            System.out.println("New email can not be the same as old!");
        else if(Objects.equals(messages , USED_EMAIL))
            System.out.println("Email already exists!");
        else if(Objects.equals(messages , BAD_EMAIL_FORMAT))
            System.out.println("Incorrect format for Email!");
        else if(Objects.equals(messages , EMAIL_CHANGED))
            System.out.println("Email changed successfully to: " + Controller.getPart(content , "-e"));
        else if(Objects.equals(messages , SLOGAN_CHANGED))
            System.out.println("Slogan changed successfully to:\n" + Controller.getPart(content , "-s"));
        else if(Objects.equals(messages , INVALID_COMMAND))
            System.out.println("Invalid command for profile change!");
    }
    public static void removeSlogan() throws IOException, InterruptedException{
        ProfileMenuMessages messages = ProfileMenuController.removeSlogan();
        if(Objects.equals(messages , SLOGAN_REMOVED))
            System.out.println("Slogan removed successfully!");
    }
    public static void display(String content) throws IOException, InterruptedException{
        ProfileMenuMessages messages = ProfileMenuController.display(content);
        if(Objects.equals(messages , DISPLAY_ALL)){
            System.out.println("username : " + Menu.currentUser.getUsername());
            System.out.println("nickname : " + Menu.currentUser.getNickName());
            System.out.println("email : " + Menu.currentUser.getEmail());
            System.out.println("slogan : " + Menu.currentUser.getSlogan());
            System.out.println("high score : " + Menu.currentUser.getHighScore());
            Controller.setRank();
            System.out.println("rank : " + Menu.currentUser.getRank());
        }
        else if(Objects.equals(messages , NO_SLOGAN))
            System.out.println("Slogan is empty!");
        else if(Objects.equals(messages , DISPLAY_HIGHSCORE))
            System.out.println(Menu.currentUser.getHighScore());
        else if(Objects.equals(messages , DISPLAY_RANK))
            System.out.println(Menu.currentUser.getRank());
        else if(Objects.equals(messages , DISPLAY_SLOGAN))
            System.out.println(Menu.currentUser.getSlogan());
        else if(Objects.equals(messages , INVALID_COMMAND))
            System.out.println("Invalid command for display!");
    }
}
