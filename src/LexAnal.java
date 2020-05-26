import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LexAnal {
    private String SYMBOLEQUAL = "Symbol Equal";
    private String WORDA = "Word a";
    private String WORDB = "Word b";
    private String WORDC = "Word c";
    private String NUMBER1 = "Number 1";
    private String NUMBER2 = "Number 2";
    private String REGULARE = "Regular E";
    private String REGULARD = "Regular D";

    private int numberOfLine;
    private int position;
    private int startPosition;
    private String line;
    File output;
    FileWriter fileWriter;

    public LexAnal(int numberOfLine, String line) {
        this.numberOfLine = numberOfLine;
        this.line = line;
        this.position = -1;
        this.startPosition = 0;
        /**System.out.println();
        System.out.println("Line number " + (numberOfLine + 1) + " .");
        System.out.println("Line : " + line + " . ");/**/
        output = new File("output" + (numberOfLine + 1) + ".txt");
        if (!output.exists()) {
            try {
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileWriter = new FileWriter(output);
            fileWriter.write("Line number " + (numberOfLine + 1) + " .\n");
            fileWriter.write("Line : " + line + " . \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getToken() {
        this.position += 1;
        String symbol = String.valueOf(this.line.charAt(position));
        if (symbol.equals("a")) {
            lexType(symbol, WORDA);
            getToken();
        } else if (symbol.equals("b")) {
            lexType(symbol, WORDB);
            getToken();
        } else if (symbol.equals("c")) {
            lexType(symbol, WORDC);
            getToken();
        } else if (symbol.equals("1")) {
            lexType(symbol, NUMBER1);
            getToken();
        } else if (symbol.equals("2")) {
            lexType(symbol, NUMBER2);
            getToken();
        } else if (symbol.equals("o")) {
            checkRegE();
        } else if (symbol.equals(":")) {
            checkEqual();
        } else
            error(symbol);

    }


    private void checkEqual() {
        String buf = ":";
        this.position += 1;
        if (String.valueOf(this.line.charAt(position)).equals("=")) {
            buf += String.valueOf(this.line.charAt(position));

            lexType(buf, SYMBOLEQUAL);
            checkRegD("");
        } else

            error(buf + this.line.charAt(position));

    }

    private void checkRegD(String reg) {
        this.position += 1;
        if (position == line.length() - 1) {
            error(reg + this.line.charAt(position));
            return;
        }

        if (String.valueOf(this.line.charAt(position)).equals("0")) {
            checkRegD(reg + "0");
        } else if (String.valueOf(this.line.charAt(position)).equals("1")) {
            this.position += 1;
            reg += "1";
            if (String.valueOf(this.line.charAt(position)).equals("0")) {
                if (position == line.length() - 1) {
                    lexType(reg + "0", REGULARD);
                    try {
                        fileWriter.write("\nLine belongs to grammar language.");
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    checkRegD(reg + "0");
            } else if (String.valueOf(this.line.charAt(position)).equals("1")) {
                checkRegD(reg + this.line.charAt(position));
            } else
                error(reg + this.line.charAt(position));
        } else
            error(reg + this.line.charAt(position));
    }

    private void checkRegE() {
        String buf = "o";
        this.position += 1;
        if (String.valueOf(this.line.charAt(position)).equals("p")) {
            buf += String.valueOf(this.line.charAt(position));
            this.position += 1;
            if (String.valueOf(this.line.charAt(position)).equals("e")) {
                buf += String.valueOf(this.line.charAt(position));
                this.position += 1;
                if (String.valueOf(this.line.charAt(position)).equals("r")) {
                    buf += String.valueOf(this.line.charAt(position));
                    this.position += 1;
                    if (String.valueOf(this.line.charAt(position)).equals("1") ||
                            String.valueOf(this.line.charAt(position)).equals("2")) {
                        buf += String.valueOf(this.line.charAt(position));
                    } else
                        this.position -= 1;
                    lexType(buf, REGULARE);
                    getToken();
                } else
                    error(buf + this.line.charAt(position));
            } else
                error(buf + this.line.charAt(position));
        } else
            error(buf + this.line.charAt(position));
    }

    private void error(String symbol) {
        /**System.out.println("Error in { " + numberOfLine + " } line, at { " + position + " } position. " +
                "Unknown symbol : { " + symbol + " }.");/**/
        try {
            fileWriter.write("\n\nError in { " + numberOfLine + " } line, at { " + position + " } position. " +
                    "Unknown symbol : { " + symbol + " }.");
            fileWriter.write("\nLine don`t belongs to grammar language.");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void lexType(String lex, String type) {
        /**System.out.println("< " + lex + " , " + type + " >");/**/
        try {
            fileWriter.write("\n< " + lex + " , " + type + " >");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
