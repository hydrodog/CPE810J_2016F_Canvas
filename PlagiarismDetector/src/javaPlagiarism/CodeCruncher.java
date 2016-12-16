//@author: Michael Reilly

package javaPlagiarism;

//---------------------------------------------------------------------------------------------------------------------------------------------
//There are several characteristics to consider plagarism with similarity to another file:
//1. Word for word.  That is if they submitted the same file
//2. Context.  Did they just submit the same thing with new variable names
//3. Function.  Did it do the exact same thing
//4. Byte Code.  Did it compile down to the same thing
//5. Pattern Irregularity.  Is this style of coding out of the ordinary for the student.


//NOTES:
//http://web.archive.org/web/20081224234350/http://www.dcs.shef.ac.uk/~sam/stringmetrics.html#overlap - String comparison algorithms
//http://www.ics.uci.edu/~kay/checker.html - MOSS algorithm
//http://theory.stanford.edu/~aiken/publications/papers/sigmod03.pdf - Research paper on document fingerprinting
//---------------------------------------------------------------------------------------------------------------------------------------------

import java.util.*;
import java.util.regex.*;
import java.io.*;
import pla.Plagiarism;


//--------------------------------------
//TOP CLASS
//Where different algorithms can be 
//summed up to make an overall score
//--------------------------------------
public class CodeCruncher {
	//Looks for words and lines for parsing
	private int overallScore;
	
	//List of files
	public CodeCruncher()
	{
			overallScore = 0;
	}
	public int getOverallScore() {
		return overallScore;
	}

	public File crunchCode(File file1, VariableEditor variable1) throws IOException, FileNotFoundException {
		double score = 0;
		
		LineParser lineParse1 = new LineParser();
		
		File fileEdit1 = new File(file1.getPath().replaceFirst(file1.getName(),file1.getName()+"Edit1"));
		
		try (FileInputStream fis1  = new FileInputStream(file1); 
		BufferedReader reader1 =  new BufferedReader (new InputStreamReader(fis1));)
		{
			//fis1 = new FileInputStream(file1);
			//reader1 = new BufferedReader (new InputStreamReader(fis1));
			
			FileWriter fos1 = new FileWriter(fileEdit1);
			BufferedWriter writer1 = new BufferedWriter(fos1);
			
			//--------------------------------------
			// Read line by line and parse
			//-------------------------------------
			
			String line = reader1.readLine();
			
			
			//--------------------------------------
			// Ignore Imports, they don't affect content
			// Pass over until the first class starts
			//-------------------------------------
			
			String edits = "";
			long lineCount1 = 0;
			boolean importStatements = true;
			
			//--------------------------------------
			// Regex for <public/private/protect> class
			//-------------------------------------
			
			
			Pattern p1 = Pattern.compile("([\\s\\t]*public[\\s\\t])|([\\s\\t]private[\\s\\t])|([\\s\\t]protected[\\s\\t])");
			Pattern p2 = Pattern.compile("([\\s\\t]class[\\s\\t])");
			Matcher m1;
			while(line != null)
			{
				lineCount1++;
				
				if(importStatements && (line.contains("public")  || line.contains("class") || line.contains("private") || line.contains("protected")))
				{
					m1 = p1.matcher(line);
					if(m1.find())
					{
						m1 = p2.matcher(line);
						if(m1.find())
							importStatements = false;
					}
				}
				if(importStatements)
				{
					line = reader1.readLine();
					line = line.trim();
						continue;
				}
				
				//--------------------------------------
				// Now pick out pieces of the code
				//-------------------------------------
				
				edits = lineParse1.stripCode(line);
				if(edits == "")
				{
					//--------------------------------------
					// Line is empty, do nothing
					//--------------------------------------
				}
				else if(line.length()>2 && line.substring(0,2).equals("/*"))
				{
					//--------------------------------------
					// Write to file if there was preceeding 
					// code.  Hoping to obey standards here.
					// One lost line isn't huge, but
					// parsing line by line is to unwieldly
					// for keeping track of block comments
					//--------------------------------------
					
					while(line != null)
					{
						//--------------------------------------
						// Strip the code before the block 
						// comment if its the same line and
						// insert where it needs to go
						//--------------------------------------
						
						line = reader1.readLine();
						if(line.length()>=2)
						{
							if(line.substring(line.length()-2,line.length()).equals("*/"))
							{
								//--------------------------------------
								// Start again if there was code afterwards
								//--------------------------------------
								
									line = "";
									break;
							}
						}
					}
				}
				
				//--------------------------------------
				// Write to file
				//--------------------------------------
				
				if(!edits.isEmpty())
				{
					writer1.append(edits);	
				}
				line = reader1.readLine();
			}
			writer1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return fileEdit1;
	}
}
/*
	class FileParser implements Comparable<FileParser>
	{
		protected FileReader file1;
		
		//--------------------------------------
		//Overall score of file similarity from a string of characters standpoint
		//--------------------------------------
		
		public int fileSimilarityScore (FileReader file, FileReader file2)
		{
			ArrayList<String> comments1 = retrieveComments(file);
			ArrayList<String> comments2 = retrieveComments(file2);
			
			ArrayList<String> loops1 = retriveLoops(file);
			ArrayList<String> loops2 = retriveLoops(file2);
			
			ArrayList<String> ifelse1 = retriveIfElse(file);
			ArrayList<String> ifelse2 = retriveIfElse(file2);
			
			//--------------------------------------
			//Now do a comparison of them all
			//Basic Hit matches
			//--------------------------------------
			
			//--------------------------------------
			//Exact comment matches
			//--------------------------------------
			
			int commentMatches = 0;
			for (int x = 0; x<comments1.size(); x++)
			{
				for (int y = 0; x<comments2.size(); x++)
				{
					if(comments1.get(x) == comments2.get(y))
						commentMatches++;
				}
			}
			//--------------------------------------
			//Loop condition direct hits, may 
			//remove variable names at a later date
			//--------------------------------------
			
			int loopMatches = 0;
			for (int x = 0; x<loops1.size(); x++)
			{
				for (int y = 0; x<loops2.size(); x++)
				{
					if(loops1.get(x) == loops2.get(y))
						loopMatches++;
				}
			}
			
			//--------------------------------------
			//if else condition direct hits, may 
			//remove variable names at a later date
			//--------------------------------------
			
			int ifelseMatches = 0;
			for (int x = 0; x<ifelse1.size(); x++)
			{
				for (int y = 0; x<ifelse2.size(); x++)
				{
					if(ifelse1.get(x) == ifelse2.get(y))
						ifelseMatches++;
				}
			}
			
			return 0;
		}
		//--------------------------------------
		//Retrieves a single line of code
		//--------------------------------------
		
		public ArrayList<String> retriveLoops (FileReader file)
		{
			ArrayList<String> loops = new ArrayList<String>();
			
			try(BufferedReader br1 = new BufferedReader(file)) {
			    for(String line; (line = br1.readLine()) != null; ) {
			       if(line.contains("for") || line.contains("while"))
			       {
			    	   loops.add(line);
			       }
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			return loops;
		}
		public ArrayList<String> retriveIfElse (FileReader file)
		{
			ArrayList<String> ifelse = new ArrayList<String>();
			
			try(BufferedReader br1 = new BufferedReader(file)) {
			    for(String line; (line = br1.readLine()) != null; ) {
			       if(line.contains("if") || line.contains("else"))
			       {
			    	   ifelse.add(line);
			       }
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ifelse;
		}
		//--------------------------------------
		//Shows if a student noted the name of 
		// who they received help from
		//--------------------------------------
		
		//--------------------------------------
		//Will be preceded by a // and a 
		//proper noun
		//Return true if they noted the student 
		//who received help, false if not found
		//--------------------------------------
		public ArrayList<String> retrieveComments (FileReader file)
		{
			ArrayList<String> comments = new ArrayList<String>();
			
			try(BufferedReader br1 = new BufferedReader(file)) {
			    for(String line; (line = br1.readLine()) != null; ) {
			       if(line.contains("//"))
			       {
			    	   comments.add(line.substring(line.indexOf("//"), line.length()));
			       }
			       else if(line.contains("/*"))
			       {
			    	   comments.add(line.substring(line.indexOf("/*"), line.length()));
			    	   line = br1.readLine();
			    	   while(line != null)
						{
							if(line.length()>=2)
							{
								if(line.contains(""))
								{
									line = line.substring(line.indexOf("")+2, line.length());
								}
							}
							line = br1.readLine();
						}
			       }
			    }
			} catch (IOException e) {
				// Unable to read file
				e.printStackTrace();
			}
			return comments;
		}
		//Returns the line number where the segement came from, otherwise return -1 if not found
		public int indexOfLine (String testLine, FileReader file)
		{
			int counter = 0;
			
			try(BufferedReader br1 = new BufferedReader(file)) {
			    for(String line; (line = br1.readLine()) != null; ) {
			       if(line.equals(testLine))
			       {
			    	   return counter++;
			       }
			       counter++;
			    }
			} catch (IOException e) {
				// Unable to read file
				e.printStackTrace();
			}
			return -1;
		}
			
		//Method of comparable.  To be filled in to add comparing one file context to another.
		@Override
		public int compareTo(FileParser arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	*/
	//--------------------------------------
	// Looks for function and similarity of 
	// variable use, i.e. did they change 
	// variable names but keep everything 
	// else the same
	//--------------------------------------
	
	
	
	//DEPRECATED AS OF BYTE CODE USING THE LCS ALGORITHM
	//Does the function do the exact same thing.  i.e. while(x<0) and while(x>=1) may be the same thing
	/*class FunctionUse extends FileParser
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
		//Different testing of string similarity for functions and their parameters
		public int fileSimilarityScore (FileReader file, FileReader file2)
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
		public int fileSimilarityScore (FileReader file, FileReader file2)
		{
			return 0;
		}
		@Override
		//Loops are different syntax
		public ArrayList<String> retriveLoops (FileReader file)
		{
			return null;
		}
		@Override
		//If else will be slightly different syntax
		public ArrayList<String> retriveIfElse (FileReader file)
		{
			return null;
		}
	}
	*/
	//Looks for irregularities in the overall 
	/*
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
	}*/
