import java.util.List;
import java.util.Arrays;

public class DataTypes {
    public static String INT = "int";
    public static String FLT = "flt";
    public static String STR = "str";
    public static String BLN = "bln";
    public static String NON = "non";
    public static String COD = "cod";
    public static String FN  = "fn";
    public static String ANY  = "any";
    public static String IDENTIFIER  = "identifier";
    public static String IDENTIFIER_UNINITIALIZED  = "identifierUninitialized";
    public static String DATA_TYPE  = "dataType";

    public static List<String> LIST = Arrays.asList(DataTypes.INT, DataTypes.STR, DataTypes.NON, DataTypes.FN, DataTypes.IDENTIFIER, DataTypes.DATA_TYPE, DataTypes.BLN);
}