import java.util.LinkedList;

public class AbstractSyntaxTreeNode {
    private String lexeme;
	private String lexemeClass;
    private int depth;
	private int lineNumber;
    private AbstractSyntaxTreeNode parent;
    private LinkedList<AbstractSyntaxTreeNode> children = new LinkedList<AbstractSyntaxTreeNode>();

    public AbstractSyntaxTreeNode(TreeNode tree) {
        // root
        this.lexeme = null;
        this.lexemeClass = "Start";
        this.depth = 0;
		this.lineNumber = -1;
        this.parent = null;

        TreeNode p = tree.getChild(0);

        while(p.getChildren().size() > 0) {
            children.add(new AbstractSyntaxTreeNode(p.getChild(0), this));
            p = p.getChild(1);
        }
    }

    public AbstractSyntaxTreeNode(TreeNode tree, AbstractSyntaxTreeNode parent) {
        this.init(tree, parent);
    }

    public void init(TreeNode tree, AbstractSyntaxTreeNode parent) {
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
		this.lineNumber = tree.getLineNumber();

        switch(tree.getLexemeClass()) {
            case "Statements": {
                TreeNode p = tree;

                while(p.getChildren().size() > 0) {
                    children.add(new AbstractSyntaxTreeNode(p.getChild(0), this));
                    p = p.getChild(1);
                }

                break;
            }
            case "Statement": {
                this.lexeme = tree.getChild(0).getChild(0).getLexeme();

                if(Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(this.lexeme) != null) {
                    this.lexemeClass = Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(this.lexeme).getIdentifier();
                }
                else {
                    this.lexemeClass = "FUNCTION_CALL";
                }

                TreeNode p = tree.getChild(0).getChild(1).getChild(1).getChild(0);

                while(p.getChildren().size() > 0) {
                    children.add(new AbstractSyntaxTreeNode(p, this));

                    if(p.getChild(1).getChildren().size() > 0) {
                        p = p.getChild(1).getChild(1);
                    }
                    else {
                        break;
                    }
                }

                break;
            }
            case "More_Arguments": {
                this.init(tree.getChild(0), parent);

                break;
            }
            case "Data": {
                if(tree.getChildren().size() > 1 && tree.getChild(1).getChildren().size() > 0) {
                    TreeNode lookahead = tree.getChild(1).getChild(0);
                    
                    if(lookahead.getLexemeClass().equals("Variable_DataType_Pair'")) {
                        this.lexemeClass = "DATA_TYPE_PAIR_NODE";

                        this.children.add(new AbstractSyntaxTreeNode(tree.getChild(0) ,this));
                        this.children.add(new AbstractSyntaxTreeNode(lookahead.getChild(1) ,this));
                    }
                    else if(lookahead.getLexemeClass().equals("Statement_Without_Delimiter'")) {
                        this.lexeme = tree.getChild(0).getLexeme();

                        if(Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(this.lexeme) != null) {
                            this.lexemeClass = Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(this.lexeme).getIdentifier();
                        }
                        else {
                            this.lexemeClass = "FUNCTION_CALL";
                        }

                        TreeNode p = tree.getChild(1).getChild(0).getChild(1).getChild(0);

                        while(p.getChildren().size() > 0) {
                            children.add(new AbstractSyntaxTreeNode(p, this));

                            if(p.getChild(1).getChildren().size() > 0) {
                                p = p.getChild(1).getChild(1);
                            }
                            else {
                                break;
                            }
                        }
                    }
                }
                else {
                    this.init(tree.getChild(0), parent);
                }

                break;
            }
            case "IDENTIFIER": {
                this.lexeme = tree.getLexeme();
                this.lexemeClass = tree.getLexemeClass();

                break;
            }
            case "DATA_TYPE": {
                this.lexeme = tree.getLexeme();
                this.lexemeClass = tree.getLexemeClass();
                
                break;
            }
            case "Anonymous_Function_Block": {
                this.lexeme = null;
                this.lexemeClass = tree.getLexemeClass().toUpperCase();

                // arguments
                children.add(new AbstractSyntaxTreeNode(tree.getChild(0).getChild(1).getChild(0), this));

                // code block
                children.add(new AbstractSyntaxTreeNode(tree.getChild(1), this));

                break;
            }
            case "Code_Block": {
                this.lexemeClass = "CODE_BLOCK";
                
                this.init(tree.getChild(1), parent);

                break;
            }
            case "String": {
                this.lexemeClass = tree.getLexemeClass();
                this.lexeme = "\"" + tree.getChild(1).getLexeme() + "\"";

                break;
            }
            case "INTEGER_LITERAL": {
            }
            case "FLOAT_LITERAL": {
            }
            case "BOOLEAN_LITERAL": {
                this.lexemeClass = tree.getLexemeClass();
                this.lexeme = tree.getLexeme();

                break;
            }
            default: {
                System.out.println("!: " + tree.getLexemeClass());
            }
        }
    }

    public String getLexeme(){
		return this.lexeme;
	}
	
	public String getLexemeClass(){
		return this.lexemeClass;
	}

    public int getDepth() {
        return this.depth;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }
	
    public LinkedList<AbstractSyntaxTreeNode> getChildren(){
        return this.children;
    }

    public AbstractSyntaxTreeNode getChild(int i) {
        return this.children.get(i);
    }

    public void printTree() {
        // System.out.println(repeat("  ", this.getDepth()) + this.getLexemeClass() + " (" + this.getLexeme() + ")");
        System.out.println(repeat("  ", this.getDepth()) + (this.getLexeme() != null ? (this.getLexeme() + " (" + this.getLexemeClass() + ")" + " at line " + this.getLineNumber()) : this.getLexemeClass()));

        for(int i = 0; i < children.size(); i++) {
            children.get(i).printTree();
        }
    }

    private static String repeat(String x, int n) {
		return new String(new char[n]).replace("\0", x);
	}
}