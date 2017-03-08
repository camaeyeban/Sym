import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Recognizer {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private Stack<String> grammarSymbols = new Stack<String>();
	private ParseTable parseTable = new ParseTable();
	
    public Recognizer(ArrayList<Token> tokens) {
		this.tokens = tokens;
		this.tokens.add(new Token());
		this.grammarSymbols.push("$");
		this.grammarSymbols.push("Statements");
	}
	
	public String recognize(){
		for(int i=0; i<this.tokens.size(); ) {
			String tokenName = tokens.get(i).getType();
			String lexeme = tokens.get(i).getLexeme();
			String tos = grammarSymbols.peek(); // tos = top of stack
			
			System.out.println("\nTOS: "+tos+"\tToken: "+lexeme+"\tToken Type: "+tokenName);
			
			// if TOS = epsilon
			if(tos.equals("EPSILON")){
				grammarSymbols.pop();
			}
			
			// if TOS = terminal
			else if(Arrays.asList(parseTable.terminals).contains(tos)){
				if(tos.equals(tokenName)){
					grammarSymbols.pop();
					i++;
				}
				else{
					return "REJECTED due to mismatch of current token and top of stack";
				}
			}
			// if TOS = nonterminal
			else if(Arrays.asList(parseTable.nonterminals).contains(tos)){
				int row = Arrays.asList(parseTable.nonterminals).indexOf(tos);
				int col = Arrays.asList(parseTable.terminals).indexOf(tokenName);
				String production = parseTable.getProduction(row, col);
				
				System.out.println("Production: "+production);
				if(production == "ERROR"){
					return "REJECTED due to absence of production rule in the corresponding cell of parse table";
				}
				else{
					grammarSymbols.pop();
					String entries[] = production.split("<|>|-| ", -1);
					
					for(int j=entries.length-1; j>0; j--){
						if(entries[j].length() > 0){
							grammarSymbols.push(entries[j]);
						}
					}
				}
			}
			
			// if TOS = $
			else if(tos == lexeme){
				grammarSymbols.pop();
				return "ACCEPTED";
			}
			
			else{
				return "REJECTED because the current token is not terminal, nonterminal, and $";
			}
		}
		return "REJECTED due to absence of $";
	}
}