/*
 * Author: Su Pengyu
 */
package binaryCodeCompare;

import java.io.File;

public class Writer {
	public static void main(String[] args){
		File f = new File("e:/temp/1.txt");
		Reader r1 = new Reader("Hello1","e:/temp/","class");
		Reader r2 = new Reader("Hello2","e:/temp/","class");
		byte[] b1 = r1.getBytes();
		byte[] b2 = r2.getBytes();
		System.out.println(b1.length + " " + b2.length);
		for(int i = 0; i < b1.length; i++){
			System.out.println(b1[i]- b2[i]);
		}
		String s = "123";
		
	}
	
	
	
}
