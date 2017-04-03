import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class TreeNode {
	private String lexeme;
	private String lexemeClass;
	private int depth;
	private TreeNode parent;
	private LinkedList<TreeNode> children;

	TreeNode(String lexeme, String lexemeClass, int depth){
		this.lexeme = lexeme;
		this.lexemeClass = lexemeClass;
		this.depth = depth;
		this.parent = null;
		this.children = new LinkedList<TreeNode> ();
	}
	
	public int getDepth(){
		return this.depth;
	}
	
	public String getLexeme(){
		return this.lexeme;
	}
	
	public String getLexemeClass(){
		return this.lexemeClass;
	}
	
	public TreeNode getParent(){
		return this.parent;
	}
	
	public LinkedList<TreeNode> getChildren(){
		return this.children;
	}
	
	public void setParent(TreeNode parent){
		this.parent = parent;
	}
	
	public void addChild(TreeNode child){
		this.children.add(child);
	}
	
	public static void printTree(TreeNode ptr, Stack<Integer> counter){
		while(ptr.getChildren().size() == 0 || (counter.peek() >= ptr.getChildren().size())){
			ptr = ptr.getParent();
			counter.pop();
			
			if(ptr.getParent() == null){
				return;
			}
		}
		
		int i = counter.pop();
		counter.push(i+1);
		ptr = ptr.getChildren().get(i);
		counter.push(0);
		System.out.println(repeat("  ", ptr.getDepth()) + ptr.getLexemeClass());
		
		printTree(ptr, counter);
	}
	
	private static String repeat(String x, int n) {
		return new String(new char[n]).replace("\0", x);
	}
}