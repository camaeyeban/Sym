import java.util.ArrayList;

public class IRrowProcedure extends IRrow {
    private String name;
    private int paramCount;
    private String retValue;
    private ArrayList<String> params = new ArrayList<String>();

    public IRrowProcedure(String name, String retValue, ArrayList<String> params) {
        this.name = name;
        this.retValue = retValue;
        this.params = params;

        this.paramCount = params.size();
    }

    @Override
    public String toString() {
        String result = "";

        for(int i = 0; i < paramCount; i++){
            result += "\nparam " + params.get(i);
        }

        if(this.retValue != null) {
            result += "\n" + this.retValue + " = call " + this.name + ", " + this.paramCount + "\n"; 
        }
        else{
            result += "\ncall " + this.name + ", " + this.paramCount + "\n"; 
        }

        return result;
    }
}