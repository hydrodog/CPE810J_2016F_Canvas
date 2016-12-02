package Compile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import Compile.JavaStringObject;

public class DynamicCompileTest {
	public static void main(String[] args) throws Exception{
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        out.println("package Compile;");
        out.println("public class Hello{");
        out.println("public static void main(String[] args){");
        out.println("System.out.println(\"Hi HelloWorld!!\");");
        out.println("}");
        out.println("}");
        out.flush();
        out.close();
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        JavaFileObject fileObject = new JavaStringObject("Hello", writer.toString());
        CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d","./bin"),null,Arrays.asList(fileObject));
        boolean success = task.call();
        if(! success){
        	System.out.println("Fail!");
        }else{
        	System.out.println("Sucess!");
        	URL[] urls = new URL[]{new URL("file:/"+"./bin")};
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class class1 = classLoader.loadClass("Compile.Hello");
            Method method = class1.getDeclaredMethod("main", String[].class );
            String[] args1 = {null};
            method.invoke(class1.newInstance(), args1);
        }
    }
}

