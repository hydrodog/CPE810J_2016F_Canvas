package binaryCodeCompare;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaClassReaderTest {
	public static void main(String[] args) throws IOException{
		File f = new File("e:/temp/HelloWorld.class");
		FileInputStream input = null;
			input = new FileInputStream(f);
		DataInputStream data = new DataInputStream(input);
		StringBuilder b = new StringBuilder();
		String s;
		while((s = data.readLine()) != null){
			b.append(s);
		}
		System.out.println(b);
	}
	
	
	
	
	
	
	
	
	
	
	
}
