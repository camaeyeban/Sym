import java.util.ArrayList;

public class IRTable {
    ArrayList<IRrow> table = new ArrayList<IRrow>();

    public String add(AbstractSyntaxTreeNode node) {
        String result = "";

        if(node.getLexemeClass().equals("ASSIGNMENT")) {
            result = node.getChild(0).getLexeme();

            IRrow row = new IRrow(node.getLexeme(), this.add(node.getChild(1)), null, result);

            table.add(row);
        }
        else if(node.getLexemeClass().equals("ADD") || node.getLexemeClass().equals("SUBTRACT") || node.getLexemeClass().equals("MULTIPLY") || node.getLexemeClass().equals("DIVIDE") || node.getLexemeClass().equals("MODULO")) {
            IRrow row = new IRrow(node.getLexeme(), this.add(node.getChild(0)), this.add(node.getChild(1)), null);

            result = row.getResult();

            table.add(row);
        }
        else if(node.getLexemeClass().equals("INTEGER_LITERAL")) {
            result = node.getLexeme();
        }
        else {
            for(int i = 0; i < node.getChildren().size(); i++) {
                this.add(node.getChild(i));
            }
        }

        return result;
    }

    public ArrayList<IRrow> getTable() {
        return this.table;
    }
}