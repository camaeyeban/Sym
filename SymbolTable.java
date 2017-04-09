import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Set;
import java.util.Stack;

public class SymbolTable {

	private LinkedList<Hashtable<String, IdEntry>> idTables = new LinkedList<Hashtable<String, IdEntry>>();
	private int level = -1;
	private Stack<Integer> codeBlockLevel = new Stack<Integer> ();
	private int foundHash = 0;

	SymbolTable(){
		init();
	}

	public void init(){
		enterBlock();			// index 0: hash table

		enterBlock();			// index 1: hash table for reserved symbols
		initReservedSymbols();
		
		enterBlock();			// index 2: hash table for global variables
	}

	
	public int getLevel(){
		return this.level;
	}
	
	public int getBlockElementCount(int blockLevel){
		return idTables.get(blockLevel).size();
	}
	
	public void initReservedSymbols(){
		installReservedSymbol("@", "KEYSYMBOL");
		installReservedSymbol("->", "KEYSYMBOL");
		installReservedSymbol(":", "KEYSYMBOL");
		installReservedSymbol("+", "KEYSYMBOL");
		installReservedSymbol("-", "KEYSYMBOL");
		installReservedSymbol("*", "KEYSYMBOL");
		installReservedSymbol("/", "KEYSYMBOL");
		installReservedSymbol("%", "KEYSYMBOL");
		installReservedSymbol("=", "KEYSYMBOL");
		installReservedSymbol("==", "KEYSYMBOL");
		installReservedSymbol("<", "KEYSYMBOL");
		installReservedSymbol("<=", "KEYSYMBOL");
		installReservedSymbol(">", "KEYSYMBOL");
		installReservedSymbol(">=", "KEYSYMBOL");
		installReservedSymbol("!=", "KEYSYMBOL");
		installReservedSymbol("&&", "KEYSYMBOL");
		installReservedSymbol("||", "KEYSYMBOL");
		installReservedSymbol("#", "KEYSYMBOL");
		installReservedSymbol("?", "KEYSYMBOL");
		installReservedSymbol("~", "KEYSYMBOL");
		installReservedSymbol("!", "KEYSYMBOL");
		installReservedSymbol("++", "KEYSYMBOL");
		installReservedSymbol("--", "KEYSYMBOL");
		installReservedSymbol("<-", "KEYSYMBOL");
	}
	
	public void installReservedSymbol(String key, String value){
		IdEntry idEntry = new IdEntry(key, 1);
		idEntry.setDataType(value);
		this.idTables.get(1).put(key, idEntry);
	}
	

	public void enterBlock(){
		this.idTables.add(new Hashtable<String, IdEntry>());
		this.level += 1;
	}


	public void leaveBlock(){
		if(this.level > 0){
			this.idTables.removeLast();
			this.level -= 1;
		}
		else{
		System.out.println("Matching Error: Closing brace '}' has no matching open brace '{'.");
		}
	}


	public IdEntry idLookup(String name, int blockLevel){
		if(blockLevel == 0 && idTables.size() > 0) {
			for(int i = 1; i < idTables.size(); i++) {
				IdEntry id = idLookup(name, i);

				if(id != null) return id;
			}
		}

		if(this.idTables.get(blockLevel).containsKey(name)){
			return this.idTables.get(blockLevel).get(name);
		}
		return null;
	}


	public void install(String name, int blockLevel, String dataType){
		IdEntry idEntry;
		if(blockLevel > 0){
			idEntry = new IdEntry(name, blockLevel);
		}
		else{
			idEntry = new IdEntry(name, this.level);
		}
		idEntry.setDataType(dataType);
		this.idTables.get(blockLevel).put(name, idEntry);
	}

	public void printIdTables(){
		for(int i=1; i<this.idTables.size(); i++){
			System.out.println("\n--------- BLOCK LEVEL: "+i);

			Set<String> keys = idTables.get(i).keySet();
	        for(String key: keys){
	            System.out.println("\t"+idTables.get(i).get(key).getName());
	        }
		}
	}

	public void create(AbstractSyntaxTreeNode ast){
		if(!codeBlockLevel.empty() && codeBlockLevel.peek() > ast.getDepth()){
        	this.leaveBlock();
        	codeBlockLevel.pop();
        }

        if(ast.getLexeme() != null && ast.getLexeme().equals("#")){
        	IdEntry symbolToAdd = null;
        	if(this.idLookup(ast.getChildren().get(0).getLexeme(), 1) == null){
        		symbolToAdd = this.idLookup(ast.getChildren().get(0).getLexeme(), this.getLevel());
        	}
        	if(symbolToAdd == null) {
				this.install(ast.getChildren().get(0).getLexeme(), this.getLevel(), ast.getChildren().get(1).getLexeme());
				System.out.println("ADDED IDENTIFIER: "+ast.getChildren().get(0).getLexeme()+"\tBlock Level: "+this.getLevel()+"\tData Type: "+ast.getChildren().get(1).getLexeme());
				this.printIdTables();
			}
			else {
				System.out.println("FAILED TO ADD IDENTIFIER: \"" + ast.getChildren().get(0).getLexeme() + "\" It already exists at block " + symbolToAdd.getBlockLevel() + ".");
			}
        }
        else if(ast.getLexeme() != null && ast.getLexeme().equals("@")){
        	IdEntry symbolToAdd = null;
        	if(this.idLookup(ast.getChildren().get(0).getLexeme(), 1) == null){
        		symbolToAdd = this.idLookup(ast.getChildren().get(0).getLexeme(), this.getLevel());
        	}
        	if(symbolToAdd == null) {
				this.install(ast.getChildren().get(0).getLexeme(), this.getLevel(), ast.getChildren().get(1).getLexeme());
				System.out.println("ADDED FUNCTION: "+ast.getChildren().get(0).getLexeme()+"\tBlock Level: "+this.getLevel()+"\tData Type: "+ast.getChildren().get(1).getLexeme());
				this.printIdTables();
			}
			else {
				System.out.println("FAILED TO ADD IDENTIFIER: \"" + ast.getChildren().get(0).getLexeme() + "\" It already exists at block " + symbolToAdd.getBlockLevel() + ".");
			}
        }
        else if(ast.getLexemeClass() != null && ast.getLexemeClass().equals("CODE_BLOCK")){
        	this.enterBlock();
        	codeBlockLevel.push(ast.getDepth());
        }

        for(int i = 0; i < ast.getChildren().size(); i++) {
            create(ast.getChildren().get(i));
        }
	}
}