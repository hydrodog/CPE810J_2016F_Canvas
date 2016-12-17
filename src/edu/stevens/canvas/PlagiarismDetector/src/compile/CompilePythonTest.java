/*
 * Author: Su Pengyu
 */
package compile;

import java.io.File;


public class CompilePythonTest {
	public static void main(String[] args) {
		File f = new File("e:1.py");
		CompilePython c = new CompilePython(f);
		c.compileSourceCode();
	}
}
