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
        String result = super.toString();

        for(int i = 0; i < paramCount; i++){
            result += "param " + params.get(i);
        }

        if(this.retValue != null) {
            result += this.retValue + " = call " + this.name + ", " + this.paramCount; 
        }
        else{
            result += "call " + this.name + ", " + this.paramCount; 
        }

        return result;
    }
}