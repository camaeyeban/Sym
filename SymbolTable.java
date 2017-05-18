import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Set;
import java.util.Stack;

public class SymbolTable {

	private LinkedList<Hashtable<String, IdEntry>> idTables = new LinkedList<Hashtable<String, IdEntry>>();
	private int level = -1;
	private Stack<Integer> codeBlockLevel = new Stack<Integer> ();
	private int errorCount;
	private int foundHash = 0;
	private int currentOffset = 0;

	SymbolTable(int errorCount){
		this.errorCount = errorCount;
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
		IdEntry idEntry = new IdEntry(key, 1, value);
		this.idTables.get(1).put(key, idEntry);
	}
	

	public void enterBlock(){
		this.idTables.add(new Hashtable<String, IdEntry>());
		this.level += 1;
	}

	public void leaveBlock(int lineNumber){
		if(this.level > 0){
			this.idTables.removeLast();
			this.level -= 1;
		}
		else{
			System.out.println("Error at line " + lineNumber + ": Close brace '}' has no matching open brace '{'.");
			this.errorCount++;
		}
	}

	public IdEntry idLookup(String name, int blockLevel){
		if(blockLevel == 0 && idTables.size() > 0) {
			for(int i = 1; i < idTables.size(); i++) {
				IdEntry id = idLookup(name, i);

				if(id != null) return id;
			}
		}

		if(this.idTables.get(blockLevel) != null) {
			if(this.idTables.get(blockLevel).containsKey(name)){
				return this.idTables.get(blockLevel).get(name);
			}
		}
		
		return null;
	}

	public void install(String name, int blockLevel, String dataType){
		IdEntry idEntry;
		if(blockLevel > 0){
			idEntry = new IdEntry(name, blockLevel, dataType);
		}
		else{
			idEntry = new IdEntry(name, this.level, dataType);
		}
		
		this.currentOffset += idEntry.getSize();
		idEntry.setOffset(this.currentOffset);
		
		this.idTables.get(blockLevel).put(name, idEntry);
		// System.out.println("ADDED IDENTIFIER: "+name+"\tBlock Level: "+blockLevel+"\tData Type: "+dataType+"\tSize: "+idEntry.getSize()+"\tOffset: "+idEntry.getOffset());
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
	
	public int getErrorCount(){
		return this.errorCount;
	}
	
	public void create(AbstractSyntaxTreeNode ast){
		if(!codeBlockLevel.empty() && codeBlockLevel.peek() > ast.getDepth()){
        	this.leaveBlock(ast.getLineNumber());
        	codeBlockLevel.pop();
        }

		if(ast.getLexemeClass() != null) {
			// @TODO: move this code somewhere not in symbol table

			checkValidDataType(ast);

			if(ast.getLexemeClass().equals("DECLARATION")){
				IdEntry symbolToAdd = null;
				if(this.idLookup(ast.getChildren().get(0).getLexeme(), 1) == null){
					symbolToAdd = this.idLookup(ast.getChildren().get(0).getLexeme(), this.getLevel());
				}
				if(symbolToAdd == null) {
					this.install(ast.getChildren().get(0).getLexeme(), this.getLevel(), ast.getChildren().get(1).getLexeme());
					// this.printIdTables();
				}
				else {
					System.out.println("Error at line " + ast.getChildren().get(0).getLineNumber() + ": Redeclaration of identifier \"" + ast.getChildren().get(0).getLexeme() + "\" It already exists at block " + symbolToAdd.getBlockLevel() + ".");
					this.errorCount++;
				}
			}
			else if(ast.getLexemeClass().equals("FUNCTION_DECLARATION")){
				IdEntry symbolToAdd = null;
				if(this.idLookup(ast.getChildren().get(0).getLexeme(), 1) == null){
					symbolToAdd = this.idLookup(ast.getChildren().get(0).getLexeme(), this.getLevel());
				}
				if(symbolToAdd == null) {
					this.install(ast.getChildren().get(0).getLexeme(), this.getLevel(), ast.getChildren().get(1).getLexeme());
					// this.printIdTables();
				}
				else {
					System.out.println("Error at line " + ast.getChildren().get(0).getLineNumber() + ": Redeclaration of function identifier \"" + ast.getChildren().get(0).getLexeme() + "\" It already exists at block " + symbolToAdd.getBlockLevel() + ".");
					this.errorCount++;
				}
			}
			else if(ast.getLexemeClass().equals("CODE_BLOCK")){
				this.enterBlock();
				codeBlockLevel.push(ast.getDepth());
			}
			
		}

        for(int i = 0; i < ast.getChildren().size(); i++) {
            create(ast.getChildren().get(i));
        }
	}

	private boolean checkValidDataType(AbstractSyntaxTreeNode node) {
		String functionName = node.getLexeme();
		boolean isValid = true;

		// check if function name is reserved
		if(Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(functionName) != null) {
			ReservedFunctionsIdentifierDataTypePair functionInfo = Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(functionName);

			// check if assignment Operator
			if(node.getLexemeClass().equals("ASSIGNMENT")) {
				isValid = expect(node.getChild(0), DataTypes.IDENTIFIER, functionName, 0);
				isValid = isValid && expect(node.getChild(1), this.getDataType(node.getChild(0)), functionName, 1);
			}
			else {
				// get expected data types for each props
				String[] argsDataType = functionInfo.getArgsDataType();
				for(int i = 0; i < argsDataType.length; i++) {
					String expectedDataType = argsDataType[i];

					if(expectedDataType == DataTypes.ANY) {
						continue;
					}

					if(!expect(node.getChild(i), expectedDataType, functionName, i)) {
						isValid = false;
					}
				}
			}
		}
		// it might be user declared
		else {
			// @TODO
		}

		return isValid;
	}

	private String getDataType(AbstractSyntaxTreeNode node) {
		String lexeme = node.getLexeme();
		String lexemeClass = node.getLexemeClass();

		if(lexeme != null) {
			// check if data type
			if(DataTypes.LIST.contains(lexeme)) {
				return DataTypes.DATA_TYPE;
			}
			// check if function name is reserved
			else if(Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(lexeme) != null) {
				ReservedFunctionsIdentifierDataTypePair functionInfo = Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(lexeme);
				
				return functionInfo.getDataType();
			}
			// it might be user declared
			else if(this.idLookup(lexeme, 0) != null) {
				return this.idLookup(lexeme, 0).getDataType();
			}
			else if(lexemeClass.equals("String")) {
				return DataTypes.STR;
			}
			// int literal
			else if(lexemeClass.equals("INTEGER_LITERAL")) {
				return DataTypes.INT;
			}
			// float literal
			else if(lexemeClass.equals("FLOAT_LITERAL")) {
				return DataTypes.FLT;
			}
			// boolean literal
			else if(lexemeClass.equals("BOOLEAN_LITERAL")) {
				return DataTypes.BLN;
			}
		}

		if(node.getLexemeClass().equals("ANONYMOUS_FUNCTION_BLOCK")) {
			return DataTypes.FN;
		}

		if(node.getLexemeClass().equals("CODE_BLOCK")) {
			return DataTypes.COD;
		}
		
		// might be an uninitialized identifier
		return DataTypes.IDENTIFIER_UNINITIALIZED;
	}
	
	private boolean expect(AbstractSyntaxTreeNode node, String expectedDataType, String functionName, int i) {
		boolean result;

		if(expectedDataType.equals(DataTypes.IDENTIFIER)) {
			result = this.idLookup(node.getLexeme(), 0) != null;
		}
		else if(expectedDataType.equals(DataTypes.ANY)) {
			// @TODO: check for null/undeclared variables	
			result = true;
		}
		else {
			result = this.getDataType(node).equals(expectedDataType);
		}

		if(!result) {
			System.out.println("Error at line " + node.getLineNumber() + ": Expected " + expectedDataType + " for function " + functionName + " argument " + (i + 1) + ", got " + this.getDataType(node) + ".");
			this.errorCount++;
		}

		return result;
	}
}