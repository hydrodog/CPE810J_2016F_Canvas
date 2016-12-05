/*
 * Author: Su Pengyu
 */
package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	private String type;
	private String direction;
	private String name;
	
	public Reader(String name, String direction, String type){
		this.type = type;
		this.direction = direction;
		this.name = name;
	}
	
	public String getCode(){
		BufferedReader input = null;
		StringBuilder code = new StringBuilder();
		try{
			input = new BufferedReader(new FileReader(new File(direction + name + "." + type)));
			String s;
			while((s = input.readLine()) != null){
				code.append(s + "\n");
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(input != null){
					input.close();
				}
			}catch(IOException e_){
				e_.printStackTrace();
			}
		}
		return code.toString();
	}
}
