package edu.stevens.canvas.zip;

import java.io.Serializable;

public class PassStudent implements Serializable {
	private static final long serialVersionUID = -7424902928604706598L;
	String folder;
	String filePath; // under the folder
	String name;
	int CWID;

	public PassStudent(String folder, String filePath, String name, int CWID) {
		this.folder = folder;
		this.filePath = filePath;
		this.name = name;
		this.CWID = CWID;
	}
}