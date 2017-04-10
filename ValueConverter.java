public class ValueConverter {
    public static String convert(String type) {
        switch(type) {
            case "FUNCTION_DELIMITER_LEFT": {
                return "000";
            }
            case "FUNCTION_DELIMITER_RIGHT": {
                return "001";
            }
            case "CODE_DELIMITER_LEFT": {
                return "002";
            }
            case "CODE_DELIMITER_RIGHT": {
                return "003";
            }
            case "STRING_DELIMITER": {
                return "004";
            }
            case "IDENTIFIER": {
                return "005";
            }
            case "DATA_TYPE": {
                return "006";
            }
            case "DATA_TYPE_DELIMITER": {
                return "007";
            }
            case "STATEMENT_DELIMITER": {
                return "008";
            }
            case "BOOLEAN_LITERAL": {
                return "009";
            }
            case "INTEGER_LITERAL": {
                return "010";
            }
            case "FLOAT_LITERAL": {
                return "011";
            }
            case "STRING_LITERAL": {
                return "012";
            }
            case "ARGUMENT_DELIMITER": {
                return "013";
            }
            case "END_OF_FILE_MARKER": {
                return "014";
            }
        }
        return "UNKOWN";
    }
}