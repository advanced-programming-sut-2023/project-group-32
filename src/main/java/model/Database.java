package model;

import controller.Controller;
import controller.SignUpMenuController;
import view.MainMenu;
import view.Menu;

import java.io.*;

public class Database {
    public static void run() throws IOException, InterruptedException {
        String file = "players.json";
        StringBuilder lines = new StringBuilder();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null)
                lines.append(line).append("\n");
            Controller.getData(lines.toString());
        } catch (IOException e){
            System.out.println("ERROR!");
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader("stayLoggedIn.txt"));
        String name = bufferedReader.readLine();
        bufferedReader.close();
        if(name != null){
            Menu.currentUser = User.getUserByUsername(name);
            MainMenu.run();
        }
    }
    public static void update() throws IOException {
        FileWriter fileWriter = new FileWriter("players.json");
        fileWriter.write("");
        fileWriter.close();
        for (User user : User.getUsers()) {
            User.addUserToFile(user);
        }
    }
    public void InitializeDatabase(){

    }
    private void InitializeItems(){

    }
    private void InitializeBuildings(){

    }
    private void InitializeTroops(){

    }
}
