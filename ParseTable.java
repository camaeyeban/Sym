public class ParseTable {
	String[] terminals = {"FUNCTION_DELIMITER_LEFT", "FUNCTION_DELIMITER_RIGHT", "CODE_DELIMITER_LEFT", "CODE_DELIMITER_RIGHT", "STRING_DELIMITER", "IDENTIFIER", "DATA_TYPE", "DATA_TYPE_DELIMITER", "STATEMENT_DELIMITER", "BOOLEAN_LITERAL", "INTEGER_LITERAL", "FLOAT_LITERAL", "STRING_LITERAL", "ARGUMENT_DELIMITER", "END_OF_FILE_MARKER"};
    String[] nonterminals = {"Statements", "Statement", "Statement_Without_Delimiter", "Statement_Without_Delimiter'", "Arguments", "More_Arguments", "More_Arguments'", "Data", "Data'", "Variable_DataType_Pair'", "String", "Anonymous_Function_Block", "Code_Block"};
	String[] production = {
		"Statements -> Statement Statements",
		"Statements -> <EPSILON>",
		"Statement -> Statement_Without_Delimiter<STATEMENT_DELIMITER>",
		"Statement_Without_Delimiter -> <IDENTIFIER>Statement_Without_Delimiter'",
		"Statement_Without_Delimiter' -> <FUNCTION_DELIMITER_LEFT>Arguments<FUNCTION_DELIMITER_RIGHT>",
		
		"Arguments -> More_Arguments",
		"Arguments -> <EPSILON>",
		"More_Arguments -> Data More_Arguments'",
		"More_Arguments' -> <ARGUMENT_DELIMITER>More_Arguments",
		"More_Arguments' -> <EPSILON>",
		
		"Data -> <IDENTIFIER> Data'",
		"Data -> <DATA_TYPE>",
		"Data -> <BOOLEAN_LITERAL>",
		"Data -> <INTEGER_LITERAL>",
		"Data -> <FLOAT_LITERAL>",
		
		"Data -> String",
		"Data -> Anonymous_Function_Block",
		"Data -> Code_Block",
		"Data' -> Variable_DataType_Pair'",
		"Data' -> Statement_Without_Delimiter'",
		
		"Data' -> <EPSILON>",
		"Variable_DataType_Pair' -> <DATA_TYPE_DELIMITER><DATA_TYPE>",
		"String -> <STRING_DELIMITER><STRING_LITERAL><STRING_DELIMITER>",
		"Anonymous_Function_Block -> <FUNCTION_DELIMITER_LEFT>Arguments<FUNCTION_DELIMITER_RIGHT>Code_Block",
		"Code_Block -> <CODE_DELIMITER_LEFT>Statements<CODE_DELIMITER_RIGHT>"
	};
	int[][] pt = new int[][]{
		{ -1,  -1,  -1,   1,  -1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   1 },
		{ -1,  -1,  -1,  -1,  -1,   2,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1 },
		{ -1,  -1,  -1,  -1,  -1,   3,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1 },
		{  4,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1 },
		{  5,   6,   5,  -1,   5,   5,   5,  -1,  -1,   5,   5,   5,  -1,  -1,  -1 },
		{  7,  -1,   7,  -1,   7,   7,   7,  -1,  -1,   7,   7,   7,  -1,  -1,  -1 },
		{ -1,   9,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   8,  -1 },
		{  16, -1,   17, -1,   15,  10,  11, -1,  -1,   12,  13,  14, -1,  -1,  -1 },
		{  19,  20, -1,  -1,  -1,  -1,  -1,   18, -1,  -1,  -1,  -1,  -1,   20, -1 },
		{ -1,  -1,  -1,  -1,  -1,  -1,  -1,   21, -1,  -1,  -1,  -1,  -1,  -1,  -1 },
		{ -1,  -1,  -1,  -1,   22, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1 },
		{  23, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1 },
		{ -1,  -1,   24, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1 }
	};
	
    public ParseTable() { }
	
	public String getProduction(int row, int col){
		int index = pt[row][col];
		if(index == -1){
			return "ERROR";
		}
		return production[index];
	}
}