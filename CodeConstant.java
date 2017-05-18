public class CodeConstant {
    
    private static int index = 0;
    private String label;
    private String value;
    private String type;

    public CodeConstant(String value, String type) {
        this.label = "_" + CodeConstant.index++;
        this.value = value;
        this.type = type;
    }

    public String getLabel() {
        return this.label;
    }
    
    public String getValue() {
        return this.value + (this.type.equals("STRING") ? " , 10" : "");
    }

    public String getType() {
        return this.type;
    }
}