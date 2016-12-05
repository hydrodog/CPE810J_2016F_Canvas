package binaryCodeCompare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JavaClassReaderTest {
	public static void main(String[] args){
		File f = new File("e:/temp/HelloWorld.class");
		BufferedReader input = null;
		StringBuilder s = new StringBuilder();
		try{
			input = new BufferedReader(new FileReader(f));
			String s_;
			while((s_ = input.readLine()) != null){
				s.append(s_);
			}
			input.close();
		}catch(IOException e){	
			e.printStackTrace();
		}
		System.out.println(s);
	}
	
	
	
	
	
	
	
	
	
	
	
}
