package controller;

import model.User;
import view.Menu;
import view.messages.SignUpMenuMessages;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.messages.SignUpMenuMessages.*;

public class SignUpMenuController {

    public static SignUpMenuMessages signUp(String content) throws InterruptedException, IOException {
        String username = Controller.getPart(content , "-u");
        String password = Controller.getPart(content , "-p");
        String nickName = Controller.getPart(content , "-n");
        String slogan = Controller.getPart(content , "-s");
        String email = Controller.getPart(content , "-e");
        String checkPassword = null;
        if(password != null)
            checkPassword = Controller.getPart(content, modifyPassword(password));
        if(username == null)
            return NOT_USERNAME;
        if(password == null)
            return NOT_PASSWORD;
        if(nickName == null)
            return NOT_NICKNAME;
        if(email == null)
            return NOT_EMAIL;
        if(slogan == null && Pattern.compile("-s ").matcher(content).find())
            return NOT_SLOGAN;
        if(!username.matches("[\\w \t]+"))
            return FAIL_USERNAME_FORMAT;
        if(User.getUserByUsername(username) != null)
            return USERNAME_EXISTS;
        if(!Controller.checkPasswordFormat(password))
            return WEAK_PASSWORD;
        if(!Objects.equals(checkPassword, password) && !password.equals("random"))
            return FAIL_PASSWORD_CONFIRMATION;
        if(User.getUserByEmail(email) != null)
            return EMAIL_EXISTS;
        if(!email.matches("\\S+@\\S+\\.\\S+"))
            return FAIL_EMAIL_FORMAT;
        if(password.equals("random")){
            String randomPassword = randomPassword().substring(0 , 8);
            if(Menu.setRandomPassword(randomPassword))
                password = randomPassword;
            else
                return FAIL_PASSWORD_CONFIRMATION;
        }
        if(slogan != null && slogan.equals("random")){
            int random = (int) Math.floor(Math.random() * 15);
            slogan = User.slogans[random];
            System.out.println("Your slogan: " + User.slogans[random]);
        }
        password = Controller.hashMaker(password);
        User user = new User(username , password , nickName , email , 0 , 0 , 0 , null , slogan);
        if(!Menu.securityQuestion(user))
            return null;
        if (Menu.captcha())
            return null;
        User.addUser(user);
        User.addUserToFile(user);
        return SUCCESS;
    }
    public static SignUpMenuMessages login(String content) throws InterruptedException, IOException {
        String username = Controller.getPart(content , "-u");
        String password = Controller.getPart(content , "-p");
        if(username == null || password == null)
            return NULL_FIELDS;
        if(User.getUserByUsername(username) == null)
            return USERNAME_DOES_NOT_EXIST;
        User user = User.getUserByUsername(username);
        String realPassword = null;
        if (user != null) {
            realPassword = user.getPassword();
        }
        password = Controller.hashMaker(password);
        if(Menu.checkPassword(realPassword , "login", password))
            return null;
        if (Menu.captcha())
            return null;
        Matcher matcher = Pattern.compile("--stay-logged-in").matcher(content);
        if(matcher.find()){
            if (user != null) {
                Controller.stayLoggedIn(user);
            }
        }
        Menu.currentUser = User.getUserByUsername(username);
        return SUCCESS;
    }
    public static String randomPassword(){
            UUID randomUUID = UUID.randomUUID();
            return randomUUID.toString().replaceAll("-", "");
    }
    public static String modifyPassword(String password){
        password = password.replaceAll("\\*" , "\\\\*");
        password = password.replaceAll("\\(" , "\\\\(");
        password = password.replaceAll("\\)" , "\\\\)");
        password = password.replaceAll("\\[" , "\\\\[");
        return password;
    }
    public static String modifyComma(String string){
        if(string != null) {
            if (string.charAt(string.length() - 1) == ',')
                string = string.substring(0, string.length() - 1);
            return string;
        }
        return "0";
    }
}
