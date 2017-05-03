import java.util.ArrayList;
import java.util.Stack;

public class IRTable {
    private ArrayList<IRrow> table = new ArrayList<IRrow>();

    private static Stack<IRrowControl> trueStack = new Stack<IRrowControl>();
    private static Stack<IRrowGoto> falseStack = new Stack<IRrowGoto>();
    private static Stack<IRrowGoto> endStack = new Stack<IRrowGoto>();
    private static Stack<IRrowControl> andTrueStack = new Stack<IRrowControl>();

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
            IRrowControl row = new IRrowControl(this.add(node.getChild(0)), new IRrowGoto("").getStatement());

            IRTable.trueStack.push(row);

            result = this.add(node.getChild(1));

            table.add(row);
        }
        else if(node.getLexemeClass().equals("AND")) {
            IRrowControl row1 = new IRrowControl(this.add(node.getChild(0)), new IRrowGoto("").getStatement());
            IRrowGoto rowFalse = new IRrowGoto("");

            IRTable.andTrueStack.push(row1);
            IRTable.falseStack.push(rowFalse);

            result = this.add(node.getChild(1));

            table.add(row1);
            table.add(rowFalse);
        }
        else if(node.getLexemeClass().equals("IF")) {
            int andTrueStackIndex = IRTable.andTrueStack.size();
            int trueStackIndex = IRTable.trueStack.size();
            int falseStackIndex = IRTable.falseStack.size();
            String firstLabel = "";

            String condition = this.add(node.getChild(0));

            IRrowControl rowTrue = new IRrowControl(condition, new IRrowGoto("").getStatement());
            firstLabel = rowTrue.getLabel();

            IRTable.trueStack.push(rowTrue);

            table.add(rowTrue);

            IRrowGoto rowFalse = new IRrowGoto("");
            IRrowGoto gotoEnd = new IRrowGoto("");

            table.add(rowFalse);

            String trueLabel = this.add(node.getChild(1));

            table.add(gotoEnd);
            IRTable.endStack.push(gotoEnd);

            String falseLabel = this.add(node.getChild(2));

            while(IRTable.andTrueStack.size() > andTrueStackIndex) {
                IRrow popped = IRTable.andTrueStack.pop();
                ((IRrowControl)popped).setStatement(new IRrowGoto(rowTrue.getLabel()).getStatement());

                if(popped.getLabel().compareTo(firstLabel) < 0) {
                    firstLabel = popped.getLabel();
                }
            }

            while(IRTable.trueStack.size() > trueStackIndex) {
                IRrow popped = IRTable.trueStack.pop();
                ((IRrowControl)popped).setStatement(new IRrowGoto(trueLabel).getStatement());

                if(popped.getLabel().compareTo(firstLabel) < 0) {
                    firstLabel = popped.getLabel();
                }
            }

            while(IRTable.falseStack.size() > falseStackIndex) {
                IRrow popped = IRTable.falseStack.pop();
                ((IRrowGoto)popped).setLabel(falseLabel);

                if(popped.getLabel().compareTo(firstLabel) < 0) {
                    firstLabel = popped.getLabel();
                }
            }

            rowFalse.setLabel(falseLabel);

            IRrow endLabel = new IRrow();

            table.add(endLabel);

            while(!IRTable.endStack.empty()) {
                IRTable.endStack.pop().setLabel(endLabel.getLabel());
            }

            result = firstLabel;
        }
        else if(node.getLexemeClass().equals("WHILE")) {
            int andTrueStackIndex = IRTable.andTrueStack.size();
            int trueStackIndex = IRTable.trueStack.size();
            int falseStackIndex = IRTable.falseStack.size();
            String firstLabel = "";

            String condition = this.add(node.getChild(0));

            IRrowControl rowCondition = new IRrowControl(condition, new IRrowGoto("").getStatement());
            firstLabel = rowCondition.getLabel();
            IRrowGoto gotoEnd = new IRrowGoto("");

            IRTable.trueStack.push(rowCondition);
            
            table.add(rowCondition);
            table.add(gotoEnd);

            String bodyLabel = this.add(node.getChild(1));

            IRrowGoto loopRow = new IRrowGoto(rowCondition.getLabel());
            table.add(loopRow);

            IRrow end = new IRrow();
            gotoEnd.setLabel(end.getLabel());
            table.add(end);

            while(IRTable.andTrueStack.size() > andTrueStackIndex) {
                IRrow popped = IRTable.andTrueStack.pop();
                ((IRrowControl)popped).setStatement(new IRrowGoto(bodyLabel).getStatement());

                if(popped.getLabel().compareTo(firstLabel) < 0) {
                    firstLabel = popped.getLabel();
                }
            }

            while(IRTable.trueStack.size() > trueStackIndex) {
                IRrow popped = IRTable.trueStack.pop();
                ((IRrowControl)popped).setStatement(new IRrowGoto(bodyLabel).getStatement());

                if(popped.getLabel().compareTo(firstLabel) < 0) {
                    firstLabel = popped.getLabel();
                }
            }

            while(IRTable.falseStack.size() > falseStackIndex) {
                IRrow popped = IRTable.falseStack.pop();
                ((IRrowGoto)popped).setLabel(end.getLabel());

                if(popped.getLabel().compareTo(firstLabel) < 0) {
                    firstLabel = popped.getLabel();
                }
            }

            result = firstLabel;
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

                if(result.equals("")) {
                    result = row.getLabel();
                }
            }

            IRrowProcedure row = new IRrowProcedure(node.getLexeme(), null, params);

            table.add(row);
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