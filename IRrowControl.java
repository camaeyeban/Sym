public class IRrowControl {
    private String condition;
    private String statement;

    public IRrowControl(String condition, String statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return super.toString() + "if " + condition + " " + statement;
    }
}