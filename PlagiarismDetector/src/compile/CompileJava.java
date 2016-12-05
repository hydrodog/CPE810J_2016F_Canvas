/*
 * Author: Su Pengyu
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
	
	private File file;
	private String direction;
	
	public CompileJava(File file, String direction){
		this.file = file;
		this.direction = direction;
	}

	@Override
	public void compileSourceCode() {
		// TODO Auto-generated method stub
		
		//Reader class can return a string contains code;
		Reader reader = new Reader();
		String code = reader.getCode(file);
		String name = file.getName();
		
		
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		JavaFileObject javaFileObject = new JavaStringObject(name,code);
		CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d",direction), null, Arrays.asList(javaFileObject));
		boolean success = task.call();
		if(! success){
			System.out.println(name + " Compilation fail!");
		}else{
			System.out.println(name + " Compilation success!");
			try {
				URL[] urls = new URL[]{
						new URL("file:/" + direction)
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
}