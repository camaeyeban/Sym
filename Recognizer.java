import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Recognizer {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private Stack<GrammarSymbol> grammarSymbols = new Stack<GrammarSymbol>();
	private ParseTable parseTable = new ParseTable();
	private TreeNode concreteTree;
	
    public Recognizer(ArrayList<Token> tokens, TreeNode concreteTree) {
		this.tokens = tokens;
		this.grammarSymbols.push(new GrammarSymbol("$", 0));
		this.grammarSymbols.push(new GrammarSymbol("Statements", 0));
		this.concreteTree = concreteTree;
	}
	
	public String recognize() throws Exception {
		TreeNode currentNode = this.concreteTree;
		for(int i=0; i<this.tokens.size(); ) {
			String tokenName = tokens.get(i).getType();
			String lexeme = tokens.get(i).getLexeme();
			int lineNumber = tokens.get(i).getLineNumber();
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
					
					// creating new node then adding it to the concrete tree
					TreeNode child = new TreeNode(lexeme, g.getSymbol(), g.getIndent()+1, lineNumber);
					while(child.getDepth()-1 < currentNode.getDepth()){
						currentNode = currentNode.getParent();
					}
					child.setParent(currentNode);
					currentNode.addChild(child);
					
					// updating pointer
					currentNode = child;
				}
				else{
					throw new Exception("REJECTED due to mismatch of current token and top of stack");
				}
			}
			
			// if TOS = nonterminal
			else if(Arrays.asList(parseTable.nonterminals).contains(tos.getSymbol())){
				int row = Arrays.asList(parseTable.nonterminals).indexOf(tos.getSymbol());
				int col = Arrays.asList(parseTable.terminals).indexOf(tokenName);
				String production = parseTable.getProduction(row, col);

				if(production == "ERROR"){
					throw new Exception("REJECTED due to absence of production rule in the corresponding cell of parse table");
				}
				else{
					GrammarSymbol g = grammarSymbols.pop();
					String entries[] = production.split("<|>|-| ", -1);

					// creating new node then adding it to the concrete tree
					TreeNode child = new TreeNode(lexeme, g.getSymbol(), g.getIndent()+1, lineNumber);
					while(child.getDepth()-1 < currentNode.getDepth()){
						currentNode = currentNode.getParent();
					}
					child.setParent(currentNode);
					currentNode.addChild(child);
					
					// updating terminal
					currentNode = child;
					
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
				throw new Exception("REJECTED because the current token is not terminal, nonterminal, and $");
			}
		}
		throw new Exception("REJECTED due to absence of $");
	}
}