//class 
import java.io.*;
class Code {
	private double score;
	private String sourceCode;
	private String comment;

	code() {}
	public void compile() {         // compile the code using cmd
	}
	public void display() {  
		try {
			FileInputStream fstream = new FileInputStream("/Users/gingerbread/Documents/C_automaticHW/Code.java");
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
		//** Interface 3 to plagiarism team.
		//String str = this.getSourceCode();
		// display the source code.
	}
	public void setComments() {
		// allow the grader to comment the code and write the comment into a file. 
	}
	public void grade() {
		//double temp = this.getScore();
		// update the grade file 
	}
	public void update() {   
		// update the comments file and grade file.
		// the graph group use the grade file to read and graph it  ** Interface 2
	}
}
public class Test {
	public static void main(String args[]) {
		Code c = new code();
		c.display();
	}
}


