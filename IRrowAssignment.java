public class IRrowAssignment extends IRrow {
    private String op;
    private String arg1;
    private String arg2;
    private String result;

    private static int tempVariableIndex = 0;

    public IRrowAssignment(String op, String arg1, String arg2, String result) {
        super("assignment");

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

    @Override
    public String toString() {
        if(this.arg2 != null) {
            return super.toString() + this.result + " = " + this.arg1 + " " + this.op + " " + this.arg2; 
        }

        return super.toString() + this.result + " = " + (this.op.equals("=") ? "": this.op + " ") + this.arg1;
    }
}