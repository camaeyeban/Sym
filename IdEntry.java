public class IdEntry {

	private String name;
	private String dataType;
	private int blockLevel;
	private int scope;			// 0 = global, 1 = parameter, 2 = local
	private int size;
	private int offset;

	IdEntry(String name, int blockLevel, String dataType){
		this.name = name;
		this.blockLevel = blockLevel;
		this.dataType = dataType;
		this.setSize(this.dataType);
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
	
	public int getSize(){
		return this.size;
	}
	
	public int getOffset(){
		return this.offset;
	}
	
	public void setScope(int scope){
		this.scope = scope;
	}

	public void setSize(String dataType){
		int size = 0;	// cod and fn
		if(dataType.equals("int")){
			size = 4;
		}
		else if(dataType.equals("flt")){
			size = 8;
		}
		else if(dataType.equals("str")){
			size = 16;
		}
		else if(dataType.equals("bln")){
			size = 1;
		}
		this.size = size;
	}

	public void setOffset(int offset){
		this.offset = offset;
	}

}