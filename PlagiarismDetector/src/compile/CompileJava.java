/*
 * Author: Su Pengyu
 * CompileJava class implements from Compile interface.
 * It's contains essential data and method for compile a 
 * .java file into .class file.
 */
package compile;

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
	
	private File file;  // .java file to be compiled.
	private String direction;  //Store temp .class files.
	
	public CompileJava(File file, String direction){
		this.file = file;
		this.direction = direction;
	}

	@Override
	public void compileSourceCode() {
		// TODO Auto-generated method stub
		
		/*Reader class can return a string contains code, in 
		 * this situation java code. 
		 */
		Reader reader = new Reader(); 
		String code = reader.getCode(file);
		
		/*JVM require .java file name has to be the same as public class,
		 * so we suppose that the file name is equal to public class name.
		 */
		String name = file.getName();  // File.getName() return a name contains postfix.
		name = name.substring(0, name.length() - 5);
		
		/*JavaCompiler, JavaFileObject and CompilationTask are core of dynamic 
		 *compile, which already write in package javax.tools. Here we just import 
		 *and use it.
		 */
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		JavaFileObject javaFileObject = new JavaStringObject(name,code);
		CompilationTask task = javaCompiler.getTask(null, null, null, 
				Arrays.asList("-d",direction), null, Arrays.asList(javaFileObject));  //See details in oracle documents.
		boolean success = task.call();
		if(! success){
			System.out.println(name + " Compilation fail!");
		}else{
			System.out.println(name + " Compilation success!");
			try {
				/*Automatically execute the .class file CompilationTask just made. This part is not
				 * essential in plagiarism detection but here just add this funcion.
				 */
				URL[] urls = new URL[]{
						new URL("file:/" + direction)
				};
				URLClassLoader classLoader = new URLClassLoader(urls);
				Class class_ = classLoader.loadClass(name);
				Method  method = class_.getDeclaredMethod("main", String[].class);
				String[] args_ ={null};
				System.out.println("\nBelow is the output of test file!");
				method.invoke(class_.newInstance(), args_);
				System.out.println("Test file output end!\n");
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
}