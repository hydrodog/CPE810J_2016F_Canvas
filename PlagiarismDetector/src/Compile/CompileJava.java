package Compile;

import java.util.*;
import java.io.*;
import java.lang.reflect.Method;


public class CompileJava implements Compile{
	private Reader r;
	private String initialString;
	CompileJava(File sourceFile) throws Exception{
		r = new Reader(sourceFile,"java");
		initialString = r.getString();
	}
	
	@Override
	public void compileSourceCode() throws Exception{
		// TODO Auto-generated method stub
		String className =" ";
		String path = "bin";
		createTempFile(className);
		
		com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
		String[] args = new String[]{
				"-d",
				path,
				"-classpath",
				"%classpath%;"
				+path
				+";",
				"-encoding",
				"utf-8",
				filePath + "\\" + fileName +".java"
		};
		
		int status = javac.compile(args);
		if(status != 0){
			System.out.println(sourceFile + ""have not compile sucessfully!);
		}else{
			try{
				Class cls = Class.forName(className);
				Method main = cls.getMethod("t", new Class[]{String[].class});
				main.invoke(null, new Object[] {String[0]});
				
			}catch (SecurityException se){}
		}
	}
	
	public void createTempFile(String className) throws Exception{
		PrintWriter output = new PrintWriter(new File(className + ".java"));
		output.write(initialString);
		output.close();
	}
	
	
}
