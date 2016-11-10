import java.io.*;
import java.util.*;
class JavaCode extends Code {
	private String path;
	private double score;
	private String comments;
	public String getPath() {
		String 
	}
	JavaCode() {
		super();
	}
	public double getScore() {
		return this.score;
	}
	public void setScore(double scr) {
		this.score = scr;
	}
	public String getSourceCode() {
		return this.sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public void compile() {         // compile the code using cmd
	}
	public void display() {  
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
		}                                    //** Interface 3 to plagiarism team.
		//String str = this.getSourceCode();
		// display the source code.
	}
	public double score(double score) {
		this.score = score;
		// allow the grader to grade the code based on the result. 
	}
	
	public void setComments() {
			Scanner input = new Scanner(System.in);
			System.out.print("Please enter the comments: ");
			this.comment = input.nextLine();
			//System.out.println("The comment you set is " + comment + " (enter t/T for Ture or f/F for False).");
			//repeatChar = input.next();
		// allow the grader to comment the code and write the comment into a file. 
	}
	public void grade() {
		String repeatChar = "";
		do{
			Scanner input = new Scanner(System.in);
			System.out.print("Please enter the grade: ");
			this.score = input.nextDouble();
			System.out.println("The grade you set is " + score + " (enter t/T for Ture or f/F for False).");
			repeatChar = input.next();
		} while (repeatChar.charAt(0) == 'F' || repeatChar.charAt(0) == 'f');
		// allow the grader to comment the code and write the comment into a file. 
	}
	public void update() throws IOException { 
		FileWriter comt = new FileWriter("comment.txt");
		BufferedWriter comWriter = new BufferedWriter(comt);
		comWriter.write(this.comment);
		comWriter.flush();
		comWriter.close();
		FileWriter grad = new FileWriter("grade.txt");
		BufferedWriter gradWriter = new BufferedWriter(grad);
		gradWriter.write(this.grade + "");
		gradWriter.flush();
		gradWriter.close();
		// update the comments file and grade file.
		// the graph group use the grade file to read and graph it  ** Interface 2
	}  
		// update the comments file and grade file.
		// the graph group use the grade file to read and graph it  ** Interface 2
	}

}