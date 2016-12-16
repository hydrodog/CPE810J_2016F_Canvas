//package ui;

import java.io.File;

public abstract class father {

	private String path, fileName;
	private File file;
	
    public abstract void compile(String path, String fileName, File file);

}
