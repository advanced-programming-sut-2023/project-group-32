package view;

import controller.Controller;
import model.Database;
import model.User;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.SignUpMenuController.randomPassword;

public class Menu {
    public static User currentUser;
    private static final Scanner scanner = new Scanner(System.in);
    public static Scanner getScanner(){
        return scanner;
    }
    public static Matcher getMatcher(String command , String regex){
        Matcher matcher = Pattern.compile(regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
    public static boolean checkPassword(String realPassword , String condition , String enteredPassword) throws InterruptedException {
        int delayCounter = 5;
        if(enteredPassword == null) {
            enteredPassword = scanner.nextLine();
            enteredPassword = Controller.hashMaker(enteredPassword);
        }
        while (true) {
            if (enteredPassword.equals(realPassword)) {
                if(condition.equals("random"))
                    System.out.println("Your password set successfully!");
                return false;
            }
            if (enteredPassword.equals("cancel")) {
                System.out.println("The operation is canceled!");
                return true;
            }
            else{
                System.out.println("Wrong password! Write you password again or if you want to cancel the operation type \"cancel\".");
                Thread.sleep(1000L * delayCounter);
                delayCounter += 5;
            }
            enteredPassword = scanner.nextLine();
            enteredPassword = Controller.hashMaker(enteredPassword);
        }
    }
    public static boolean securityQuestion(User user){
        String questionPickRegex = "\\s*question\\s+pick\\s+-q\\s+(?<number>[1-3])\\s+-a\\s+(?<answer>.+)\\s+-c\\s+(?<answerConfirmation>.+)\\s*";
        String command;
        Matcher matcher;
        System.out.println("Pick your security question:\n1.What is my father’s name?" +
                "2.What was my first pet’s name?" +
                "3.What is my mother’s last name?");
        while (true){
            command = scanner.nextLine();
            matcher = Pattern.compile(questionPickRegex).matcher(command);
            if(command.equals("cancel")){
                System.out.println("The operation is canceled!");
                return false;
            }
            else if(matcher.find()){
                if (!matcher.group("answer").equals(matcher.group("answerConfirmation"))) {
                    System.out.println("Answer confirmation is not same as your answer!");
                }
                else {
                    user.setSecurityQuestion(Integer.parseInt(matcher.group("number")) , matcher.group("answer"));
                    return true;
                }
            }
            else{
                System.out.println("Invalid command! If you want to cancel the operation type \"cancel\".");
            }
        }
    }
    public static void checkAnswer(User user) throws InterruptedException, IOException {
        String command;
        while (true){
            command = scanner.nextLine();
            if(command.equals("cancel")) {
                System.out.println("The operation canceled!");
                return;
            }
            else if(command.equals(user.getQuestionAnswer())){
                System.out.println("Enter your new password:");
                String newPassword = scanner.nextLine();
                if(!Controller.checkPasswordFormat(newPassword))
                    System.out.println("Your new password is weak!");
                else if(newPassword.equals("random")){
                    String randomPassword = randomPassword().substring(0 , 8);
                    System.out.println("Your random password is: " + randomPassword);
                    System.out.println("Please re-enter your password: ");
                    if(checkPassword(Controller.hashMaker(randomPassword), "random", null))
                        return;
                    user.setPassword((randomPassword));
                }
                else {
                    System.out.println("Please enter your new password again:");
                    if(checkPassword(Controller.hashMaker(newPassword) , "change", null))
                        return;
                    else {
                        user.setPassword(Controller.hashMaker(newPassword));
                        System.out.println("password changed successfully to: " + newPassword);
                        Database.update();
                    }
                }
                return;
            }
            else
                System.out.println("Wrong answer! If you want to cancel the operation type \"cancel\".");
        }
    }
    public static boolean captcha(){
        String command;
        while (true){
            int code = Controller.captchaGenerator();
            command = scanner.nextLine();
            if(command.matches("[0-9]+")){
                if(Integer.parseInt(command) == code) {
                    System.out.println("Correct!");
                    return false;
                }
                else {
                    System.out.println("Wrong! If you want to make another captcha write \"again\", else you will quit captcha operation.");
                    if(scanner.nextLine().equals("again"))
                        continue;
                    else
                        return true;
                }
            }
            else {
                System.out.println("Wrong! If you want to make another captcha write \"again\", else you will quit captcha operation.");
                if (scanner.nextLine().equals("again"))
                    continue;
                else {
                    System.out.println("Captcha canceled!");
                    return true;
                }
            }
        }
    }
    public static boolean setRandomPassword(String randomPassword){
        System.out.println("Your random password is: " + randomPassword);
        System.out.println("Type your random password: ");
        String password = Menu.getScanner().nextLine();
        return password.equals(randomPassword);
    }
}