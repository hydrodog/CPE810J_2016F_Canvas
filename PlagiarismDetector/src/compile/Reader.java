/*
 * Author: Su Pengyu
 */
package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	
	public String getCode(File file){
		BufferedReader input = null;
		StringBuilder code = new StringBuilder();
		try{
			input = new BufferedReader(new FileReader(file));
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
