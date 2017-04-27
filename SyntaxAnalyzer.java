import java.util.ArrayList;
import java.util.Stack;

public class SyntaxAnalyzer {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private SymbolTable symbolTable;
	private TreeNode concreteTree = new TreeNode("", "", 0, -1);
	private AbstractSyntaxTreeNode ast;
	private int errorCount = 0;
	
    public SyntaxAnalyzer(ArrayList<Token> tokens) {
		this.tokens = tokens;
		symbolTable = new SymbolTable(this.errorCount);
	}
	
	public int getErrorCount(){
		return this.errorCount;
	}
	
	public AbstractSyntaxTreeNode analyze(){
		System.out.println("\n------------------------------ SYNTAX ANALYZER ----------------------------\n");
		
		
		System.out.println("\n ~ ~ ~ ~ ~ --------------------- RECOGNIZER -------------------- ~ ~ ~ ~ ~ \n");
		Recognizer recognizer = new Recognizer(this.tokens, this.concreteTree);
		try {
			String result = recognizer.recognize();
        System.out.println("\n ~ ~ ~ ~ ~ ----------------- END OF RECOGNIZER ----------------- ~ ~ ~ ~ ~ \n");
			
		}
		catch(Exception e) {
			System.out.println(e);

			return null;
		}
		
		System.out.println("\n ~ ~ ~ ~ ~ -------------------- CONCRETE TREE ------------------ ~ ~ ~ ~ ~ \n");
		Stack<Integer> counter = new Stack<Integer> ();
		counter.push(0);
		TreeNode.printTree(concreteTree, counter);
        System.out.println("\n ~ ~ ~ ~ ~ ---------------- END OF CONCRETE TREE --------------- ~ ~ ~ ~ ~ \n");

		System.out.println("\n ~ ~ ~ ~ ~ ------------------------- AST ----------------------- ~ ~ ~ ~ ~ \n");
		ast = new AbstractSyntaxTreeNode(concreteTree);
		ast.printTree();
        System.out.println("\n ~ ~ ~ ~ ~ --------------------- END OF AST -------------------- ~ ~ ~ ~ ~ \n");
		
		System.out.println("\n ~ ~ ~ ~ ~ -------------------- SYMBOL TABLE ------------------- ~ ~ ~ ~ ~ \n");
		symbolTable.create(ast);
        System.out.println("\n ~ ~ ~ ~ ~ ---------------- END OF SYMBOL TABLE ---------------- ~ ~ ~ ~ ~ \n");
		this.errorCount += symbolTable.getErrorCount();

		
        System.out.println("\n-------------------------- END OF SYNTAX ANALYZER -------------------------\n");

		return this.ast;
	}
}