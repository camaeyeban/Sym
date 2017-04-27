public class IRrow {
    protected String label = "";

    private static int labelIndex = 0;

    public IRrow() {
        this.label = "L" + IRrow.labelIndex++;
    }

    public String getLabel() {
        return this.label;
    }

    public String toString() {
        return this.label + "\t";
    }
}