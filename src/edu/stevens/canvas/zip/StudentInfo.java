package edu.stevens.canvas.zip;

import java.io.*;

public class StudentInfo implements Serializable {
	private static final long serialVersionUID = 4766620764257438232L;
	String name;
	int stuID;
	String email;
	String hw; // abstract path

	public StudentInfo(String name, int stuID, String email, String hw) {
		this.name = name;
		this.stuID = stuID;
		this.email = email;
		this.hw = hw;
	}
}