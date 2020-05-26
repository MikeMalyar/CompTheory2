import java.util.Arrays;

public enum Token {

    TOKEN_FOR("for"),
    TOKEN_TO("to"),
    TOKEN_DO("do"),
    TOKEN_I1("i1"),
    TOKEN_I2("i2"),
    TOKEN_I3("i3"),
    TOKEN_0("0"),
    TOKEN_1("1"),
    TOKEN_55("55"),
    TOKEN_66("66"),
    TOKEN_SPACE(" "),
    TOKEN_EQUALS(":=");

    private String string;

    Token(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static Token fromString(String string) {
        return Arrays.stream(Token.values()).filter(token -> token.getString().equals(string)).findFirst()
                .orElseThrow(() -> new IllegalStateException("Token with string " + string + " not found"));
    }
}
