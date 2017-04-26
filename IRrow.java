public class IRrow {
    private String op;
    private String arg1;
    private String arg2;
    private String result;

    private static int tempVariableIndex = 0;

    public IRrow(String op, String arg1, String arg2, String result) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;

        if(result == null) {
            this.result = "T" + (tempVariableIndex++);
        }
        else {
            this.result = result;
        }
    }

    public String getResult() {
        return this.result;
    }

    public String toString() {
        return "\n{\n\top: " + this.op + ",\n\targ1: " + this.arg1 + ",\n\targ2: " + this.arg2 + ",\n\tres: " + this.result + "\n}";
    }
}