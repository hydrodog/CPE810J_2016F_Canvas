/*
* Author: Tao Jiang, Weihao Cheng, Yucheng Xie
* Description: This file contain a child class of abstract code class, we split our project into 3 different parts
* 		and then merge them together.
*/
import java.io.*;
import java.util.*;
/*
* This class has the method to compile and display the code file, set the grade and comments, 
* and return two files (grade and comments) by the update method.
*/
/*
* Week 3: design the GUI of this part. And it can be split into parts:
	  1. Display Area: display the code;
	  2. Run Button: for grader to test the code;
	  3. Result Display: display the result;
	  4. Grade Area: input the score and generate a grade file;
	  5. Comment Area: text the comments and generate a comment file;
*/
public class JavaCode {
	private String path;
	private double score;
	private String comments;
	
	//constructor, has not finished yet
	JavaCode() {
		super();
	}
	//get the path of this code file (Neeed to discuss with the unzip team about it)
	public String getPath() {
		return this.path;
	}	
	public double getScore() {
		return this.score;
	}
	public void setScore(double scr) {
		this.score = scr;
	}
	
	// compile java code using cmd.exe
	public void compile() {
		try {
		    Process pro = Runtime.getRuntime().exec("javac Main.java"); //need to modify to get the path of the file.
		    String line = null;
		    BufferedReader in = new BufferedReader(
			    new InputStreamReader(pro.getErrorStream()));
		    while ((line = in.readLine()) != null) { //display the error location of compiling
			System.out.println("javac Main.java" + " The error : " + " " + line); 
		    }
		    pro.waitFor();
		    System.out.println("javac Main.java" + " exitValue() " + pro.exitValue()); //show "0" if compile successfully


		    pro = Runtime.getRuntime().exec("java Main");
		    line = null;
		    in = new BufferedReader(
			    new InputStreamReader(pro.getInputStream()));
		    while ((line = in.readLine()) != null) { //display the output of the java program in console
			System.out.println("java Main" + " The output of this program : " + " " + line);
		    }
		    line = null;
		    in = new BufferedReader(
			    new InputStreamReader(pro.getErrorStream()));
		    while ((line = in.readLine()) != null) { //display the error location when running the java program
			System.out.println("java Main" + " The error : " + " " + line);
		    }
		    pro.waitFor();
		    System.out.println("java Main" + " exitValue() " + pro.exitValue()); //show "0" when running successfully
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	//display the code in console (GUI group can use it to display it into a textarea)
	public void display() {    
		BufferedReader in;
		try {
		    in = new BufferedReader(new FileReader(this.getPath()));
		    String strLine;
		    while ((strLine = in.readLine()) != null) { //display the source code in console
			System.out.println(strLine);
		    }
		    in.close();
		} catch (Exception e) {
		    System.err.println("Error: " + e.getMessage());
		}
	}
	
	// allow the grader to input the comment. 
	public void setComments() {
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the comments: ");
		this.comments = input.nextLine();
	}
	
	// allow the grader to grade the code.
	public void setGrade() {
		String repeatChar = "";
		do{
			Scanner input = new Scanner(System.in);
			System.out.print("Please enter the grade: ");
			this.score = input.nextDouble();
			System.out.println("The grade you set is " + score + " (enter t/T for Ture or f/F for False).");
			repeatChar = input.next();//make sure it is the ture score grader give
		} while (repeatChar.charAt(0) == 'F' || repeatChar.charAt(0) == 'f'); 
	}
	
	// create two files for comments and grade and write into respectively.
	public void update() throws IOException { 
		FileWriter comt = new FileWriter("comment.txt");//input the comments into a .txt file
		BufferedWriter comWriter = new BufferedWriter(comt);
		comWriter.write(this.comments);
		comWriter.flush();
		comWriter.close();
		FileWriter grad = new FileWriter("grade.txt");//input the grade into a .txt file
		BufferedWriter gradWriter = new BufferedWriter(grad);
		gradWriter.write(this.score + "");
		gradWriter.flush();
		gradWriter.close();
	}
// 	public static void main(String[] args) throws IOException { //this is the test code, we could run it successfully.
// 		JavaCode java = new JavaCode();
// 		java.display();
// 		java.compile();
// 		java.setComments();
// 		java.setGrade();
// 		java.update();
//     	}
}
