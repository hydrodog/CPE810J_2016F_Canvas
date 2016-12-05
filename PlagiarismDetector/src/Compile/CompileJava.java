/*
 * Author: Su Pengyu
 */
package Compile;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class CompileJava implements Compile{
	
	// A CompileJava class need to store two datas: name and direction.
	private String name;
	private String direction;
	
	public CompileJava(String name, String direction){
		this.name = name;
		this.direction = direction;
	}

	@Override
	public void compileSourceCode() {
		// TODO Auto-generated method stub
		
		//Reader class can return a string contains code;
		Reader reader = new Reader("java",direction, name);
		String code = reader.getCode();
		
		
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		JavaFileObject javaFileObject = new JavaStringObject(name,code);
		CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d","e:/temp"), null, Arrays.asList(javaFileObject));
		boolean success = task.call();
		if(! success){
			System.out.println("Compilation fail!");
		}else{
			System.out.println("Compilation success!");
			try {
				URL[] urls = new URL[]{
						new URL("file:/e:/temp/")
				};
				URLClassLoader classLoader = new URLClassLoader(urls);
				Class class_ = classLoader.loadClass(name);
				Method  method = class_.getDeclaredMethod("main", String[].class);
				String[] args_ ={null};
				method.invoke(class_.newInstance(), args_);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String compiledFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
}