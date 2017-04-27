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
        "||",
        "!"
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
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("@",
            new ReservedFunctionsIdentifierDataTypePair("FUNCTION_DECLARATION", "non", new String[] {
                DataTypes.IDENTIFIER_UNINITIALIZED, DataTypes.DATA_TYPE, DataTypes.FN
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("->",
            new ReservedFunctionsIdentifierDataTypePair("READ_INPUT", "non", new String[] {
                DataTypes.IDENTIFIER
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(":",
            new ReservedFunctionsIdentifierDataTypePair("PRINT", "non", new String[] {
                DataTypes.STR
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("#",
            new ReservedFunctionsIdentifierDataTypePair("DECLARATION", "non", new String[] {
                DataTypes.IDENTIFIER_UNINITIALIZED, DataTypes.DATA_TYPE
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("?",
            new ReservedFunctionsIdentifierDataTypePair("IF", "non", new String[] {
                DataTypes.BLN, DataTypes.COD, DataTypes.COD
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("~",
            new ReservedFunctionsIdentifierDataTypePair("WHILE", "non", new String[] {
                DataTypes.BLN, DataTypes.COD
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("++",
            new ReservedFunctionsIdentifierDataTypePair("INCREMENT", "non", new String[] {
                DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("--",
            new ReservedFunctionsIdentifierDataTypePair("DECREMENT", "non", new String[] {
                DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<-",
            new ReservedFunctionsIdentifierDataTypePair("RETURN", "non", new String[] {
                DataTypes.ANY
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("+",
            new ReservedFunctionsIdentifierDataTypePair("ADD", "int", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("-",
            new ReservedFunctionsIdentifierDataTypePair("SUBTRACT", "int", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("*",
            new ReservedFunctionsIdentifierDataTypePair("MULTIPLY", "int", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("/",
            new ReservedFunctionsIdentifierDataTypePair("DIVIDE", "int", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("%",
            new ReservedFunctionsIdentifierDataTypePair("MODULO", "int", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("=",
            new ReservedFunctionsIdentifierDataTypePair("ASSIGNMENT", "non", new String[] {
                DataTypes.IDENTIFIER, DataTypes.ANY
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("==",
            new ReservedFunctionsIdentifierDataTypePair("EQUALS", "bln", new String[] {
                DataTypes.ANY, DataTypes.ANY
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<",
            new ReservedFunctionsIdentifierDataTypePair("LESS THAN", "bln", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("<=",
            new ReservedFunctionsIdentifierDataTypePair("LESS THAN OR EQUAL TO", "bln", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(">",
            new ReservedFunctionsIdentifierDataTypePair("GREATER THAN", "bln", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put(">=",
            new ReservedFunctionsIdentifierDataTypePair("GREATER THAN OR EQUAL TO", "bln", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("!=",
            new ReservedFunctionsIdentifierDataTypePair("NOT EQUAL TO", "bln", new String[] {
                DataTypes.INT, DataTypes.INT
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("&&",
            new ReservedFunctionsIdentifierDataTypePair("AND", "bln", new String[] {
                DataTypes.BLN, DataTypes.BLN
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("||",
            new ReservedFunctionsIdentifierDataTypePair("OR", "bln", new String[] {
                DataTypes.BLN, DataTypes.BLN
            })
        );
        RESERVED_FUNCTIONS_LOOKUP_TABLE.put("!",
            new ReservedFunctionsIdentifierDataTypePair("NOT", "bln", new String[] {
                DataTypes.BLN
            })
        );
    }
}