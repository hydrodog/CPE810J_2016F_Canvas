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
	private File file;
	
	public Reader(File file) {
		this.file = file;
	}
	
	
	public byte[] getBytes() {
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
