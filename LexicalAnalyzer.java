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

    private String getWord(String fileContents) {
        String tokenBuffer = "";
        int i = 0;

        while(!isDelimiter(fileContents.charAt(i))) {
            tokenBuffer += fileContents.charAt(i);
            i++;
        }

        return tokenBuffer;
    }

    private Token getKeyword(String fileContents) {
        String word = getWord(fileContents);

        return new Token(TypeAnalyzer.identify(word), word);
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
                        String word = getWord(fileContents.substring(i));

                        tokens.add(new Token("STRING_LITERAL", word));

                        i += word.length();

                        break;
                    }

                    Token keyword = getKeyword(fileContents.substring(i));

                    tokens.add(keyword);

                    i += keyword.getLexeme().length();
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