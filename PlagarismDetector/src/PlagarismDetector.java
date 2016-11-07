
//There are several characteristics to consider plagarism with similarity to another file:
//1. Word for word.  That is if they submitted the same file
//2. Context.  Did they just submit the same thing with new variable names
//3. Function.  Did it do the exact same thing
//4. Byte Code.  Did it compile down to the same thing
//5. Pattern Irregularity.  Is this style of coding out of the ordinary for the student.

import java.util.*;
import java.io.*;

public class PlagarismDetector {
	//Looks for words and lines for parsing
	private ArrayList<FileReader> files;
	private int overallScore;
	
	public PlagarismDetector()
	{
		files = new ArrayList<FileReader>();
		overallScore = 0;
	}
	
	public int getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(int overallScore) {
		this.overallScore = overallScore;
	}
	class FileParser implements Comparable<FileParser>
	{
		protected FileReader file1;
		//Overall score of file similarity from a string of characters standpoint
		public int fileSimilarityScore (FileReader file)
		{
			return 0;
		}
		//Retrieves a single line of code
		public String retriveLine (FileReader file)
		{
			return null;
		}
		public String retriveLoop (FileReader file)
		{
			return null;
		}
		public String retriveIfElse (FileReader file)
		{
			return null;
		}
		//Shows if a student noted the name of who they received help from
		//Will be preceded by a // and a proper noun
		//Return true if they noted the student who received help, false if not found
		public boolean studentNotedHelp (FileReader file)
		{
			return false;
		}
		//Returns the line number where the segement came from, otherwise return -1 if not found
		public int indexOfLine (String line)
		{
			return 0;
		}

		//Method of comparable.  To be filled in to add comparing one file context to another.
		@Override
		public int compareTo(FileParser arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	//Looks for function and similarity of variable use, i.e. did they change variable names but keep everything else the same
	class variableEditor implements Comparable<variableEditor>
	{
		public ArrayList<String> variableNames = new ArrayList<String>();
		public ArrayList<String> variableTypes = new ArrayList<String>();
		
		//Retrieve all variables names from the file
		public ArrayList<String> retrieveVariableNames (FileReader file)
		{
			return null;
		}
		//Retrieve all variable data types, both implicit and explicitly defined
		//Does not count variables re-cast for a moment, just variable types initially put on the stack at initialization
		public ArrayList<String> retrieveVariableTypes (FileReader file)
		{
			return null;
		}
		//Searches for a variable by Name
		public boolean variableExistsByName (FileReader file, String name)
		{
			return false;
		}
		//Searches for a variable with a name and type
		public boolean variableExistsByNameAndType (FileReader file, String name, String type)
		{
			return false;
		}
		//Overall score of variable similarity
		//Does grade whether the same types were used and only names were changed
		public int variableScore (FileReader file)
		{
			 return 0;
		}
		@Override
		public int compareTo(variableEditor o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	//Does the function do the exact same thing.  i.e. while(x<0) and while(x>=1) may be the same thing
	class FunctionUse extends FileParser
	{
		//Reduces loop down to a set of instructions, i.e. i<1 and i <=0 is the same thing for two loops
		public String loopContext(FileReader file)
		{
			return null;
		}
		//Reduces ifElse conditions down to similar instruction sets
		public String ifElseContext (FileReader file)
		{
			return null;
		}
		//Reduces file to a set of simple instructions
		public void instructionPerformance (FileReader file)
		{
			
		}
		@Override
		public int fileSimilarityScore (FileReader file)
		{
			return 0;
			
		}
	}
	
	//Potentially parses compiled byte code of the function. This shows if the logic between programs is the same 
	class ByteCodeTesting extends FileParser
	{
		//Compiles code down to byte code
		public FileReader compileCode (FileReader file)
		{
			return null;
		}
		@Override
		//Uses different methods
		public int fileSimilarityScore (FileReader file)
		{
			return 0;
		}
		@Override
		//Loops are different syntax
		public String retriveLoop (FileReader file)
		{
			return null;
		}
		@Override
		//If else will be slightly different syntax
		public String retriveIfElse (FileReader file)
		{
			return null;
		}
	}
	
	//Looks for irregularities in the overall 
	class StudentPatterns
	{
		//Typical loop variables used (some use i, some use x)
		public ArrayList<String>loopNames(FileReader file)
		{
			return null;
		}
		//Does the student usually use spaces between +, -, etc.
		public boolean spacingBetweenSymbols (FileReader file)
		{
			return false;
		}
		//Does the student usually use spaces between <, >, etc.
		public boolean spacingBetweenComparators (FileReader file)
		{
			return false;
		}
		//-1 if tabs, 0, if nothing, 1 if spaces
		public int tabsOrSpaces (FileReader file)
		{
			return 0;
		}
		//Bracket positions.  -1 if top inline with function name, 0 if \n { followed by code, 1 if separate lines
		public int bracketPositioning (FileReader file)
		{
			return 0;
		}
	}
	//Class to retrieve files from canvas
	abstract class fileRetrieval
	{
		//Gets a file based on a string
		public void getFile(String filepath)
		{
			
		}
		//Retrieves a student's assignment 
		public FileReader searchForStudentFile(String name, String assignment)
		{
			return null;
		}
		//Retrieves all assingments of that type
		public ArrayList<FileReader> getAssignment (String assignment)
		{
			return null;
		}
	}
	public static void main (String args[])
	{
		PlagarismDetector plag = new PlagarismDetector();
	}
}
