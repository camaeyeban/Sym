public class IRrowSimple extends IRrow {
    private String val = "";

    public IRrowSimple(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return this.val;
    }
}