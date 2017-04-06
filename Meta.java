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
    }
}