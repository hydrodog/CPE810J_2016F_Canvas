/*
* This is an abstract class to be extended.
* In this week, our team complete the methods in this class in different files and then merge them together. 
*/
abstract class Code {
	private double score;
	private String path;
	private String comment;

	code() {}
	
	public void compile() { // this method will compile the code using cmd
	}
	
	
	public void display() { // this method will display the code
		
	}
	 
	public void setComments() { // allow the grader to comment the code and write the comment into a file.
		
	}

	public void grade() { // allow the grader to grade and write the grade into a file.
		
	}

	public void update() { // update the comments file and grade file.

	}
}



