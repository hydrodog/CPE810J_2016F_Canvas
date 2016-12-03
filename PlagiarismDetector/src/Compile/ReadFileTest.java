package Compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileTest {
	public static void main(String[] args){
		String direction = "E:HelloWorld";
		BufferedReader input = null;
		try{
			input = new BufferedReader(new FileReader(new File(direction+".java")));
			StringBuilder output = new StringBuilder();
			String s;
			while((s = input.readLine()) != null){
				output.append(s);
				System.out.println(s);
			}
			System.out.println(output);
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e_) {
					// TODO Auto-generated catch block
					e_.printStackTrace();
				}
			}
		}
		
	}
}
