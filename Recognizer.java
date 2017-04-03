import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

// @TODO: store grammar tree in a data structure

public class Recognizer {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private Stack<GrammarSymbol> grammarSymbols = new Stack<GrammarSymbol>();
	private ParseTable parseTable = new ParseTable();
	private SymbolTable symbolTable = new SymbolTable();
	private TreeNode concreteTree;
	
    public Recognizer(ArrayList<Token> tokens, TreeNode concreteTree) {
		this.tokens = tokens;
		this.tokens.add(new Token());
		this.grammarSymbols.push(new GrammarSymbol("$", 0));
		this.grammarSymbols.push(new GrammarSymbol("Statements", 0));
		this.concreteTree = concreteTree;
	}
	
	public String recognize(){
		TreeNode currentNode = this.concreteTree;
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
					
					// creating new node then adding it to the concrete tree
					TreeNode child = new TreeNode(lexeme, g.getSymbol(), g.getIndent()+1);
					while(child.getDepth()-1 < currentNode.getDepth()){
						currentNode = currentNode.getParent();
					}
					child.setParent(currentNode);
					currentNode.addChild(child);
					
					// updating pointer
					currentNode = child;
					
					if(g.getSymbol().equals("CODE_DELIMITER_LEFT")){
						symbolTable.enterBlock();
					}
					else if(g.getSymbol().equals("CODE_DELIMITER_RIGHT")){
						symbolTable.leaveBlock();
					}
					else if(g.getSymbol().equals("IDENTIFIER")){
						if( (! Arrays.asList(Meta.RESERVED_FUNCTION_NAMES).contains(lexeme)) &&
						(! Arrays.asList(Meta.DELIMITERS).contains(lexeme)) ){
							IdEntry symbolToAdd = symbolTable.idLookup(lexeme, 0);

							// check if identifier doesn't exist in the symbol table
							if(symbolToAdd == null) {
								symbolTable.install(lexeme, symbolTable.getLevel());
								System.out.println("ADDED IDENTIFIER: "+lexeme+"\tBlock Level: "+symbolTable.getLevel()+"\tCurrent Block's Element Count: "+symbolTable.getBlockElementCount(symbolTable.getLevel()));
								symbolTable.printIdTables();
							}
							else {
								System.out.println("FAILED TO ADD IDENTIFIER: \"" + lexeme + "\" It already exists at block " + symbolToAdd.getBlockLevel() + ".");
							}
						}
					}
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

					// creating new node then adding it to the concrete tree
					TreeNode child = new TreeNode(lexeme, g.getSymbol(), g.getIndent()+1);
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
				return "REJECTED because the current token is not terminal, nonterminal, and $";
			}
		}
		return "REJECTED due to absence of $";
	}

	private String repeat(String x, int n) {
		return new String(new char[n]).replace("\0", x);
	}
}