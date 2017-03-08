/*
    @TODOs:

    Check for escaped characters in strings
	Skip comments
*/

import java.util.ArrayList;
import java.util.Arrays;

public class LexicalAnalyzer {
	
    String fileContents;

    public LexicalAnalyzer(String fileContents) {
        this.fileContents = fileContents;
    }

    private Keyword getKeyword(String fileContents) {
        String tokenBuffer = "";
        int i = 0;
        int escaped = 0;

        while(!isDelimiter(fileContents.charAt(i)) || escaped == 1) {
            if (fileContents.charAt(i) == '\\' && escaped == 0) {
                escaped = 1;
                i++;
                continue;
            }

            tokenBuffer += fileContents.charAt(i);
            escaped = 0;
            i++;
        }

        return new Keyword(new Token(TypeAnalyzer.identify(tokenBuffer), tokenBuffer), i);
    }

    public ArrayList<Token> generateLexemes() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        boolean stringFlag = false;

        for(int i = 0; i < fileContents.length();) {
            switch(fileContents.charAt(i)) {   
                case '(': {
                    tokens.add(new Token("FUNCTION_DELIMITER_LEFT", "("));
                    i++;
                    break;
                }
                case ')': {
                    tokens.add(new Token("FUNCTION_DELIMITER_RIGHT", ")"));
                    i++;
                    break;
                }
                case '{': {
                    tokens.add(new Token("CODE_DELIMITER_LEFT", "{"));
                    i++;
                    break;
                }
                case '}': {
                    tokens.add(new Token("CODE_DELIMITER_RIGHT", "}"));
                    i++;
                    break;
                }
                case '"': {
                    tokens.add(new Token("STRING_DELIMITER", "\""));
                    stringFlag = !stringFlag;
                    i++;
                    break;
                }
                case '|': {
                    tokens.add(new Token("DATA_TYPE_DELIMITER", "|"));
                    i++;
                    break;
                }
                case ';': {
                    tokens.add(new Token("STATEMENT_DELIMITER", ";"));
                    i++;
                    break;
                }
                case ',': {
                    tokens.add(new Token("ARGUMENT_DELIMITER", ","));
                    i++;
                    break;
                }
                default: {
                    if (Character.isWhitespace(fileContents.charAt(i))) {
                        i++;
                        break;
                    }

                    if (stringFlag) {
                        Keyword word = getKeyword(fileContents.substring(i));

                        tokens.add(new Token("STRING_LITERAL", word.getToken().getLexeme()));

                        i += word.getLength();

                        break;
                    }

                    Keyword keyword = getKeyword(fileContents.substring(i));

                    tokens.add(keyword.getToken());

                    i += keyword.getLength();
                }
            }
        }

		System.out.println("\n------------------- LEXICAL ANALYZER ------------------\n");
        System.out.println(tokens);
        System.out.println("\n--------------- END OF LEXICAL ANALYZER ---------------\n");
		
        return tokens;
    }

    private boolean isDelimiter(char c) {
        for(int i = 0; i < Meta.DELIMITERS.length; i++) {
            if (c == Meta.DELIMITERS[i]) return true;
        }

        return false;
    }
}