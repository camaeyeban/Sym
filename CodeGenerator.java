import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

public class CodeGenerator {
    private ArrayList<CodeConstant> paramConstants = new ArrayList<CodeConstant>();
    private Stack<CodeConstant> paramStack = new Stack<CodeConstant>();

    private ArrayList<String> body = new ArrayList<String>();

    public CodeGenerator(ArrayList<IRrow> table) {
        for(IRrow row: table) {
            if(row.getType().equals("param")) {
                IRrowParameter rowParam = (IRrowParameter)row;

                CodeConstant param = new CodeConstant(rowParam.getValue());

                paramConstants.add(param);
                paramStack.push(param);
            }
            else if(row.getType().equals("call")) {
                IRrowProcedure rowProcedure = (IRrowProcedure)row;

                this.generateCall(rowProcedure.getCommand(), rowProcedure.getParamCount());
            }
        }
    }

    public void generateCall(String command, int paramSize) {
        if(command.equals(":")) {
            CodeConstant str = paramStack.pop();

            body.add(
                "mov rax, 1"
            );
            body.add(
                "mov rdi, 1"
            );
            body.add(
                "mov rsi, " + str.getLabel()
            );
            body.add(
                "mov rdx, " + (str.getValue().length() - 2)
            );
            body.add(
                "syscall"
            );

            body.add(
                "mov eax, 4"
            );
            body.add(
                "mov ebx, 1"
            );
            body.add(
                "mov ecx, new_line"
            );
            body.add(
                "mov edx, 1"
            );
            body.add(
                "int 80h"
            );
        }
    }

    public String generateData() {
        String code = 
            "section .data\n" +
            "new_line: db 10\n";

        code += String.join("\n", this.paramConstants
            .stream()
            .map(c -> c.getLabel() + ": db " + c.getValue() + ", 10")
            .collect(Collectors.toList()));

            return code;
    }

    public String generate() {
        String exit =
            "mov eax, 60\n" +
            "xor rdi, rdi\n" +
            "syscall\n";

        String bodyCode = String.join("\n", this.body);

        String code =
            "global _start\n" +
            this.generateData() + "\n" +
            "section .text\n" +
            "_start:\n"+
            bodyCode + "\n" +
            exit;

        return code;
    }
}