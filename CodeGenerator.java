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
            body.add(row.getLabel() + ":");
            if(row.getType().equals("param")) {
                IRrowParameter rowParam = (IRrowParameter)row;

                paramStack.push(rowParam.getValue());
            }
            else if(row.getType().equals("call")) {
                IRrowProcedure rowProcedure = (IRrowProcedure)row;
                String value;

                if(rowProcedure.getCommand().equals(":")){
                    value = paramStack.peek();
                    
                    // if string constant
                    if(value.matches("\".*\"")){
                        CodeConstant c = new CodeConstant(value, "str");
                        paramConstants.add(c);
                        tos = 0;
                    }
                    // if integer constant
                    else if(value.matches("\\d+")) {
                        CodeConstant c = new CodeConstant(value, "int");
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
            else if(row.getType().equals("assignment")) {
                IRrowAssignment rowAssignment = (IRrowAssignment)row;

                // check if result is in symbol table just to be sure
                boolean flag = true;

                for(CodeVariable v: paramVariables) {
                    if(v.getName().equals(rowAssignment.getResult())) {
                        flag = false;
                    }
                }

                if(flag) {
                    // @TODO: hardcoded integers ang temporary variables
                    paramVariables.add(new CodeVariable(rowAssignment.getResult(), "int"));
                }

                if(rowAssignment.getOp().equals("=")) {
                    String value = rowAssignment.getArg1();
                    String type = "";

                    // string
                    if(value.matches("\".*\"")) {
                        type = "string";

                        CodeConstant c = new CodeConstant(value, "str");
                        paramConstants.add(c);

                        value = c.getLabel();

                        body.add(
                            "\tmov rsi, " + value + "\n"
                        );

                        value = "rsi";
                    }
                    else if(value.equals("true")) {
                        value = "1";
                    }
                    else if(value.equals("false")) {
                        value = "0";
                    }
                    else if(value.matches("^\\d*$")) {
                        body.add(
                            "\tmov rax, " + value
                        );

                        value = "rax";
                    }
                    else {
                        body.add(
                            "\tmov rax, " + "[" + value + "]"
                        );

                        value = "rax";
                    }

                    String result = "[" + rowAssignment.getResult() + "]";
                    // result = (type.equals("string") ? "" : "byte") + result;

                    body.add(
                        "\tmov " + result + ", " + value + "\n"
                    );
                }
                else if(rowAssignment.getOp().equals("+")) {
                    String arg1 = rowAssignment.getArg1();
                    String arg2 = rowAssignment.getArg2();

                    if(!arg1.matches("^\\d+$")) {
                        body.add(
                            "\tmov rax, [" + arg1 + "]\n"
                        );

                        arg1 = "rax";
                    }

                    if(!arg2.matches("^\\d+$")) {
                        body.add(
                            "\tmov rbx, [" + arg2 + "]\n"
                        );

                        arg1 = "rbx";
                    }

                    body.add(
                        "\tmov qword[" + rowAssignment.getResult() + "], " + arg1 + "\n" +
                        "\tmov rcx, " + arg2 + "\n" +
                        "\tadd [" + rowAssignment.getResult() + "], rcx\n"
                    );
                }
                else if(rowAssignment.getOp().equals("-")) {
                    String arg1 = rowAssignment.getArg1();
                    String arg2 = rowAssignment.getArg2();

                    if(!arg1.matches("^\\d+$")) {
                        body.add(
                            "\tmov rax, [" + arg1 + "]\n"
                        );

                        arg1 = "rax";
                    }

                    if(!arg2.matches("^\\d+$")) {
                        body.add(
                            "\tmov rbx, [" + arg2 + "]\n"
                        );

                        arg1 = "rbx";
                    }

                    body.add(
                        "\tmov qword[" + rowAssignment.getResult() + "], " + arg1 + "\n" +
                        "\tmov rcx, " + arg2 + "\n" +
                        "\tsub [" + rowAssignment.getResult() + "], rcx\n"
                    );
                }
                else if(rowAssignment.getOp().equals("*")) {
                    String arg1 = rowAssignment.getArg1();
                    String arg2 = rowAssignment.getArg2();

                    if(!arg1.matches("^\\d+$")) {
                        body.add(
                            "\tmov rax, [" + arg1 + "]\n"
                        );

                        arg1 = "rax";
                    }

                    if(!arg2.matches("^\\d+$")) {
                        body.add(
                            "\tmov rbx, [" + arg2 + "]\n"
                        );

                        arg1 = "rbx";
                    }

                    body.add(
                        "\tmov rax, " + arg1 + "\n" +
                        "\tmov rdx, " + arg2 + "\n" +
                        "\tmul rdx\n" +
                        "\tmov [" + rowAssignment.getResult() + "]" + ", rax"
                    );
                }
                else if(rowAssignment.getOp().equals("/")) {
                    String arg1 = rowAssignment.getArg1();
                    String arg2 = rowAssignment.getArg2();

                    if(!arg1.matches("^\\d+$")) {
                        body.add(
                            "\tmov rax, [" + arg1 + "]\n"
                        );

                        arg1 = "rax";
                    }

                    if(!arg2.matches("^\\d+$")) {
                        body.add(
                            "\tmov rbx, [" + arg2 + "]\n"
                        );

                        arg1 = "rbx";
                    }

                    body.add(
                        "\txor rdx, rdx\n" +
                        "\tmov rax, " + arg1 + "\n" +
                        "\tmov rbx, " + arg2 + "\n" +
                        "\tdiv rbx\n" +
                        "\tmov [" + rowAssignment.getResult() + "]" + ", rax"
                    );
                }
                else if(rowAssignment.getOp().equals("%")) {
                    String arg1 = rowAssignment.getArg1();
                    String arg2 = rowAssignment.getArg2();

                    if(!arg1.matches("^\\d+$")) {
                        body.add(
                            "\tmov rax, [" + arg1 + "]\n"
                        );

                        arg1 = "rax";
                    }

                    if(!arg2.matches("^\\d+$")) {
                        body.add(
                            "\tmov rbx, [" + arg2 + "]\n"
                        );

                        arg1 = "rbx";
                    }

                    body.add(
                        "\txor rdx, rdx\n" +
                        "\tmov rax, " + arg1 + "\n" +
                        "\tmov rbx, " + arg2 + "\n" +
                        "\tdiv rbx\n" +
                        "\tmov [" + rowAssignment.getResult() + "]" + ", rdx"
                    );
                }
                else if(rowAssignment.getOp().equals("++")){
                    String var = rowAssignment.getArg1();

                    body.add(
                        "\tinc qword[" + var + "]\n"
                    );
                }
                else if(rowAssignment.getOp().equals("--")){
                    String var = rowAssignment.getArg1();

                    body.add(
                        "\tdec qword[" + var + "]\n"
                    );
                }
            }
            else if(row.getType().equals("if")) {
                IRrowControl rowControl = (IRrowControl)row;

                if(rowControl.getCondition().indexOf("==") > -1) {
                    String[] condition = rowControl.getCondition().split("==");

                    // check if condition[0] is literal
                    if(condition[0].matches("\\d+") || condition[0].equals("true") || condition[0].equals("false")) {
                        if(condition[0].equals("true")) {
                            condition[0] = "1";
                        }
                        if(condition[0].equals("false")) {
                            condition[0] = "0";
                        }

                        body.add(
                            "\tmov ah, " + condition[0]
                        );

                        condition[0] = "ah";
                    }

                    if(condition[1].equals("true")) {
                        condition[1] = "1";
                    }
                    if(condition[1].equals("false")) {
                        condition[1] = "0";
                    }
                    
                    // check if condition[1] is variable
                    for(CodeVariable v: paramVariables) {
                        if(v.getName().equals(condition[1])) {
                            body.add(
                                "\tmov al, [" + condition[1] + "]"
                            );

                            condition[1] = "al";

                            break;
                        }
                    }

                    body.add(
                        "\tcmp " + (condition[0].equals("ah") ? "ah" : "byte[" + condition[0] + "]") + ", " + condition[1] + "\n" +
                        "\tje " + rowControl.getTrueLabel() + "\n"
                    );
                }
                else if(rowControl.getCondition().indexOf("!=") > -1) {
                    String[] condition = rowControl.getCondition().split("!=");

                    // check if condition[0] is literal
                    if(condition[0].matches("\\d+") || condition[0].equals("true") || condition[0].equals("false")) {
                        if(condition[0].equals("true")) {
                            condition[0] = "1";
                        }
                        if(condition[0].equals("false")) {
                            condition[0] = "0";
                        }

                        body.add(
                            "\tmov ah, " + condition[0]
                        );

                        condition[0] = "ah";
                    }

                    if(condition[1].equals("true")) {
                        condition[1] = "1";
                    }
                    if(condition[1].equals("false")) {
                        condition[1] = "0";
                    }
                    
                    // check if condition[1] is variable
                    for(CodeVariable v: paramVariables) {
                        if(v.getName().equals(condition[1])) {
                            body.add(
                                "\tmov al, [" + condition[1] + "]"
                            );

                            condition[1] = "al";

                            break;
                        }
                    }

                    body.add(
                        "\tcmp " + (condition[0].equals("ah") ? "ah" : "qword[" + condition[0] + "]") + ", " + condition[1] + "\n" +
                        "\tjne " + rowControl.getTrueLabel() + "\n"
                    );
                }
                else if(rowControl.getCondition().indexOf(">=") > -1) {
                    String[] condition = rowControl.getCondition().split(">=");

                    // check if condition[0] is literal
                    if(condition[0].matches("\\d+") || condition[0].equals("true") || condition[0].equals("false")) {
                        if(condition[0].equals("true")) {
                            condition[0] = "1";
                        }
                        if(condition[0].equals("false")) {
                            condition[0] = "0";
                        }

                        body.add(
                            "\tmov rax, " + condition[0]
                        );

                        condition[0] = "rax";
                    }

                    if(condition[1].equals("true")) {
                        condition[1] = "1";
                    }
                    if(condition[1].equals("false")) {
                        condition[1] = "0";
                    }
                    
                    // check if condition[1] is variable
                    for(CodeVariable v: paramVariables) {
                        if(v.getName().equals(condition[1])) {
                            body.add(
                                "\tmov rbx, [" + condition[1] + "]"
                            );

                            condition[1] = "rbx";

                            break;
                        }
                    }

                    body.add(
                        "\tcmp " + (condition[0].equals("rax") ? "rax" : "qword[" + condition[0] + "]") + ", " + condition[1] + "\n" +
                        "\tjge " + rowControl.getTrueLabel() + "\n"
                    );
                }
                else if(rowControl.getCondition().indexOf("<=") > -1) {
                    String[] condition = rowControl.getCondition().split("<=");

                    // check if condition[0] is literal
                    if(condition[0].matches("\\d+") || condition[0].equals("true") || condition[0].equals("false")) {
                        if(condition[0].equals("true")) {
                            condition[0] = "1";
                        }
                        if(condition[0].equals("false")) {
                            condition[0] = "0";
                        }

                        body.add(
                            "\tmov rax, " + condition[0]
                        );

                        condition[0] = "rax";
                    }

                    if(condition[1].equals("true")) {
                        condition[1] = "1";
                    }
                    if(condition[1].equals("false")) {
                        condition[1] = "0";
                    }
                    
                    // check if condition[1] is variable
                    for(CodeVariable v: paramVariables) {
                        if(v.getName().equals(condition[1])) {
                            body.add(
                                "\tmov rbx, [" + condition[1] + "]"
                            );

                            condition[1] = "rbx";

                            break;
                        }
                    }

                    body.add(
                        "\tcmp " + (condition[0].equals("rax") ? "rax" : "qword[" + condition[0] + "]") + ", " + condition[1] + "\n" +
                        "\tjle " + rowControl.getTrueLabel() + "\n"
                    );
                }
                else if(rowControl.getCondition().indexOf(">") > -1) {
                    String[] condition = rowControl.getCondition().split(">");

                    // check if condition[0] is literal
                    if(condition[0].matches("\\d+") || condition[0].equals("true") || condition[0].equals("false")) {
                        if(condition[0].equals("true")) {
                            condition[0] = "1";
                        }
                        if(condition[0].equals("false")) {
                            condition[0] = "0";
                        }

                        body.add(
                            "\tmov rax, " + condition[0]
                        );

                        condition[0] = "rax";
                    }

                    if(condition[1].equals("true")) {
                        condition[1] = "1";
                    }
                    if(condition[1].equals("false")) {
                        condition[1] = "0";
                    }
                    
                    // check if condition[1] is variable
                    for(CodeVariable v: paramVariables) {
                        if(v.getName().equals(condition[1])) {
                            body.add(
                                "\tmov rbx, [" + condition[1] + "]"
                            );

                            condition[1] = "rbx";

                            break;
                        }
                    }

                    body.add(
                        "\tcmp " + (condition[0].equals("rax") ? "rax" : "qword[" + condition[0] + "]") + ", " + condition[1] + "\n" +
                        "\tjg " + rowControl.getTrueLabel() + "\n"
                    );
                }
                else if(rowControl.getCondition().indexOf("<") > -1) {
                    String[] condition = rowControl.getCondition().split("<");

                    // check if condition[0] is literal
                    if(condition[0].matches("\\d+") || condition[0].equals("true") || condition[0].equals("false")) {
                        if(condition[0].equals("true")) {
                            condition[0] = "1";
                        }
                        if(condition[0].equals("false")) {
                            condition[0] = "0";
                        }

                        body.add(
                            "\tmov rax, " + condition[0]
                        );

                        condition[0] = "rax";
                    }

                    if(condition[1].equals("true")) {
                        condition[1] = "1";
                    }
                    if(condition[1].equals("false")) {
                        condition[1] = "0";
                    }
                    
                    // check if condition[1] is variable
                    for(CodeVariable v: paramVariables) {
                        if(v.getName().equals(condition[1])) {
                            body.add(
                                "\tmov rbx, [" + condition[1] + "]"
                            );

                            condition[1] = "rbx";

                            break;
                        }
                    }

                    body.add(
                        "\tcmp " + (condition[0].equals("rax") ? "rax" : "qword[" + condition[0] + "]") + ", " + condition[1] + "\n" +
                        "\tjl " + rowControl.getTrueLabel() + "\n"
                    );
                }
                else if(rowControl.getCondition().equals("true")) {
                    body.add(
                        "\tjmp " + rowControl.getTrueLabel() + "\n"
                    );
                }
                else if(rowControl.getCondition().equals("false")) {}
                else {
                    body.add(
                        "\tcmp qword[" + rowControl.getCondition() + "]" + ", 1\n" +
                        "\tje " + rowControl.getTrueLabel() + "\n"
                    );
                }
            }
            else if(row.getType().equals("goto")) {
                IRrowGoto rowGoto = (IRrowGoto)row;

                body.add(
                    "\tjmp " + rowGoto.getGotoLabel()
                );
            }
        }
    }

    public void generateCall(String command, int paramSize, int tos) {
        if(command.equals(":")) {
            String label = "";
            int size = 30;
            String type = "";

            if(tos == 0){
                CodeConstant str = paramConstants.get( paramConstants.size() - 1);

                label = str.getLabel();
                type = str.getType();
            }
            else{
                String paramName = paramStack.pop();
                label = "[" + paramName + "]";
                
                CodeVariable variable = null;

                for(CodeVariable v : paramVariables) {
                    if(v.getName().equals(paramName)) {
                        variable = v;
                    }
                }

                type = variable.getType();
            }

            if(type.equals("str")) {
                body.add(
                    "\tmov rsi, " + label + "\n" +
                    "\tmov rdi, string_format\n" +
                    "\txor rax, rax\n" +
                    "\tcall printf\n"
                );
            }
            else if(type.equals("int")) {
                body.add(
                    "\tmov rsi, " + label + "\n" +
                    "\tmov rdi, integer_format\n" +
                    "\txor rax, rax\n" +
                    "\tcall printf\n"
                );
            }
            else if(type.equals("bln")) {
                body.add(
                    "\tmov rsi, " + label + "\n" +
                    "\tmov rdi, integer_format\n" +
                    "\txor rax, rax\n" +
                    "\tcall printf\n"
                );
            }
        }
        else if(command.equals("->")) {
            String var = paramStack.pop();

            body.add(
                "\tmov rax, 0\n" +
                "\tmov rdi, 0\n" +
                "\tmov rsi, inputBuffer\n" +
                "\tmov rdx, 30\n" +
                "\tsyscall\n"
            );
            
            body.add(
                "\tmov [" + var + "], rsi\n"
            );


            if(paramVariables.get( paramVariables.size() - 1 ).getType().equals("int")){
                body.add(
                    "\tmov rdi, [" + var + "]\n" +
                    "\tcall atoi\n" +
                    "\tmov [" + var + "], rax\n"
                );
            }

        }
    }

    public String generateData() {
        String code = 
            "section .data\n" +
            "\tnew_line: db 10\n" +
            "\tinteger_format: db \"%i\", 10, 0\n" +
            "\tstring_format: db \"%s\", 10, 0\n\t";

        code += String.join("\n\t", this.paramConstants
            .stream()
            .map(c -> c.getLabel() + ": " + (c.getType().equals("int") ? "equ" : "db") +" " + c.getValue())
            .collect(Collectors.toList()));

        return code;
    }

    public String generateBss() {
        String code = 
            "section .bss\n" +
            "\tinputBuffer resb 30\n\t";

        code += String.join("\n\t", this.paramVariables
            .stream()
            .map(c -> c.getName() + " resq " + c.getSize())
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
            "extern atoi\n\n" +
            this.generateData() + "\n\n" +
            this.generateBss() + "\n\n" +
            "section .text\n" +
            "global main\n"+
            "main:\n"+
            bodyCode + "\n" +
            exit;

        this.outputCode = code;
        return code;
    }

    private CodeVariable getVariable(String name) {
        for(CodeVariable v: paramVariables) {
            if(v.getName().equals(name)) {
                return v;
            }
        }

        return null;
    }

    public String getOuputCode(){
        return this.outputCode;
    }
}