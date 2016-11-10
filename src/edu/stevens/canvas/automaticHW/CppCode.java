/*
* Author: Weihao Cheng 
* Description: This file contain a child class of abstract code class, for .cpp file
*/
import java.util.*;
import java.io.*;
/*
* This class has the method to compile the code file, set the grade and comments, 
* and return two files(grade and comments) by the update method.
*/
class CppCode extends Code {
	private String path;
	private double score;
	private String comments;
	
	//constructor
	public CppCode() {
		super();
	}
	
	//get the path of this code file
	public String getPath() {
		return this.path;
	}
	
	public double getScore() {
		return this.score;
	}
	
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	public void compile() {         // compile the code using cmd
	}
	
	//display the code (GUI group can use it to display it into a textarea)
	public void display() {    
		try {
			FileInputStream fstream = new FileInputStream(this.getPath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}   
	}
	
	// allow the grader to comment the code and put the comment into a file. 
	public void setComments() {
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the comments: ");
		this.comment = input.nextLine();
	}
	
	// allow the grader to grade the code and put the grade into a file. 
	public void setGrade() {
		String repeatChar = "t";
		do{
			Scanner input = new Scanner(System.in);
			System.out.print("Please enter the grade: ");
			this.score = input.nextDouble();
			System.out.println("The grade you set is " + score + " (enter t/T for Ture or f/F for False).");
			repeatChar = input.next();
		}while(repeatChar.charAt(0) == 'F' || repeatChar.charAt(0) == 'f');
	}
	
	// update the comments file and grade file.
	// the graph group use the grade file to read and graph it  ** Interface 2
	public void update() throws IOException {
		FileWriter fwc = new FileWriter("comment.txt");
		BufferedWriter bufwc = new BufferedWriter(fwc);
		bufwc.write(this.comments);
		bufwc.flush();
		bufwc.close();
		FileWriter fwg = new FileWriter("grade.txt");
		BufferedWriter bufwg = new BufferedWriter(fwg);
		bufwg.write(this.score + "");
		bufwg.flush();
		bufwg.close();
	}
}
