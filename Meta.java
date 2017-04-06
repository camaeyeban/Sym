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
        "<-",

        "+",
        "-",
        "/",
        "*",
        "%",
        "=",
        "==",
        "<",
        "<=",
        ">",
        "=>",
        "!=",
        "&&",
        "||"
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
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("#", "DECLARATION");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("?", "IF");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("~", "WHILE");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("++", "INCREMENT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("--", "DECREMENT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<-", "RETURN");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("+", "ADD");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("-", "SUBTRACT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("*", "MULTIPLY");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("/", "DIVIDE");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("%", "MODULO");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("=", "ASSIGNMENT");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("==", "EQUALS");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<", "LESS THAN");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<=", "LESS THAN OR EQUAL TO");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(">", "GREATER THAN");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(">=", "GREATER THAN OR EQUAL TO");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("!=", "NOT EQUAL TO");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("&&", "AND");
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("||", "OR");
    }
}