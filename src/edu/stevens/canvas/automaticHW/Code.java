/*
* This is an abstract class to be extended.
* In this week, our team complete the methods in this class in different files and then merge them together. 
*/
abstract class Code {
	private double score;
	private String path;
	private String comments;

	Code() {}
	
	public void compile() { // this method will compile the code using cmd.
	}
	
	
	public void display() { // this method will display the code.
		
	}
	 
	public void setComments() { // allow the grader to comment the code.
		
	}

	public void setGrade() { // allow the grader to grade the code.
		
	}

	public void update() { // create two files for comments and grade and write into respectively.

	}
}



