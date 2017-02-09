import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Compiler {

	String inputFile;

	Compiler(String inputFile){
		this.inputFile = inputFile;
	}

	public void compile(){
		String fileContent = this.readFile();
		//System.out.println(fileContent);

		LexicalAnalyzer lex = new LexicalAnalyzer(fileContent);
		lex.generateLexemes();
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
				fileContent = fileContent.concat(line);
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