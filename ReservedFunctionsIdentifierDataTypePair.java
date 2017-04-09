public class ReservedFunctionsIdentifierDataTypePair {
    private String identifier;
    private String dataType;
    private String[] argsDataType;

    public ReservedFunctionsIdentifierDataTypePair(String identifier, String dataType, String[] argsDataType) {
        this.identifier = identifier;
        this.dataType = dataType;
        this.argsDataType = argsDataType;
    }

    public String getIdentifier(){
        return this.identifier;
    };

    public String getDataType(){
        return this.dataType;
    };

    public String[] getArgsDataType(){
        return this.argsDataType;
    };
}