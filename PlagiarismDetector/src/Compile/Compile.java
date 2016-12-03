package Compile;

public abstract class Compile {
	private String direction;
	private char type;
	
	public Compile(String direction, String type){
		this.direction = direction;
		if(type.equals("java")){
			this.type = 'j';
		}else if(type.equals("c++")){
			this.type = 'c';
		}else if(type.equals("python")){
			this.type = 'p';
		}
	}
	
	public abstract String readFile();
	
	public abstract String compiledFile();
}
