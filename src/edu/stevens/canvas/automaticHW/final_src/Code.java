import java.io.File;

/*
* This is an abstract class to be extended.
* To provide the abstract method compile(). 
*/

public abstract class Code {

	private String path, fileName;
	private File file;
	
    public abstract void compile(String path, String fileName, File file);

}
