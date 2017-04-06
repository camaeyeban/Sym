import java.util.ArrayList;
import java.util.Stack;

public class SyntaxAnalyzer {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private SymbolTable symbolTable = new SymbolTable();
	private TreeNode concreteTree = new TreeNode("", "", 0);
	private AbstractSyntaxTreeNode ast;
	
    public SyntaxAnalyzer(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public void analyze(){
		System.out.println("\n------------------------------ SYNTAX ANALYZER ----------------------------\n");
		
		
		System.out.println("\n ~ ~ ~ ~ ~ -------------------- SYMBOL TABLE ------------------- ~ ~ ~ ~ ~ \n");
		Recognizer recognizer = new Recognizer(this.tokens, this.concreteTree);
		String result = recognizer.recognize();
        System.out.println("\n ~ ~ ~ ~ ~ ---------------- END OF SYMBOL TABLE ---------------- ~ ~ ~ ~ ~ \n");
		
		System.out.println("\n ~ ~ ~ ~ ~ --------------------- RECOGNIZER -------------------- ~ ~ ~ ~ ~ \n");
        System.out.println("Code is " + result + ".");
        System.out.println("\n ~ ~ ~ ~ ~ ----------------- END OF RECOGNIZER ----------------- ~ ~ ~ ~ ~ \n");
	
		Stack<Integer> counter = new Stack<Integer> ();
		counter.push(0);
		
		System.out.println("\n ~ ~ ~ ~ ~ -------------------- CONCRETE TREE ------------------ ~ ~ ~ ~ ~ \n");
		TreeNode.printTree(concreteTree, counter);
        System.out.println("\n ~ ~ ~ ~ ~ ---------------- END OF CONCRETE TREE --------------- ~ ~ ~ ~ ~ \n");

		System.out.println("\n ~ ~ ~ ~ ~ -------------------- AST ------------------ ~ ~ ~ ~ ~ \n");
		ast = new AbstractSyntaxTreeNode(concreteTree);
		ast.printTree();
        System.out.println("\n ~ ~ ~ ~ ~ ---------------- END OF AST --------------- ~ ~ ~ ~ ~ \n");
		
		System.out.println("\n ~ ~ ~ ~ ~ -------------------- SYMBOL TABLE ------------------- ~ ~ ~ ~ ~ \n");
		symbolTable.create(ast);
        System.out.println("\n ~ ~ ~ ~ ~ ---------------- END OF SYMBOL TABLE ---------------- ~ ~ ~ ~ ~ \n");
		

		
        System.out.println("\n-------------------------- END OF SYNTAX ANALYZER -------------------------\n");
	}
}