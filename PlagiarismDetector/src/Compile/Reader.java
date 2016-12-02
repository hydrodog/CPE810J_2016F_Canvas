package Compile;

import java.util.*;
import java.io.*;

public class Reader {
	private StringBuilder s = new StringBuilder();
	private String type;
	
	public Reader(File sourceFile, String type) throws Exception{
		this.type = type;
		Scanner input = new Scanner(sourceFile);
		while(input.hasNext()){
			s.append(input.next());
		}
		input.close();
	}
	
	public String getString(){
		return s.toString();
	}
	
	public String getType(){
		return type;
	}
}
