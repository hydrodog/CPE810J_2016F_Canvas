package Compile;

import java.util.*;
import java.io.*;
import java.lang.reflect.Method;
import sun.tools.java.MethodType;


public class CompileJava implements CompileString{
	private Reader r;
	private String initialString;
	private String sourceCode;
	
	CompileJava(String sourceFile){
		this.sourceCode = sourceCode;	
	}
	
	@Override
	public void compileSourceCode() throws Exception{
		// TODO Auto-generated method stub
		String className = "Test";
		String path = "bin";
		@SuppressWarnings("restriction")
		com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
		
		String[] args = new String[]{
				"-d",
				path,
				"-classpath",
				"%classpath%;" + path + ";",
				"-encoding",
				"utf-8",
				".java"
		};
		
		@SuppressWarnings("restriction")
		int status = javac.compile(args);
		if(status != 0){
			System.out.println(sourceFile + ""have not compile sucessfully!);
		}else{
			try{
				Class cls = Class.forName(className);
				Method main = cls.getMethod("t", new Class[]{String[].class});
				main.invoke(null, new Object[] {new String[0]});
				
			}catch (SecurityException se){}
		}
	}
	
	public void createTempFile(String className) throws Exception{
		PrintWriter output = new PrintWriter(new File(className + ".java"));
		output.write(initialString);
		output.close();
	}
	
	
}
