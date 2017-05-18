public class CodeVariable {
    private String name;
    private String type;
    private int size;
    private String value;

    public CodeVariable(String name, String type) {
        this.name = name;
        this.type = type;
        this.value = null;

        if(this.type.equals("int") || this.type.equals("bln")) {
            this.size = 1;
        }
        else if(this.type.equals("str")) {
            this.size = 30;
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public int getSize() {
        return this.size;
    }
}