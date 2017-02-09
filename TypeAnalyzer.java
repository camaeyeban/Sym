public class TypeAnalyzer {
    private static String DATA_TYPE_PATTERN = "^(str|int|flt|bln|non|cod|fn)$";
    private static String INTEGER_PATTERN = "^-?\\d+$";
    private static String BOOLEAN_PATTERN = "^(true|false)$";
    private static String FLOAT_PATTERN = "^-?\\d+(\\.\\d)?$";

    public static String identify(String in) {
        if (in.matches(DATA_TYPE_PATTERN)) {
            return "DATA_TYPE";
        }

        if (in.matches(INTEGER_PATTERN)) {
            return "INTEGER_LITERAL";
        }
        
        if (in.matches(BOOLEAN_PATTERN)) {
            return "BOOLEAN_LITERAL";
        }

        if (in.matches(FLOAT_PATTERN)) {
            return "FLOAT_LITERAL";
        }

        return "IDENTIFIER";
    }
}