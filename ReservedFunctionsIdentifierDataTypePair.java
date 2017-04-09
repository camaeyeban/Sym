public class ReservedFunctionsIdentifierDataTypePair {
    private String identifier;
    private String dataType;

    public ReservedFunctionsIdentifierDataTypePair(String identifier, String dataType) {
        this.identifier = identifier;
        this.dataType = dataType;
    }

    public String getIdentifier(){
        return this.identifier;
    };
    public String getDataType(){
        return this.dataType;
    };
}