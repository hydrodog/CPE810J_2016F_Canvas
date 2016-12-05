/*
 * Author: Su Pengyu
 */
package Compile;

public class Test {
	public static void main(String[] args){
		CompileJava c = new CompileJava("HelloWorld","e:");
		c.compileSourceCode();
		System.out.println("hi");
	}
}
