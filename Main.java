public class Main {
    public static void main(String args[]) {
        LexicalAnalyzer lexAnalyzer = new LexicalAnalyzer("@(main, non, (args|str){:(\"Hello world!\")})");

        lexAnalyzer.generateLexemes();
    }
}