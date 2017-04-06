import java.util.LinkedList;

public class AbstractSyntaxTreeNode {
    private String lexeme;
	private String lexemeClass;
    private int depth;
    private AbstractSyntaxTreeNode parent;
    private LinkedList<AbstractSyntaxTreeNode> children = new LinkedList<AbstractSyntaxTreeNode>();

    public AbstractSyntaxTreeNode(TreeNode tree) {
        // root
        this.lexeme = null;
        this.lexemeClass = "Start";
        this.depth = 0;
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

        switch(tree.getLexemeClass()) {
            case "Statements": {
                break;
            }
            case "Statement": {
                this.lexeme = tree.getChild(0).getChild(0).getLexeme();

                if(Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(this.lexeme) != null) {
                    this.lexemeClass = Meta.RESERVED_FUNCTIONS_LOOKUP_TABLE.get(this.lexeme);
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
                this.lexeme = tree.getChild(0).getLexeme();
                this.lexemeClass = tree.getChild(0).getLexemeClass();

                // @TODO: catch all cases
                break;
            }
            // @TODO: other productions
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

    public void printTree() {
        System.out.println(repeat("  ", this.getDepth()) + this.getLexemeClass() + " (" + this.getLexeme() + ")");

        for(int i = 0; i < children.size(); i++) {
            children.get(i).printTree();
        }
    }

    private static String repeat(String x, int n) {
		return new String(new char[n]).replace("\0", x);
	}
}