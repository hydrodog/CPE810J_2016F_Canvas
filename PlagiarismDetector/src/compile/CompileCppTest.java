/*
 * Author: Su Pengyu
 */
package compile;

import java.io.File;

public class CompileCppTest {
	public static void main(String[] args) {
		File f = new File("e:HelloWorld.cpp");
		CompileCpp c = new CompileCpp(f);
		c.compileSourceCode();
	}
}
