// Java Program to Print Colored Text in Console

// Importing input output classes
import org.junit.Before;

import java.io.*;

// Main class
public class Test {


    public static final String TEXT_RED    = "\u001B[31m";
    public static final String TEXT_GREEN  = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE   = "\u001B[34m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_CYAN   = "\u001B[36m"; //firozeh
    public static final String TEXT_BRIGHT_BLACK  = "\u001B[90m"; //tusi
    public static final String TEXT_BRIGHT_WHITE  = "\u001B[97m"; //sefid

    public static final String TEXT_BG_BLACK  = "\u001B[40m";
    public static final String TEXT_BG_RED    = "\u001B[41m";
    public static final String TEXT_BG_GREEN  = "\u001B[42m";
    public static final String TEXT_BG_YELLOW = "\u001B[43m";
    public static final String TEXT_BG_BLUE   = "\u001B[44m";
    public static final String TEXT_BG_PURPLE = "\u001B[45m";
    public static final String TEXT_BG_CYAN   = "\u001B[46m";
    public static final String WHITE = "\u001B[7m";

    public static final String TEXT_BRIGHT_BG_BLACK  = "\u001B[100m";
    public static final String TEXT_BRIGHT_BG_RED    = "\u001B[101m";
    public static final String TEXT_BRIGHT_BG_GREEN  = "\u001B[102m";
    public static final String TEXT_BRIGHT_BG_YELLOW = "\u001B[103m";
    public static final String TEXT_BRIGHT_BG_BLUE   = "\u001B[104m";
    public static final String TEXT_BRIGHT_BG_PURPLE = "\u001B[105m";
    public static final String TEXT_BRIGHT_BG_CYAN   = "\u001B[106m";
    public static final String TEXT_BRIGHT_BG_WHITE  = "\u001B[107m";
    public static void main(String[] args) {
        String TEXT_RESET  = "\u001B[0m";
        System.out.println(TEXT_RESET + "salam");
        //System.out.println(TEXT_BG_BLUE + "salam");
    }
    @org.junit.Test
    public void test(){

    }
}
