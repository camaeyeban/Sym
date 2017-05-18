import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;
import java.io.*;

public class CodeGenerator {
    private String outputCode;

    private ArrayList<CodeVariable> paramVariables = new ArrayList<CodeVariable>();
    private ArrayList<CodeConstant> paramConstants = new ArrayList<CodeConstant>();
    private Stack<String> paramStack = new Stack<String>();

    private ArrayList<String> body = new ArrayList<String>();

    public CodeGenerator(ArrayList<IRrow> table) {
        int tos = 0;    // latest param is 0 if constant while 1 if variable
        for(IRrow row: table) {
            if(row.getType().equals("param")) {
                IRrowParameter rowParam = (IRrowParameter)row;

                paramStack.push(rowParam.getValue());
            }
            else if(row.getType().equals("call")) {
                IRrowProcedure rowProcedure = (IRrowProcedure)row;
                String value;

                if(rowProcedure.getCommand().equals(":")){
                    value = paramStack.pop();
                    
                    // if string constant
                    if(value.matches("\".*\"")){
                        CodeConstant c = new CodeConstant(value, "STRING");
                        paramConstants.add(c);
                        tos = 0;
                    }
                    // if integer constant
                    else if(value.matches("\\d+")) {
                        CodeConstant c = new CodeConstant(value, "INTEGER");
                        paramConstants.add(c);
                        tos = 0;
                    }
                    // if variable
                    else{
                        CodeVariable v = new CodeVariable(value, "");
                        tos = 1;
                    }
                }
                else if(rowProcedure.getCommand().equals("#")){
                    String type = paramStack.pop();
                    String name = paramStack.pop();
                    
                    CodeVariable v = new CodeVariable(name, type);
                    paramVariables.add(v);
                }
                
                this.generateCall(rowProcedure.getCommand(), rowProcedure.getParamCount(), tos);
            }
        }
    }

    public void generateCall(String command, int paramSize, int tos) {
        if(command.equals(":")) {
            String label = "";
            int size = 30;
            CodeConstant str = null;

            if(tos == 0){
                str = paramConstants.get( paramConstants.size() - 1);

                label = str.getLabel();
                size = str.getValue().length() - 2;
            }
            else{
                label = "[" + paramVariables.get( paramVariables.size()-1 ).getName() + "]";
            }

            if(str == null || str.getType().equals("STRING")) {
                body.add(
                    "\tmov rax, 1\n" +
                    "\tmov rdi, 1\n" +
                    "\tmov rsi, " + label + "\n" +
                    "\tmov rdx, " + size + "\n" +
                    "\tsyscall\n"
                );
            }
            else if(str.getType().equals("INTEGER")) {
                body.add(
                    "\tsub rsp, 8\n" +
                    "\tmov rsi, [" + label + "]\n" +
                    "\tmov rdi, integer_format\n" +
                    "\txor rax, rax\n" +
                    "\tcall printf\n"
                );
            }

            body.add(
                "\tmov eax, 4\n" +
                "\tmov ebx, 1\n" +
                "\tmov ecx, new_line\n" +
                "\tmov edx, 1\n" +
                "\tint 80h\n"
            );
        }
        else if(command.equals("->")) {
            CodeVariable var = paramVariables.get( paramVariables.size()-1 );
            
            body.add(
                "\tmov rax, 0\n" +
                "\tmov rdi, 0\n" +
                "\tmov rsi, inputBuffer\n" +
                "\tmov rdx, 30\n" +
                "\tsyscall\n"
            );
            
            body.add(
                "\tmov [" + var.getName() + "], rsi\n"
            );
        }
    }

    public String generateData() {
        String code = 
            "section .data\n" +
            "\tnew_line: db 10\n" +
            "\tinteger_format: db \"%i\", 10, 0\n\t";

        code += String.join("\n\t", this.paramConstants
            .stream()
            .map(c -> c.getLabel() + ": db " + c.getValue())
            .collect(Collectors.toList()));

        return code;
    }

    public String generateBss() {
        String code = 
            "section .bss\n" +
            "\tinputBuffer resb 30\n\t";

        code += String.join("\n\t", this.paramVariables
            .stream()
            .map(c -> c.getName() + " resb 30")
            .collect(Collectors.toList()));

        return code;
    }
    
    public String generate() {
        String exit =
            "\tmov eax, 60\n" +
            "\txor rdi, rdi\n" +
            "\tsyscall\n";

        String bodyCode = String.join("\n", this.body);

        String code =
            "extern printf\n" +
            this.generateData() + "\n\n" +
            this.generateBss() + "\n\n" +
            "section .text\n" +
            "global main:\n"+
            "main:\n"+
            bodyCode + "\n" +
            exit;

        this.outputCode = code;
        return code;
    }

    public String getOuputCode(){
        return this.outputCode;
    }
}