package view;

import controller.Controller;
import controller.SignUpMenuController;
import model.User;
import view.messages.SignUpMenuMessages;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;

import static view.messages.SignUpMenuMessages.*;

public class SignUpMenu {
    public static String createUserRegex = "^\\s*user\\s+create\\s+(?<content>.+)$";
    public static String loginUserRegex = "^\\s*user\\s+login\\s+(?<content>.+)$";
    public static String forgotPassword = "^\\s*forgot\\s+my\\s+password\\s+(?<content>.+)$";
    public static void run() throws InterruptedException, IOException {
        String command;
        Matcher matcher;
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*exit\\s*$"))
                return;
            if((matcher = Menu.getMatcher(command , createUserRegex)) != null)
                signUp(matcher.group("content"));
            else if((matcher = Menu.getMatcher(command , loginUserRegex)) != null)
                login(matcher.group("content"));
            else if(command.matches("^\\s*show\\s+current\\s+menu$"))
                System.out.println("Signup Menu");
            else if((matcher = Menu.getMatcher(command , forgotPassword)) != null)
                forgotPassword(matcher.group("content"));
            else
                System.out.println("Invalid command!");
        }
    }
    public static void signUp(String content) throws IOException, InterruptedException {
        SignUpMenuMessages messages = SignUpMenuController.signUp(content);
        if(Objects.equals(messages, NOT_USERNAME))
            System.out.println("You must have a username!");
        else if(Objects.equals(messages , NOT_PASSWORD))
            System.out.println("You must have a password!");
        else if(Objects.equals(messages , NOT_EMAIL))
            System.out.println("You must have an email!");
        else if(Objects.equals(messages , NOT_NICKNAME))
            System.out.println("You must have a nickname!");
        else if(Objects.equals(messages , NOT_SLOGAN))
            System.out.println("You didn't type your slogan!");
        else if(Objects.equals(messages , FAIL_USERNAME_FORMAT))
            System.out.println("Incorrect format for username!");
        else if(Objects.equals(messages , USERNAME_EXISTS)){
            System.out.println("Username already exists!");
            System.out.println("You can set your username as: " + User.suggestUsername(Controller.getPart(content , "-u")));
        }
        else if(Objects.equals(messages , WEAK_PASSWORD))
            System.out.println("Your password is weak!");
        else if(Objects.equals(messages , FAIL_PASSWORD_CONFIRMATION))
            System.out.println("Your password confirmation is not the same as your password!");
        else if(Objects.equals(messages , EMAIL_EXISTS))
            System.out.println("Email already exists!");
        else if(Objects.equals(messages , FAIL_EMAIL_FORMAT))
            System.out.println("Incorrect format for email!");
        else if(Objects.equals(messages , SUCCESS))
            System.out.println("User " + Controller.getPart(content , "-u") + " created successfully!");
    }
    public static void login(String content) throws IOException, InterruptedException {
        SignUpMenuMessages messages = SignUpMenuController.login(content);
        if(Objects.equals(messages, NULL_FIELDS))
            System.out.println("You can't log in without your username and password!");
        else if(Objects.equals(messages , USERNAME_DOES_NOT_EXIST))
            System.out.println("No user found with this username");
        else if(Objects.equals(messages , SUCCESS)){
            System.out.println("User logged in successfully!");
            MainMenu.run();
        }
    }
    public static void forgotPassword(String content) throws InterruptedException, IOException {
        String username = Controller.getPart(content , "-u");
        User user = User.getUserByUsername(username);
        if(user == null)
            System.out.println("Username does not exist!");
        else{
            System.out.println(User.securityQuestions[user.questionNumber]);
            Menu.checkAnswer(user);
        }
    }
}
