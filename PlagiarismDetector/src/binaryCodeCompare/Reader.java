/*
 * Author: Su Pengyu
 * Reader class can read a binary file and change data
 * into Array<byte>.
 */
package binaryCodeCompare;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader {
	private String name;  //File name
	private String direction;  // File direction
	private String type;  //File type
	
	public Reader(String name, String direction, String type) {
		this.name = name;
		this.direction = direction;
		this.type = type;
	}
	
	
	public byte[] getBytes() {
		File file = new File(direction + name);
		FileInputStream input = null;
		byte[] bytes = null;
		try {
			input = new FileInputStream(file);
			DataInputStream data = new DataInputStream(input);
			bytes = new byte[data.available()];
			data.readFully(bytes);  //DataInputStream.readFully returns all content as an Array<byte>
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
