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
		byte[] b = new byte[data.available()];
		data.readFully(b);
		for(int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
