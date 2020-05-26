import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        ArrayList<String> lines = readFileInList();

        checkLines(lines);
    }

    private static void checkLines(ArrayList<String> lines) {
        int i = 0;
        for (String line : lines) {
            LexAnal lexAnal = new LexAnal(i, line);
            lexAnal.getToken();
            ++i;
        }
    }

    public static ArrayList<String> readFileInList()
    {
        ArrayList<String> lines = new  ArrayList<>();
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }
}
