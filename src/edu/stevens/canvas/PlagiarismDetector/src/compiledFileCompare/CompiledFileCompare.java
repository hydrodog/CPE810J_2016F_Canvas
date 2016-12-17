package compiledFileCompare;

import java.io.File;

public abstract class CompiledFileCompare {
	protected File[] filePair = new File[2];
	protected String type;
	
	public CompiledFileCompare(File[] filePair, String type) {
		this.filePair = filePair;
		this.type = type;
	}
	
	public abstract double similarity();
}
