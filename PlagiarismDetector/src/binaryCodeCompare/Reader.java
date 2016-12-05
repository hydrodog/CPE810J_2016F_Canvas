/*
 * Author: Su Pengyu
 */
package binaryCodeCompare;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader {
	private String name;
	private String direction;
	private String type;
	
	public Reader(String name, String direction, String type) {
		this.name = name;
		this.direction = direction;
		this.type = type;
	}
	
	public byte[] getBytes() {
		File file = new File(direction + name + "." + type);
		FileInputStream input = null;
		byte[] bytes = null;
		try {
			input = new FileInputStream(file);
			DataInputStream data = new DataInputStream(input);
			bytes = new byte[data.available()];
			data.readFully(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return bytes;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
