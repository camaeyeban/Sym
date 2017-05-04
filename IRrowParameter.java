import java.util.ArrayList;

public class IRrowParameter extends IRrow {
    private String name;

    public IRrowParameter(String name) {
        super("param");
        
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + "param " + this.name;
    }
}