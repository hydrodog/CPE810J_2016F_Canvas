/*
 * Author: Su Pengyu
 */
package Compile;

import java.io.IOException;
import java.net.URI;
import javax.tools.SimpleJavaFileObject;

public class JavaStringObject extends SimpleJavaFileObject{
	private String code;
	public JavaStringObject(String name, String code){
		super(URI.create(name + ".java"), Kind.SOURCE);
		this.code = code;
	}
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException{
		return code;
	}
}
