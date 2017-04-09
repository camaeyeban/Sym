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

    public static HashMap<String, ReservedFunctionsIdentifierDataTypePair> RESERVED_FUNCTIONS_LOOKUP_TABLE = new HashMap<String, ReservedFunctionsIdentifierDataTypePair>();

    static {
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("@", new ReservedFunctionsIdentifierDataTypePair("FUNCTION_DECLARATION", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("->", new ReservedFunctionsIdentifierDataTypePair("READ_INPUT", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(":", new ReservedFunctionsIdentifierDataTypePair("PRINT", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("#", new ReservedFunctionsIdentifierDataTypePair("DECLARATION", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("?", new ReservedFunctionsIdentifierDataTypePair("IF", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("~", new ReservedFunctionsIdentifierDataTypePair("WHILE", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("++", new ReservedFunctionsIdentifierDataTypePair("INCREMENT", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("--", new ReservedFunctionsIdentifierDataTypePair("DECREMENT", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<-", new ReservedFunctionsIdentifierDataTypePair("RETURN", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("+", new ReservedFunctionsIdentifierDataTypePair("ADD", "int"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("-", new ReservedFunctionsIdentifierDataTypePair("SUBTRACT", "int"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("*", new ReservedFunctionsIdentifierDataTypePair("MULTIPLY", "int"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("/", new ReservedFunctionsIdentifierDataTypePair("DIVIDE", "int"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("%", new ReservedFunctionsIdentifierDataTypePair("MODULO", "int"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("=", new ReservedFunctionsIdentifierDataTypePair("ASSIGNMENT", "non"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("==", new ReservedFunctionsIdentifierDataTypePair("EQUALS", "bln"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<", new ReservedFunctionsIdentifierDataTypePair("LESS THAN", "bln"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<=", new ReservedFunctionsIdentifierDataTypePair("LESS THAN OR EQUAL TO", "bln"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(">", new ReservedFunctionsIdentifierDataTypePair("GREATER THAN", "bln"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(">=", new ReservedFunctionsIdentifierDataTypePair("GREATER THAN OR EQUAL TO", "bln"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("!=", new ReservedFunctionsIdentifierDataTypePair("NOT EQUAL TO", "bln"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("&&", new ReservedFunctionsIdentifierDataTypePair("AND", "bln"));
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("||", new ReservedFunctionsIdentifierDataTypePair("OR", "bln"));
    }
}