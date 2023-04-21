import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        System.out.println(command);
        Matcher matcher = Pattern.compile("\\s*start\\s+a\\s+game\\s+with\\s+users:\\s*\"(?<users>[^\"]+)\"\\s+turns\\s+number:\\s+(?<turnsNumber>\\d+)\\s*$").matcher(command);
        if(matcher.find())
            System.out.println(matcher.group("users"));
    }
}
