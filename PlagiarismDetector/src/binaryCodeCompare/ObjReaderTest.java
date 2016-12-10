/*
 * Author: Su Pengyu
 */
package binaryCodeCompare;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class ObjReaderTest {
	public static void main(String[] args) throws Exception {
		File f = new File("e:/HelloWorld.obj");
		FileInputStream input = new FileInputStream(f);
		DataInputStream data = new DataInputStream(input);
		StringBuilder s = new StringBuilder();
		byte[] b = new byte[data.available()];
		data.readFully(b);
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
	}
}
