package ui;

import java.io.File;

abstract public class father {

	private String path, fileName;
	private File file;
	
    abstract public void compile(String path, String fileName, File file);

}