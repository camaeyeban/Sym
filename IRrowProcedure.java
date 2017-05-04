import java.util.ArrayList;

public class IRrowProcedure extends IRrow {
    private String name;
    private int paramCount;
    private String retValue;
    private ArrayList<String> params = new ArrayList<String>();

    public IRrowProcedure(String name, String retValue, ArrayList<String> params) {
        super("call");
        
        this.name = name;
        this.retValue = retValue;
        this.params = params;

        if(this.params != null){
            this.paramCount = params.size();
        }
        else{
            this.paramCount = 0;
        }
    }

    @Override
    public String toString() {
        if(this.retValue != null) {
            return super.toString() + this.retValue + " = call " + this.name + ", " + this.paramCount; 
        }

        return super.toString() + "call " + this.name + ", " + this.paramCount;
    }

    public String getCommand() {
        return this.name;
    }

    public int getParamCount() {
        return this.paramCount;
    }
}