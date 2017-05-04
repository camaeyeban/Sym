import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Compiler {

	private String inputFile;
    private ArrayList<Token> tokens = new ArrayList<Token>();
	private int errorCount = 0;

	Compiler(String inputFile){
		this.inputFile = inputFile;
	}

	public void compile(){
        String fileContent = this.readFile();
		fileContent = fileContent.concat("\n$");
		
		System.out.println("\n------------------------------- FILE CONTENT ------------------------------\n");
		System.out.println(fileContent);
		System.out.println("\n--------------------------- END OF FILE CONTENT ---------------------------\n");


		LexicalAnalyzer lex = new LexicalAnalyzer(fileContent);
		tokens = lex.generateLexemes();
		
		SyntaxAnalyzer syn = new SyntaxAnalyzer(tokens);
		AbstractSyntaxTreeNode ast = syn.analyze();
		
		errorCount += syn.getErrorCount();
		System.out.println("\n***************************************************************************\n");
		System.out.println("Found " + this.errorCount + " errors.");

		if(errorCount > 0 || ast == null) return;

		// convert to intermediate representation
		IRGenerator irGen = new IRGenerator(ast);

		System.out.println("IR:");
		IRTable irTable = irGen.generate();
		irTable.printTable();

		CodeGenerator codeGenerator = new CodeGenerator(irTable.getTable());

		System.out.println("\n***************************************************************************\n");
		System.out.println(codeGenerator.generate());
	}

    public String readFile(){
    	BufferedReader br = null;
		FileReader fr = null;
		String fileContent = "";

		try {
			fr = new FileReader(this.inputFile);
			br = new BufferedReader(fr);

			String line;

			br = new BufferedReader(new FileReader(this.inputFile));

			while ((line = br.readLine()) != null) {
				fileContent = fileContent.concat("\n" + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} 

		return fileContent;
    }
}