import java.util.ArrayList;

public class IRrowParameter extends IRrow {
    private String name;

    public IRrowParameter(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + "param " + this.name;
    }
}