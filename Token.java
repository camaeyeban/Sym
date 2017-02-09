public class Token {
    String type;
    String lexeme;

    public Token(String type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public String getType() {
        return this.type;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public String toString() {
        return "\n{ \""+ this.type +"\": \"" + this.lexeme + "\" }";
    }
}