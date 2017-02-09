public class Token {
    String value;
    String type;
    String lexeme;

    public Token(String type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;

        this.value = ValueConverter.convert(type);
    }

    public String getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public String toString() {
        return "\n<" + value + ", "+ this.type +", \"" + this.lexeme + "\">";
    }
}