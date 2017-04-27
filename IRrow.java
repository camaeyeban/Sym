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
        if(this.arg2 != null) {
            return "\n" + this.result + " = " + this.arg1 + " " + this.op + " " + this.arg2 + "\n"; 
        }

        return "\n" + this.result + " = " + (this.op != "=" ? this.op + " " : "") + this.arg1 + "\n";
    }
}