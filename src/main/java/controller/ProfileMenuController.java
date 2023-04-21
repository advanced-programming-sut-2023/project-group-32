package controller;

import model.User;
import view.Menu;

public class ProfileMenuController {
    public static void change(String content) throws InterruptedException {
        String username = Controller.getPart(content , "-u");
        String nickName = Controller.getPart(content , "-n");
        String oldPassword = Controller.getPart(content , "-op");
        String newPassword = Controller.getPart(content , "-np");
        String email = Controller.getPart(content , "-e");
        String slogan = Controller.getPart(content , "-s");
        if(username != null){
            if(!username.matches("[\\w \t]+")){
                System.out.println("Incorrect format for username!");
            }
            else if(Menu.currentUser.getUsername().equals(username)){
                System.out.println("New username cannot be the same as old!");
                System.out.println("You can set your username as: " + User.suggestUsername(username));
            }
            else if(User.getUserByUsername(username) != null){
                System.out.println("Username already exists!");
                System.out.println("You can set your username as: " + User.suggestUsername(username));
            }
            else {
                Menu.currentUser.setUsername(username);
                System.out.println("Username changed successfully to: " + username);
            }
        }
        if(nickName != null){
            Menu.currentUser.setNickName(nickName);
            System.out.println("Nickname changed successfully to: " + nickName);
        }
        if(oldPassword != null && newPassword != null){
            oldPassword = Controller.hashMaker(oldPassword);
            if(!Menu.currentUser.getPassword().equals(oldPassword)){
                System.out.println("Current password is incorrect!");
            }
            else if(!newPassword.equals("random") && (newPassword.length() < 6 || !newPassword.matches(".*[a-z].*") || !newPassword.matches(".*[A-Z].*") ||
                    !newPassword.matches(".*[0-9].*") || !newPassword.matches(".*[^a-zA-Z0-9].*"))){
                System.out.println("Your new password is weak!");
            }
            else if(Controller.hashMaker(newPassword).equals(oldPassword)){
                System.out.println("New password cannot be the same as old!");
            }
            else {
                System.out.println("Please enter your new password again:");
                newPassword = Controller.hashMaker(newPassword);
                if(Menu.checkPassword(newPassword, "change", null))
                    return;
                else {
                    Menu.currentUser.setPassword(newPassword);
                    System.out.println("password changed successfully to: " + Controller.getPart(content , "-np"));
                }
            }
        }
        if(email != null){
            if(Menu.currentUser.getEmail().equals(email)){
                System.out.println("New email cannot be the same as old!");
            }
            else if(User.getUserByEmail(email) != null){
                System.out.println("Email already exists!");
            }
            else if(!email.matches("\\S+@\\S+\\.\\S+")){
                System.out.println("Incorrect format for Email!");
            }
            else {
                Menu.currentUser.setEmail(email);
                System.out.println("Email changed successfully to: " + email);
            }
        }
        else if(slogan != null){
            Menu.currentUser.setSlogan(slogan);
            System.out.println("Slogan changed successfully to:\n" + slogan);
        }
        if(oldPassword != null && newPassword == null || oldPassword == null && newPassword != null || username == null && nickName == null && oldPassword == null && email == null && slogan == null){
            System.out.println("Invalid command for profile change!");
        }
    }
    public static void removeSlogan(){
        Menu.currentUser.setSlogan(null);
        System.out.println("Slogan removed successfully!");
    }

    public static void display(String content){
        if(content == null){
            System.out.println("username : " + Menu.currentUser.getUsername());
            System.out.println("nickname : " + Menu.currentUser.getNickName());
            System.out.println("email : " + Menu.currentUser.getEmail());
            System.out.println("slogan : " + Menu.currentUser.getSlogan());
            System.out.println("high score : " + Menu.currentUser.getHighScore());
            Controller.setRank();
            System.out.println("rank : " + Menu.currentUser.getRank());
        }
        else if(content.equals("slogan")){
            if(Menu.currentUser.getSlogan() == null){
                System.out.println("Slogan is empty!");
                return;
            }
            System.out.println(Menu.currentUser.getSlogan());
        }
        else{
            System.out.println("Invalid command for display!");
        }
    }
}
