/*
 * Author: Su Pengyu
 */
package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompilePython implements Compile{
	private File file;
	
	public CompilePython(File file) {
		this.file = file;
	}
	
	@Override
	public void compileSourceCode() {
		// TODO Auto-generated method stub
		
		String path = file.getPath().replaceFirst(file.getName(),"");
		String command = "cmd /c " + path + "&&python -m py_compile " + file.getName();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			BufferedReader brTrue = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader brFalse = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line;
			while( (line = brTrue.readLine()) != null) {
				System.out.println(line);
			}
			if((line = brFalse.readLine()) != null){
				System.out.println("Error in Commond-Line!\n");
				System.out.println(line);
				while( (line = brFalse.readLine()) != null) {
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
