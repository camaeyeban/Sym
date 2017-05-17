public class CodeConstant {
    
    private static int index = 0;
    private String label;
    private String value;

    public CodeConstant(String value) {
        this.label = "STR_" + CodeConstant.index++;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }
    
    public String getValue() {
        return this.value;
    }
}