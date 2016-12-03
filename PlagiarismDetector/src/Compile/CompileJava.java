package Compile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class CompileJava extends Compile{
	
	private String code;
	
	public CompileJava(String code){
		this.code = code;
	}

	@Override
	public void CompileSourceCode() {
		// TODO Auto-generated method stub
		Reader reader = new Reader("java","e:", "HelloWorld");
		String code = reader.getCode();
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		JavaFileObject fileObject = new JavaStringObject()
	}
	
	@Override
	public String compiledFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
}