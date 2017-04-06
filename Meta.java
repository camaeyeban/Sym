import java.util.HashMap;

public class Meta {
    public static String[] RESERVED_FUNCTION_NAMES = {
        "@",
        "->",
        ":",
        "#",
        "?",
        "~",
        "++",
        "--",
        "<-"
    };

    public static char[] DELIMITERS = {
        '{',
        '}',
        '(',
        ')',
        ',',
        ';',
        '"',
        '|'
    };

    public static HashMap<String, String> RESERVED_FUNCTIONS_LOOKUP_TABLE = new HashMap<String, String>();

    static {
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("@", "FUNCTION_DECLARATION");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("->", "READ_INPUT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(":", "PRINT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("#", "ASSIGNMENT_OPERATOR");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("?", "IF");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("~", "WHILE");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("++", "INCREMENT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("--", "DECREMENT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<-", "RETURN");
    }
}