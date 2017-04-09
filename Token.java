public class Token {
    private String value;
    private String type;
    private String lexeme;
	private int lineNumber;

    public Token(String type, String lexeme, int lineNumber) {
        this.type = type;
        this.lexeme = lexeme;
		this.lineNumber = lineNumber;

        this.value = ValueConverter.convert(type);
    }
	
	public Token(){
		this.type = "END_OF_FILE_MARKER";
		this.lexeme = "$";
		this.value = "";
		this.lineNumber = -1;
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

    public int getLineNumber() {
        return this.lineNumber;
    }
	
    public String toString() {
        return "\n<" + value + ", "+ this.type +", \"" + this.lexeme + "\", " + this.lineNumber + ">";
    }
	
}