import java.util.Scanner;

public class Game
{
    public static Scanner scanner = new Scanner(System.in);

    // see in: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static Scanner scan = new Scanner(System.in);


    public static void main(String[] args)
    {
        Board board = new Board();
    }


    public static String bold_redTXT (String str) {return "\033[1;31m" + str + "\u001B[0m";}
    public static String greenTXT (String str) {return "\u001B[32m" +str+ "\u001B[0m";}
    public static String cyanTXT (String str) {return "\u001B[36m" +str+ "\u001B[0m";}
    public static String yellowTXT (String str) {return "\u001B[33m" +str+ "\u001B[0m";}
    public static String redTXT (String str) {return "\u001B[31m" +str+ "\u001B[0m";}
}
