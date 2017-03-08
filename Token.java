public class Token {
    private String value;
    private String type;
    private String lexeme;

    public Token(String type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;

        this.value = ValueConverter.convert(type);
    }
	
	public Token(){
		this.type = "END_OF_FILE_MARKER";
		this.lexeme = "$";
		this.value = "";
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