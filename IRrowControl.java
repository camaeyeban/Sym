public class IRrowControl extends IRrow {
    private String condition;
    private String statement;

    public IRrowControl(String condition, String statement) {
        super("if");
        
        this.condition = condition;
        this.statement = statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return super.toString() + "if " + condition + " " + statement;
    }

    public String getCondition() {
        return this.condition;
    }

    public String getTrueLabel() {
        return this.statement.substring(5);
    }
}