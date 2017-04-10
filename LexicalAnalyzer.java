import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LexicalAnalyzer {
	
    String fileContents;

    public LexicalAnalyzer(String fileContents) {
        this.fileContents = fileContents;
    }

    private Keyword getKeyword(String fileContents, int lineNumber) {
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

        return new Keyword(new Token(TypeAnalyzer.identify(tokenBuffer), tokenBuffer, lineNumber), i);
    }

    private void clearComments() {
        List<String> mappedContent = Arrays.asList(fileContents.split("\n"))
            .stream()
            .map(t -> removeComment(t))
            .collect(Collectors.toList());

        fileContents = joinString(mappedContent.toArray(new String[mappedContent.size()]), "\n");
    }

    private String removeComment(String s) {
        return s.indexOf("//") > -1 ? s.substring(0, s.indexOf("//")) : s;
    }

    public ArrayList<Token> generateLexemes() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        boolean stringFlag = false;
		int lineNumber = 0;

        clearComments();

        for(int i = 0; i < fileContents.length();) {
            switch(fileContents.charAt(i)) {
				case '\n':{
					lineNumber++;
					i++;
					break;
				}
                case '(': {
                    tokens.add(new Token("FUNCTION_DELIMITER_LEFT", "(", lineNumber));
                    i++;
                    break;
                }
                case ')': {
                    tokens.add(new Token("FUNCTION_DELIMITER_RIGHT", ")", lineNumber));
                    i++;
                    break;
                }
                case '{': {
                    tokens.add(new Token("CODE_DELIMITER_LEFT", "{", lineNumber));
                    i++;
                    break;
                }
                case '}': {
                    tokens.add(new Token("CODE_DELIMITER_RIGHT", "}", lineNumber));
                    i++;
                    break;
                }
                case '"': {
                    tokens.add(new Token("STRING_DELIMITER", "\"", lineNumber));
                    stringFlag = !stringFlag;
                    i++;
                    break;
                }
                case '|': {
                    tokens.add(new Token("DATA_TYPE_DELIMITER", "|", lineNumber));
                    i++;
                    break;
                }
                case ';': {
                    tokens.add(new Token("STATEMENT_DELIMITER", ";", lineNumber));
                    i++;
                    break;
                }
                case ',': {
                    tokens.add(new Token("ARGUMENT_DELIMITER", ",", lineNumber));
                    i++;
                    break;
                }
                case '$': {
                    tokens.add(new Token("END_OF_FILE_MARKER", "$", lineNumber));
                    i++;
                    break;
                }
                default: {
                    if (Character.isWhitespace(fileContents.charAt(i))) {
                        i++;
                        break;
                    }

                    if (stringFlag) {
                        Keyword word = getKeyword(fileContents.substring(i), lineNumber);

                        tokens.add(new Token("STRING_LITERAL", word.getToken().getLexeme(), lineNumber));

                        i += word.getLength();

                        break;
                    }

                    Keyword keyword = getKeyword(fileContents.substring(i), lineNumber);

                    tokens.add(keyword.getToken());

                    i += keyword.getLength();
                }
            }
        }

		System.out.println("\n----------------------------- LEXICAL ANALYZER ----------------------------\n");
        System.out.println(tokens);
        System.out.println("\n------------------------- END OF LEXICAL ANALYZER -------------------------\n");
		
        return tokens;
    }

    private boolean isDelimiter(char c) {
        for(int i = 0; i < Meta.DELIMITERS.length; i++) {
            if (c == Meta.DELIMITERS[i]) return true;
        }

        return false;
    }

    public static String joinString(Object[] arr, String separator) {
        if (null == arr || 0 == arr.length) return "";

        StringBuilder sb = new StringBuilder(256);
        sb.append(arr[0]);

        //if (arr.length == 1) return sb.toString();

        for (int i = 1; i < arr.length; i++) sb.append(separator).append(arr[i]);

        return sb.toString();
    }
}