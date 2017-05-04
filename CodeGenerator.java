import java.util.ArrayList;
import java.util.Stack;

public class CodeGenerator {
    private ArrayList<String> paramConstants = new ArrayList<String>();
    private Stack<String> paramStack = new Stack<String>();

    private ArrayList<String> body = new ArrayList<String>();

    public CodeGenerator(ArrayList<IRrow> table) {
        for(IRrow row: table) {
            if(row.getType().equals("param")) {
                IRrowParameter rowParam = (IRrowParameter)row;
                paramConstants.add(rowParam.toString());
                paramStack.push(rowParam.toString());
            }
            else if(row.getType().equals("call")) {
                paramStack.pop();

                IRrowProcedure rowProcedure = (IRrowProcedure)row;

                this.generateCall(rowProcedure.getCommand(), rowProcedure.getParamCount());
            }
        }
    }

    public void generateCall(String command, int paramSize) {
        if(command.equals(":")) {

        }
    }

    public String generate() {
        String exit =
            "mov eax, 60\n" +
            "xor rdi, rdi\n" +
            "syscall\n";

        String code =
            "global _start\n" +
            "section .text\n" +
            "_start\n"+
            exit;

        return code;
    }
}