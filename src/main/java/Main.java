import model.Database;
import view.SignUpMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Database.run();
        SignUpMenu.run();
    }
}