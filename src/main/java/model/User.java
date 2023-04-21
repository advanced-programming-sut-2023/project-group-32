package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickName;
    private String email;
    public int questionNumber;
    private String questionAnswer;
    private int highScore;
    private int rank;
    private String slogan;
    private final Government government = new Government();

    public static String[] slogans = new String[]{"I shall have my revenge, in this life or the next.",
            "The object of war is not to die for your country, but to make the other bastard die for his.",
            "How high you fly depends on how brave you are.",
            "Never interrupt your enemy when he is making a mistake.",
            "Never forget that your weapon is made by the lowest bidder.",
            "Teamwork is essential, it gives them someone else to shoot at.",
            "Skip the War, Start the Peace Now",
            "Great is the guilt of an unnecessary war.",
            "Our modern states are preparing for war without even knowing the future enemy.",
            "In war, you win or lose, live, or die – and the difference is just an eyelash.",
            "The only excuse for war is that we may live in peace unharmed.",
            "War is only a cowardly escape from the problems of peace.",
            "The first and most imperative necessity in war is money, for money means everything else – men, guns, ammunition.",
            "A war is a horrible thing, but it’s also a unifier of countries.",
            "I AM A SOLDIER, I FIGHT WHERE I AM TOLD AND I WIN WHERE I FIGHT!"
    };
    public static String[] securityQuestions = new String[]{"What is my father’s name?",
            "What was my first pet’s name?",
            "What is my mother’s last name?"};
    public User(String username , String password , String nickName , String email , int highScore , int rank , int questionNumber , String questionAnswer , String slogan){
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.slogan = slogan;
        this.rank = rank;
        this.highScore = highScore;
        this.questionNumber = questionNumber;
        this.questionAnswer = questionAnswer;
    }
    public String getPassword(){
        return password;
    }
    private static final ArrayList<User> users = new ArrayList<>();
    public static void addUser(User user) {
        users.add(user);
    }
    public static void addUserToFile(User user){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try{
            FileWriter fileWriter = new FileWriter("players.json" , true);
            gson.toJson(user , fileWriter);
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setSecurityQuestion(int questionNum , String answer){
        this.questionAnswer = answer;
        this.questionNumber = questionNum - 1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public static User getUserByUsername(String username){
        for (User user : users) {
            if(user.username.equals(username))
                return user;
        }
        return null;
    }
    public static User getUserByEmail(String email){
        for (User user : users) {
            if(user.email.equalsIgnoreCase(email))
                return user;
        }
        return null;
    }
    public static ArrayList<User> getUsers(){
        return users;
    }
    public static String suggestUsername(String username){
        int i = 1;
        String suggestingUsername;
        while (true){
            suggestingUsername = username + i;
            if(getUserByUsername(suggestingUsername) == null)
                return suggestingUsername;
            else
                i++;
        }
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getQuestionAnswer(){
        return questionAnswer;
    }
    public int getHighScore(){
        return highScore;
    }
    public int getRank(){
        return rank;
    }
    public void setRank(int rank){
        this.rank = rank;
    }

    public Government getGovernment() {
        return government;
    }
}