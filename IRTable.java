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
        else if(node.getLexemeClass().equals("BOOLEAN_LITERAL")) {
            result = node.getLexeme();
        }
        else if(node.getLexemeClass().equals("IDENTIFIER")) {
            result = node.getLexeme();
        }
        else if(
            node.getLexemeClass().equals("GREATER THAN") ||
            node.getLexemeClass().equals("LESS THAN") ||
            node.getLexemeClass().equals("GREATER THAN OR EQUAL TO") ||
            node.getLexemeClass().equals("LESS THAN OR EQUAL TO") ||
            node.getLexemeClass().equals("NOT EQUAL TO") ||
            node.getLexemeClass().equals("EQUALS")
        ) {
            result = this.add(node.getChild(0)) + node.getLexeme() + this.add(node.getChild(1));
        }
        else if(node.getLexemeClass().equals("OR")) {
            IRrowControl row = new IRrowControl(this.add(node.getChild(0)), new IRrowGoto("true").getStatement());

            result = this.add(node.getChild(1));

            table.add(row);
        }
        else if(node.getLexemeClass().equals("IF")) {
            String condition = this.add(node.getChild(0));

            IRrowControl rowTrue = new IRrowControl(condition, new IRrowGoto("true").getStatement());

            table.add(rowTrue);

            IRrowGoto rowFalse = new IRrowGoto("false");

            table.add(rowFalse);

            String trueLabel = this.add(node.getChild(1));
            String falseLabel = this.add(node.getChild(2));

            rowTrue.setStatement(new IRrowGoto(trueLabel).getStatement());
            rowFalse.setLabel(falseLabel);
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
            }

            IRrowProcedure row = new IRrowProcedure(node.getLexeme(), null, params);

            table.add(row);

            result = row.getLabel();
        }
        else {
            for(int i = 0; i < node.getChildren().size(); i++) {
                result = this.add(node.getChild(i));
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