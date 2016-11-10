import java.util.*;
import java.io.*;

class CppCode extends Code {
	
	public CppCode() {
		super();
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
	
	public void display() {                     //** Interface 3 to plagiarism team.
		//String str = this.getSourceCode();
		// display the source code.
	}
	
	public void setComments() {
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the comments: ");
		this.comment = input.nextLine();
		// allow the grader to comment the code and write the comment into a file. 
	}
	
	public void setGrade() {
		String repeatChar = "t";
		do{
			Scanner input = new Scanner(System.in);
			System.out.print("Please enter the grade: ");
			this.score = input.nextDouble();
			System.out.println("The grade you set is " + score + " (enter t/T for Ture or f/F for False).");
			repeatChar = input.next();
		}while(repeatChar.charAt(0) == 'F' || repeatChar.charAt(0) == 'f');
		// update the grade file 
	}
	
	public void update() throws IOException {
		FileWriter fwc = new FileWriter("comment.txt");
		BufferedWriter bufwc = new BufferedWriter(fwc);
		bufwc.write(comment);
		bufwc.flush();
		bufwc.close();
		FileWriter fwg = new FileWriter("grade.txt");
		BufferedWriter bufwg = new BufferedWriter(fwg);
		bufwg.write(score + "");
		bufwg.flush();
		bufwg.close();
		// update the comments file and grade file.
		// the graph group use the grade file to read and graph it  ** Interface 2
	}
}
