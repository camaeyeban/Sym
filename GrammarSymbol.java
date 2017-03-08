public class GrammarSymbol {
    private String symbol;
    private int indent;

    public GrammarSymbol(String symbol, int indent) {
        this.symbol = symbol;
        this.indent = indent;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getIndent() {
        return this.indent;
    }
}