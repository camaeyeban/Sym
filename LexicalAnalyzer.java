/*
    @TODOs:

    Check for escaped characters in strings
    Token Values
    String Literal
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

        while(!isDelimIter(fileContents.charAt(i))) {
            tokenBuffer += fileContents.charAt(i);
            i++;
        }

        // identify type here

        return new Token(TypeAnalyzer.identify(tokenBuffer), tokenBuffer);
    }

    public void generateLexemes() {
        ArrayList<Token> tokens = new ArrayList<Token>();

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
                    tokens.add(new Token("CODE_DELIMITER_LEFT", "\""));
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

                    Token keyword = getKeyword(fileContents.substring(i));

                    tokens.add(keyword);

                    i += keyword.getLexeme().length();
                }
            }
        }

        System.out.println(tokens);
    }

    private boolean isDelimIter(char c) {
        for(int i = 0; i < Meta.DELIMITERS.length; i++) {
            if (c == Meta.DELIMITERS[i]) return true;
        }

        return false;
    }
}