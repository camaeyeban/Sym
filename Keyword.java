public class Keyword {
    private Token token;
    private int length;

    public Keyword(Token token, int length) {
        this.token = token;
        this.length = length;
    }

    public Token getToken() {
        return this.token;
    }

    public int getLength() {
        return this.length;
    }
}