import java.util.ArrayList;

public class SyntaxAnalyzer {
	
	private ArrayList<Token> tokens = new ArrayList<Token>();
	
    public SyntaxAnalyzer(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public void analyze(){
		Recognizer rec = new Recognizer(this.tokens);
		
		String result = rec.recognize();
			
		System.out.println("\n---------------------- RECOGNIZER ---------------------\n");
        System.out.println("Code is " + result + ".");
        System.out.println("\n------------------ END OF RECOGNIZER ------------------\n");
	
	}
	
}