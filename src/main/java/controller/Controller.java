package controller;

import model.User;
import view.Menu;
import view.ShopMenu;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public static int captchaGenerator(){
        String[] strings = new String[7];
        for(int i = 0 ; i < 7 ; i++){
            strings[i] = "";
        }
        int code = 0;
        int number;
        int random;
        int randomSize = (int) Math.floor(Math.random() * 4) + 4;
        for(int i = 0 ; i < randomSize ; i++){
            number = (int) Math.pow(10 , randomSize - 1 - i);
            random = (int) Math.floor(Math.random() * 10);
            code += number * random;
            Save(random , strings);
        }
        for(int i = 0 ; i < 7 ; i++){
            System.out.println(strings[i]);
        }
        return code;
    }
    public static void Save(int a , String[] strings){
        switch (a) {
            case 2 -> {
                strings[0] += "  ****    ";
                strings[1] += " *    *   ";
                strings[2] += "      *   ";
                strings[3] += "   ***    ";
                strings[4] += "  *       ";
                strings[5] += " *        ";
                strings[6] += " ******   ";
            }
            case 1 -> {
                strings[0] += "  *     ";
                strings[1] += " ***    ";
                strings[2] += "  **    ";
                strings[3] += "  **    ";
                strings[4] += "  **    ";
                strings[5] += "  *     ";
                strings[6] += " ****   ";
            }
            case 3 -> {
                strings[0] += "   ****    ";
                strings[1] += "      *    ";
                strings[2] += "      *    ";
                strings[3] += "   ***     ";
                strings[4] += "      *    ";
                strings[5] += "      *    ";
                strings[6] += "  ****     ";
            }
            case 4 -> {
                strings[0] += "     *    ";
                strings[1] += "   * *    ";
                strings[2] += "  *  *    ";
                strings[3] += "  *****   ";
                strings[4] += "     *    ";
                strings[5] += "     *    ";
                strings[6] += "     *    ";
            }
            case 5 -> {
                strings[0] += " ** ***    ";
                strings[1] += " *         ";
                strings[2] += "  *****    ";
                strings[3] += "       *   ";
                strings[4] += "       *   ";
                strings[5] += "  *    *   ";
                strings[6] += "   ****    ";
            }
            case 6 -> {
                strings[0] += " ****     ";
                strings[1] += " *        ";
                strings[2] += " *        ";
                strings[3] += " ******   ";
                strings[4] += " *    *   ";
                strings[5] += " *    *   ";
                strings[6] += "  ****    ";
            }
            case 7 -> {
                strings[0] += " *** **   ";
                strings[1] += "      *   ";
                strings[2] += "      *   ";
                strings[3] += "     *    ";
                strings[4] += "     *    ";
                strings[5] += "   *      ";
                strings[6] += "  *       ";
            }
            case 8 -> {
                strings[0] += " *****    ";
                strings[1] += " *    *   ";
                strings[2] += " *    *   ";
                strings[3] += "  ****    ";
                strings[4] += " *    *   ";
                strings[5] += " *    *   ";
                strings[6] += "  ****    ";
            }
            case 9 -> {
                strings[0] += "  ****    ";
                strings[1] += " *   *    ";
                strings[2] += "*    *    ";
                strings[3] += "  ****    ";
                strings[4] += "    *     ";
                strings[5] += "   *      ";
                strings[6] += "  *       ";
            }
            case 0 -> {
                strings[0] += "  ****    ";
                strings[1] += "*   **    ";
                strings[2] += " *  * *   ";
                strings[3] += " * *   *  ";
                strings[4] += " **   *   ";
                strings[5] += " *    *   ";
                strings[6] += "  *****   ";
            }
            default -> {
            }
        }
    }
    public static void setRank(){
        for(int i = 0; i < User.getUsers().size() - 1 ; i++){
            if(User.getUsers().get(i).getHighScore() < User.getUsers().get(i+1).getHighScore()){
                int t = User.getUsers().get(i).getRank();
                User.getUsers().get(i).setRank(User.getUsers().get(i+1).getRank());
                User.getUsers().get(i+1).setRank(t);
                i = -1;
            }
        }
    }
    public static String hashMaker(String password){
        if(password != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA algorithm not supported!", e);
            }
        }
        return null;
    }
    public static void stayLoggedIn(User user) throws IOException {
        FileWriter fileWriter = new FileWriter("stayLoggedIn.txt");
        fileWriter.write(user.getUsername());
        fileWriter.close();
    }
    public static String getPart(String content , String mark){
        Matcher matcher = Pattern.compile(mark + "\\s+(?<firstWord>\\S+)").matcher(content);
        if(matcher.find()){
            String part = null;
            if(matcher.group("firstWord").charAt(0) == '\"'){
                matcher = Pattern.compile(mark + "\\s+\"(?<username>[^\"]+)\"").matcher(content);
                if(matcher.find())
                    part = matcher.group("username");
            }
            else
                part = matcher.group("firstWord");
            return part;
        }
        return null;
    }
    public static void getData(String list) throws IOException {
        String[] users = list.split("}\\{");
        for (String user : users) {
            getUser(user);
        }
    }
    public static void getUser(String user){
        String username = Controller.getPart(user, "\"username\":");
        String password = Controller.getPart(user, "\"password\":");
        String nickName = Controller.getPart(user, "\"nickName\":");
        String email = Controller.getPart(user, "\"email\":");
        String questionAnswer = Controller.getPart(user, "\"questionAnswer\":");
        String slogan = Controller.getPart(user, "\"slogan\":");
        int rank = Integer.parseInt(SignUpMenuController.modifyComma(Controller.getPart(user, "\"rank\":")));
        int highScore = Integer.parseInt(SignUpMenuController.modifyComma(Controller.getPart(user, "\"highScore\":")));
        int questionNumber = Integer.parseInt(SignUpMenuController.modifyComma(Controller.getPart(user, "\"questionNumber\":")));
        if(username != null)
            User.addUser(new User(username , password , nickName , email , highScore , rank , questionNumber , questionAnswer , slogan));
    }
    public static boolean checkPasswordFormat(String password){
        return password.equals("random") || (password.length() >= 6 && password.matches(".*[a-z].*") && password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") && password.matches(".*[^a-zA-Z0-9].*"));
    }
}
