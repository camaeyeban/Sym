public class IdEntry {

	private String name;
	private String dataType;
	private int blockLevel;
	private int scope;			// 0 = global, 1 = parameter, 2 = local
	private int offset;

	IdEntry(String name, int blockLevel){
		this.name = name;
		this.blockLevel = blockLevel;
	}

	public String getName(){
		return this.name;
	}

	public String getDataType(){
		return this.dataType;
	}
	
	public int getBlockLevel(){
		return this.blockLevel;
	}
	
	public int getScope(){
		return this.scope;
	}
	
	public int getOffset(){
		return this.offset;
	}
	
	public void setDataType(String dataType){
		this.dataType = dataType;
	}

	public void setScope(int scope){
		this.scope = scope;
	}

	public void setOffset(int offset){
		this.offset = offset;
	}

}