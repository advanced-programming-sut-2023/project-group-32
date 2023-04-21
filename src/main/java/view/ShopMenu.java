package view;

public class ShopMenu {
    public static void run(){
        String command;
        while (true){
            command = Menu.getScanner().nextLine();
            if(command.matches("^\\s*show\\s+current\\s+menu\\s*$"))
                System.out.println("Shop Menu");
            else if(command.matches("^\\s*back\\s*$")){
                System.out.println("Entered Game Menu!");
                return;
            }
            else
                System.out.println("Invalid command!");
        }
    }
}
