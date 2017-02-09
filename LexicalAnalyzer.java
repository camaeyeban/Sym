/*
    @TODOs:

    Check for escaped characters in strings
    Identify Type
*/

import java.util.ArrayList;
import java.util.Arrays;

public class LexicalAnalyzer {
    String fileContents;

    public LexicalAnalyzer(String fileContents) {
        this.fileContents = fileContents;
    }

    private Token getKeyword(String fileContents) {
        String tokenBuffer = "";
        int i = 0;

        while(!isDelimeter(fileContents.charAt(i))) {
            tokenBuffer += fileContents.charAt(i);
            i++;
        }

        // identify type here

        return new Token("TYPE_PENDING", tokenBuffer);
    }

    // @TODO: 
    public void generateLexemes() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        // Tokens here

        for(int i = 0; i < fileContents.length();) {
            switch(fileContents.charAt(i)) {   
                case '(': {
                    tokens.add(new Token("FUNCTION_DELIMETER_LEFT", "("));
                    i++;
                    break;
                }
                case ')': {
                    tokens.add(new Token("FUNCTION_DELIMETER_RIGHT", ")"));
                    i++;
                    break;
                }
                case '{': {
                    tokens.add(new Token("CODE_DELIMETER_LEFT", "{"));
                    i++;
                    break;
                }
                case '}': {
                    tokens.add(new Token("CODE_DELIMETER_LEFT", "}"));
                    i++;
                    break;
                }
                case '"': {
                    tokens.add(new Token("CODE_DELIMETER_LEFT", "\""));
                    i++;
                    break;
                }
                case '|': {
                    tokens.add(new Token("DATA_TYPE_DELIMETER", "|"));
                    i++;
                    break;
                }
                case ';': {
                    tokens.add(new Token("STATEMENT_DELIMETER", ";"));
                    i++;
                    break;
                }
                case ',': {
                    tokens.add(new Token("ARGUMENT_DELIMETER", ","));
                    i++;
                    break;
                }
                default: {
                    if (Character.isWhitespace(fileContents.charAt(i))) {
                        i++;
                        break;
                    }

                    Token keyword = getKeyword(fileContents.substring(i));

                    tokens.add(keyword);

                    i += keyword.getLexeme().length();
                }
            }
        }

        System.out.println(tokens);
    }

    private boolean isDelimeter(char c) {
        for(int i = 0; i < Meta.DELIMETERS.length; i++) {
            if (c == Meta.DELIMETERS[i]) return true;
        }

        return false;
    }
}

/*

*/