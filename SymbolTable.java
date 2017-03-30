import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Set;

public class SymbolTable {

	private LinkedList<Hashtable<String, IdEntry>> idTables = new LinkedList<Hashtable<String, IdEntry>>();
	private int level = -1;              

	SymbolTable(){
		init();
	}

	public void init(){
		enterBlock();			// index 0: hash table

		enterBlock();			// index 1: hash table for reserved symbols
		initReservedSymbols();
		
		enterBlock();			// index 2: hash table for global variables
		//enterBlock();			// index 1: hash table for reserved keywords
        //initReservedWords();
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
		if(this.idTables.get(blockLevel).containsKey(name)){
			return this.idTables.get(blockLevel).get(name);
		}
		return null;
	}


	public void install(String name, int blockLevel){
		IdEntry idEntry;
		if(blockLevel > 0){
			idEntry = new IdEntry(name, blockLevel);
		}
		else{
			idEntry = new IdEntry(name, this.level);
		}
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
}