package controller;

import model.User;
import view.Menu;
import view.messages.ProfileMenuMessages;

import java.io.IOException;

import static view.messages.ProfileMenuMessages.*;

public class ProfileMenuController {
    public static ProfileMenuMessages change(String content) throws IOException {
        String username = Controller.getPart(content , "-u");
        String nickName = Controller.getPart(content , "-n");
        String oldPassword = Controller.getPart(content , "-op");
        String newPassword = Controller.getPart(content , "-np");
        String email = Controller.getPart(content , "-e");
        String slogan = Controller.getPart(content , "-s");
        if(username != null){
            if(!username.matches("[\\w \t]+")){
                return BAD_USERNAME_FORMAT;
            }
            else if(Menu.currentUser.getUsername().equals(username)){
                return SAME_USERNAME;
            }
            else if(User.getUserByUsername(username) != null){
                return DUPLICATE_USERNAME;
            }
            else {
                Menu.currentUser.setUsername(username);
                return USERNAME_CHANGED;
            }
        }
        if(nickName != null){
            Menu.currentUser.setNickName(nickName);
            return NICKNAME_CHANGED;
        }
        if(oldPassword != null && newPassword != null){
            oldPassword = Controller.hashMaker(oldPassword);
            if(!Menu.currentUser.getPassword().equals(oldPassword)){
                return INCORRECT_PASSWORD;
            }
            if(!newPassword.equals("random") && (newPassword.length() < 6 || !newPassword.matches(".*[a-z].*") || !newPassword.matches(".*[A-Z].*") ||
                    !newPassword.matches(".*[0-9].*") || !newPassword.matches(".*[^a-zA-Z0-9].*"))){
                return WEAK_PASSWORD;
            }
            else if(Controller.hashMaker(newPassword).equals(oldPassword)){
                return DUPLICATE_PASSWORD;
            }
            else {
                Menu.captcha();
                Menu.currentUser.setPassword(Controller.hashMaker(newPassword));
                return PASSWORD_CONFIRM;
            }
        }
        if(email != null){
            if(Menu.currentUser.getEmail().equals(email)){
                return DUPLICATE_EMAIL;
            }
            else if(User.getUserByEmail(email) != null){
                return USED_EMAIL;
            }
            else if(!email.matches("\\S+@\\S+\\.\\S+")){
                return BAD_EMAIL_FORMAT;
            }
            else {
                Menu.currentUser.setEmail(email);
                return EMAIL_CHANGED;
            }
        }
        else if(slogan != null){
            Menu.currentUser.setSlogan(slogan);
            return SLOGAN_CHANGED;
        }
        return INVALID_COMMAND;
    }
    public static ProfileMenuMessages removeSlogan() throws InterruptedException, IOException{
        Menu.currentUser.setSlogan(null);
        return SLOGAN_REMOVED;
    }

    public static ProfileMenuMessages display(String content) throws InterruptedException, IOException{
        if(content == null){
            return DISPLAY_ALL;
        }
        else if(content.equals("slogan")){
            if(Menu.currentUser.getSlogan() == null){
                return NO_SLOGAN;
            }
            return DISPLAY_SLOGAN;
        }
        else if(content.equals("rank"))
            return DISPLAY_RANK;
        else if(content.equals("highscore"))
            return DISPLAY_HIGHSCORE;
        else{
            return INVALID_COMMAND;
        }
    }
}

