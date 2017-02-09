public class Main {

	static Compiler compiler;

    public static void main(String args[]) {
        String inputFile = args[0];
        compiler = new Compiler(inputFile);
        compiler.compile();
    }
}