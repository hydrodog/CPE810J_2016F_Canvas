/**
 *  @author: Siyu Sun
 *  @modifier -
 * 
 *
 */

package edu.stevens.canvas.download;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;


//as the grade is showing in the console, I am trying to save the result in console into a txt file
public class save {
	public void save() {
		try {
			//create a buffered reader that connects to the console, we use it so we can read lines
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			//read a line from the console
			String lineFromInput = in.readLine();
			
			//create an print stream for writing to a file
			PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
			System.setOut(out);
			
			//output to the file a line	
			out.println(lineFromInput);

			//close the file 
			out.close();
			}
	      	catch(IOException e1) {
	      		System.out.println("Error during reading/writing");
	      	}
	}
}


