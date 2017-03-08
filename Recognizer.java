import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

// @TODO: store grammar tree in a data structure

public class Recognizer {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private Stack<GrammarSymbol> grammarSymbols = new Stack<GrammarSymbol>();
	private ParseTable parseTable = new ParseTable();
	
    public Recognizer(ArrayList<Token> tokens) {
		this.tokens = tokens;
		this.tokens.add(new Token());
		this.grammarSymbols.push(new GrammarSymbol("$", 0));
		this.grammarSymbols.push(new GrammarSymbol("Statements", 0));
	}
	
	public String recognize(){
		for(int i=0; i<this.tokens.size(); ) {
			String tokenName = tokens.get(i).getType();
			String lexeme = tokens.get(i).getLexeme();
			GrammarSymbol tos = grammarSymbols.peek(); // tos = top of stack
			
			// if TOS = epsilon
			if(tos.getSymbol().equals("EPSILON")){
				grammarSymbols.pop();
			}
			
			// if TOS = terminal
			else if(Arrays.asList(parseTable.terminals).contains(tos.getSymbol())){
				if(tos.getSymbol().equals(tokenName)){
					GrammarSymbol g = grammarSymbols.pop();
					i++;
					System.out.println(repeat("  ", g.getIndent()) + g.getSymbol());
				}
				else{
					return "REJECTED due to mismatch of current token and top of stack";
				}
			}
			// if TOS = nonterminal
			else if(Arrays.asList(parseTable.nonterminals).contains(tos.getSymbol())){
				int row = Arrays.asList(parseTable.nonterminals).indexOf(tos.getSymbol());
				int col = Arrays.asList(parseTable.terminals).indexOf(tokenName);
				String production = parseTable.getProduction(row, col);

				if(production == "ERROR"){
					return "REJECTED due to absence of production rule in the corresponding cell of parse table";
				}
				else{
					GrammarSymbol g = grammarSymbols.pop();
					String entries[] = production.split("<|>|-| ", -1);

					System.out.println(repeat("  ", g.getIndent()) + g.getSymbol());
					
					for(int j=entries.length-1; j>0; j--){
						if(entries[j].length() > 0){
							grammarSymbols.push(new GrammarSymbol(entries[j], g.getIndent() + 1));
						}
					}
				}
			}
			
			// if TOS = $
			else if(tos.getSymbol() == lexeme){
				grammarSymbols.pop();
				return "ACCEPTED";
			}
			
			else{
				return "REJECTED because the current token is not terminal, nonterminal, and $";
			}
		}
		return "REJECTED due to absence of $";
	}

	private String repeat(String x, int n) {
		return new String(new char[n]).replace("\0", x);
	}
}