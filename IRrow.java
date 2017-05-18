public class IRrow {
    protected String label = "";
    private String type;

    private static int labelIndex = 0;

    public IRrow() {
        this("empty");
    }

    public IRrow(String type) {
        this.type = type;
        this.label = "__" + IRrow.labelIndex++;
    }

    public String getLabel() {
        return this.label;
    }

    public String toString() {
        return this.label + "\t";
    }

    public String getType() {
        return this.type;
    }
}