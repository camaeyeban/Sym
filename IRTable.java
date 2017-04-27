import java.util.ArrayList;

public class IRTable {
    ArrayList<IRrow> table = new ArrayList<IRrow>();

    public String add(AbstractSyntaxTreeNode node) {
        String result = "";

        if(node.getLexemeClass().equals("ASSIGNMENT")) {
            result = node.getChild(0).getLexeme();

            IRrowAssignment row = new IRrowAssignment(node.getLexeme(), this.add(node.getChild(1)), null, result);

            table.add(row);
        }
        else if(node.getLexemeClass().equals("ADD") || node.getLexemeClass().equals("SUBTRACT") || node.getLexemeClass().equals("MULTIPLY") || node.getLexemeClass().equals("DIVIDE") || node.getLexemeClass().equals("MODULO")) {
            IRrowAssignment row = new IRrowAssignment(node.getLexeme(), this.add(node.getChild(0)), this.add(node.getChild(1)), null);

            result = row.getResult();

            table.add(row);
        }
        else if(
            node.getLexemeClass().equals("INCREMENT") ||
            node.getLexemeClass().equals("DECREMENT") ||
            node.getLexemeClass().equals("NOT")
        ) {
            IRrowAssignment row = new IRrowAssignment(node.getLexeme(), this.add(node.getChild(0)), null, null);

            result = row.getResult();

            table.add(row);
        }
        else if(node.getLexemeClass().equals("INTEGER_LITERAL")) {
            result = node.getLexeme();
        }
        else if(node.getLexemeClass().equals("FLOAT_LITERAL")) {
            result = node.getLexeme();
        }
        else if(node.getLexemeClass().equals("IDENTIFIER")) {
            result = node.getLexeme();
        }
        else if(
            node.getLexemeClass().equals("PRINT") ||
            node.getLexemeClass().equals("DECLARATION") ||
            node.getLexemeClass().equals("READ_INPUT") ||
            node.getLexemeClass().equals("RETURN")
        ) {
            ArrayList<String> params = new ArrayList<String>();

            for(int i = 0; i < node.getChildren().size(); i++){
                params.add(node.getChild(i).getLexeme());
                
                IRrowParameter row = new IRrowParameter(node.getChild(i).getLexeme());
                table.add(row);
            }

            IRrowProcedure row = new IRrowProcedure(node.getLexeme(), null, params);

            table.add(row);
        }
        else {
            for(int i = 0; i < node.getChildren().size(); i++) {
                this.add(node.getChild(i));
            }
        }

        return result;
    }

    public void printTable() {
        for(IRrow row: this.table) {
            System.out.println(row);
        }
    }

    public ArrayList<IRrow> getTable() {
        return this.table;
    }
}