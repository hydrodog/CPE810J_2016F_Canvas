/*
 * Author: Su Pengyu
 * .java, .cpp and .py file need different ways to compile as
 * a executable file for computer. So we define a Compile 
 * interface to form their common method.
 */
package compile;

import java.io.File;

public interface Compile {
	
	public abstract void compileSourceCode();

}
