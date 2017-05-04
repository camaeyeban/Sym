public class IRrowGoto extends IRrow {
    private String label;

    public IRrowGoto(String label) {
        super("goto");

        this.label = label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStatement() {
        return "goto " + this.label;
    }

    @Override
    public String toString() {
        return super.toString() + this.getStatement();
    }
}