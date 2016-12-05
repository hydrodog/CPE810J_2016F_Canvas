package binaryCodeCompare;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaClassReaderTest {
	public static void main(String[] args) throws Exception{
		File f = new File("e:/temp/HelloWorld.class");
		FileInputStream input = null;
			input = new FileInputStream(f);
		DataInputStream data = new DataInputStream(input);
		StringBuilder s = new StringBuilder();
		while(data.available() > 0){
			s.append(data.readByte()+ "\n");
		}
		System.out.println(s);
		System.out.println(s.length());
	}
	
	
	
	
	
	
	
	
	
	
	
}
