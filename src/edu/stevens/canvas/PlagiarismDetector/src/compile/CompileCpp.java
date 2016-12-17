/*
 * Author: Su Pengyu
 * CompileCpp class implements from Compile interface.
 * 
 * It's contains essential data and method for compile a 
 * cpp file into .obj file.
 * 
 * In order to compile .cpp file, we need a c++ 
 * compiler. In this case, we use default compiler 
 * in Visual Studio 2015. Before use this program, 
 * user need to set the path and environment for Command-Line
 * Buildings. See details in "README.md" under compile package.
 */
package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompileCpp implements Compile{
	private File file;
	
	public CompileCpp(File file) {
		this.file = file;
	}

	@Override
	public void compileSourceCode () {
		// TODO Auto-generated method stub
		String path = file.getPath().replaceFirst(file.getName(),"");
		String command = "cmd /c vcvarsall.bat&&" + path + 
				"&&cl /EHsc " + file.getName();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}