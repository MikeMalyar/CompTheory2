import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Lab2 {

    private static FileWriter writer;

    public static void main(String[] args) throws IOException {
        try {
            File input = new File("input.txt");
            Scanner sc = new Scanner(input);
            int index = 1;
            while (sc.hasNextLine()) {
                try {
                    File output = new File("output" + index  + ".txt");
                    if (!output.exists()) {
                        output.createNewFile();
                    }
                    String line = sc.nextLine();
                    writer = new FileWriter(output);
                    writer.write("Output file for line " + index + " \"" + line + "\"\n\n");
                    System.out.println("Processing line " + index + " \"" + line + "\"");
                    writer.write("Tokens found:\n");
                    LinkedList<String> tokens = getLineTokens(line);
                    if (checkTokens(tokens)) {
                        System.out.println("Line belongs to this grammar language\n");
                    } else {
                        System.out.println("Line doesn't belong to this grammar language\n");
                    }
                    writer.flush();
                } catch (IllegalStateException ex) {
                    writer.write(ex.getMessage() + "\n");
                    System.out.println(ex.getMessage() + "\n");
                    writer.flush();
                }
                ++index;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File input.txt was not found.");
            e.printStackTrace();
        }
    }

    public static LinkedList<String> getLineTokens(String line) throws IOException {
        LinkedList<String> res = new LinkedList<>();

        for (int i = 0; i < line.length(); ++i) {
            try {
                String token = getToken(line.substring(i));
                res.add(token);
                writer.write("<" + Token.fromString(token) + ", " + token + ">" + "\n");
                i += token.length() - 1;
            } catch (IllegalStateException ignored) {
                throw new IllegalStateException("An error occurred at position " + i + " (positions start from 0)");
            }
        }

        return res;
    }

    /** get_token method
     *  was renamed due to Java convention
     * @param string - string to find a token from start
     */
    public static String getToken(String string) {
        for (String tokenString : Arrays.stream(Token.values()).map(Token::getString).collect(Collectors.toList())) {
            if (string.startsWith(tokenString)) {
                return tokenString;
            }
        }
        throw new IllegalStateException("String doesn't start from a known token");
    }

    public static boolean checkTokens(LinkedList<String> tokens) {
        return checkS(tokens);
    }

    public static boolean checkS(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        return checkFor(tokens) && checkSpace(tokens) && checkA(tokens) && checkSpace(tokens) && checkTo(tokens)
                && checkSpace(tokens) && checkD(tokens) && checkSpace(tokens) && checkDo(tokens) && checkSpace(tokens)
                    && checkC(tokens);
    }

    public static boolean checkFor(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        if (tokens.get(0).equals(Token.TOKEN_FOR.getString())) {
            tokens.removeFirst();
            return true;
        }
        return false;
    }

    public static boolean checkSpace(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        if (tokens.get(0).equals(Token.TOKEN_SPACE.getString())) {
            tokens.removeFirst();
            return true;
        }
        return false;
    }

    public static boolean checkA(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        return checkV(tokens) && checkEquals(tokens) && checkD(tokens);
    }

    public static boolean checkV(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        String token = tokens.get(0);
        if (token.equals(Token.TOKEN_I1.getString()) || token.equals(Token.TOKEN_I2.getString())
                || token.equals(Token.TOKEN_I3.getString())) {
            tokens.removeFirst();
            return true;
        }
        return false;
    }

    public static boolean checkEquals(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        if (tokens.get(0).equals(Token.TOKEN_EQUALS.getString())) {
            tokens.removeFirst();
            return true;
        }
        return false;
    }

    public static boolean checkD(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return true;
        }
        String token = tokens.get(0);
        if (token.equals(Token.TOKEN_55.getString()) || token.equals(Token.TOKEN_66.getString())) {
            tokens.removeFirst();
            return true;
        }
        if (token.equals(Token.TOKEN_0.getString()) || token.equals(Token.TOKEN_1.getString())) {
            tokens.removeFirst();
            checkD(tokens);     // result of this check isn't needful, if D is present then tokens be removed
            return true;
        }
        return false;
    }

    public static boolean checkTo(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        if (tokens.get(0).equals(Token.TOKEN_TO.getString())) {
            tokens.removeFirst();
            return true;
        }
        return false;
    }

    public static boolean checkDo(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        if (tokens.get(0).equals(Token.TOKEN_DO.getString())) {
            tokens.removeFirst();
            return true;
        }
        return false;
    }

    public static boolean checkC(LinkedList<String> tokens) {
        if (tokens.size() == 0) {
            return false;
        }
        return checkS(tokens) || checkA(tokens);
    }
}
