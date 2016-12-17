//@author: Michael Reilly

package javaPlagiarism;

import java.util.ArrayList;

public class LineParser 
{
	//--------------------------------------
	// ArrayList of Variable Names
	//--------------------------------------
	
	ArrayList<String> variableNames;
	public LineParser()
	{
		variableNames = new ArrayList<String>();
	}
	public ArrayList<String> getVariableNames() {
		return variableNames;
	}
	public String stripCode(String line)
	{
		String edits = "";
		
		//--------------------------------------
		//Remove any tabs or whitespace at 
		// the start and end
		//--------------------------------------
		
		if(line.contains("//"))
		{
			//--------------------------------------
			//Comment, skip line, do nothing
			//--------------------------------------
			edits = stripCode(line.substring(0,line.indexOf("//")));
			return edits;
		}
		if(line.contains("}") || line.contains("{"))
		{
			//--------------------------------------
			//Line is functionally useless, get rid of it
			//--------------------------------------
			line = line.replaceAll("\\{", " ");
			line = line.replaceAll("\\}", " ");
		}
		
		//--------------------------------------
		// Extract a for loop
		//--------------------------------------
		
		if(line.length()>=3 && line.matches("\\s+(for)\\s+"))
		{
			//--------------------------------------
			// I'm just looking for the breaking condition
			//--------------------------------------
			
			String forLoop = line.substring(line.indexOf("("), line.lastIndexOf(")"));
			String loopOn = forLoop.split(";")[1]; 
			
			//--------------------------------------
			// Notify the looping condition and append
			//--------------------------------------
			
			edits = " " + "l" + " " + loopOn;
		}
		else if(line.length()>=5 && line.matches("\\s+(while)\\s+"))
		{
			edits = line.substring(line.indexOf("("), line.lastIndexOf(")"));
			/*

			//--------------------------------------
			// Later decided logical operands
			// were important to stay
			//--------------------------------------
			 * 
			if(edits.contains(" && "))
			{
				edits = " " + edits.substring(0, edits.indexOf("&&"))+edits.substring(edits.indexOf("&&"), edits.length());
			}
			else if(edits.contains(" || "))
			{
				edits = " " + edits.substring(0, edits.indexOf("||"))+edits.substring(edits.indexOf("||"), edits.length());
			}
			*/
			
			//--------------------------------------
			// Notify the looping condition and append
			//--------------------------------------
			
			edits = " " + "l" + " " + edits;
		}
		else
		{
			//--------------------------------------
			// Append a space because the regex 
			// relies on beginning with a space
			//--------------------------------------
			
			edits = " " + line;
		}
		if(line.length()>=3 && line.matches("\\s+(if)\\s+"))
		{
			edits = line.substring(line.indexOf("("), line.lastIndexOf(")"));
			edits = " " + "z" + " " + edits;
		}
		else if(line.length()>=7 && line.matches("\\s+(else\\sif)\\s+"))
		{
			edits= line.substring(line.indexOf("("), line.lastIndexOf(")"));
			edits = " " + "z" + " " + edits;
		}
			
		//--------------------------------------
		// Begin removing key words
		// Longer replacements -> less frequency of use
		//--------------------------------------
		
		
		//--------------------------------------
		// How things are put into classes are 
		// very important
		//--------------------------------------
		
			edits = edits.replaceAll("\\s+(class)\\s+", " ccc");
			
		//--------------------------------------
		// Variable Primitive Types
		//--------------------------------------
			
		if(edits.matches("\\s+int\\s+|\\s+String\\s+|\\s+char\\s+|\\s+double\\s+|\\s+boolean\\s+|\\s+byte\\s+|\\s+short\\s+|\\s+long\\s+|\\s+float\\s+"))
		{
			//--------------------------------------
			// Retrieves the word right after a 
			// variable type that 
			// Does not apply to casts
			// or functions
			//--------------------------------------
			
			String[] vars = edits.split("(\\s+int\\s+|\\s+String\\s+|\\s+char\\s+|\\s+double\\s+|\\s+boolean\\s+|\\s+byte\\s+|\\s+short\\s+|\\s+long\\s+|\\s+float\\s+)\\w+(?<!(public|private|protected))");
			String name = "";
			
			//--------------------------------------
			// Keep a record of variable names
			//--------------------------------------
			
			for (int i = 1; i < vars.length-1; i++)
			{
				name = vars[i];
				name = name.trim();
				name = name.substring(0, name.indexOf(" "));
				name.trim();
				
				//--------------------------------------
				// Add them if they don't exist already
				//--------------------------------------
				
				if(!variableNames.contains(name))
					variableNames.add(name);
			}
			edits = edits.replaceAll("int\\s+|String\\s+|char\\s+|double\\s+|boolean\\s+|byte\\s+|short\\s+|long\\s+|float\\s+", " ");
		}
		
		for(int i = 0; i < variableNames.size(); i++)
		{
			edits = edits.replaceAll(("\\s+" + variableNames.get(i) + "\\s+"), "v"+ i);
		}
			
		//--------------------------------------
		// Method Scopes
		//--------------------------------------
			
		edits = edits.replaceAll("\\s+(public)\\s+", " ppp");
		edits = edits.replaceAll("\\s+(private)\\s+", " ppp");
		edits = edits.replaceAll("\\s+(private)\\s+", " ppp");
			
		//--------------------------------------
		// Class, Method, and Variable Modifiers
		//--------------------------------------
		
		edits = edits.replaceAll("\\s+(static)\\s+", " ss");
		edits = edits.replaceAll("\\s+(synchronized)\\s+", " ss");
		edits = edits.replaceAll("\\s+(assert)\\s+", " ss");
		edits = edits.replaceAll("\\s+(final)\\s+", " ss");
		edits = edits.replaceAll("\\s+(const)\\s+", " ss");
		edits = edits.replaceAll("\\s+(abstract)\\s+", " ss");
		edits = edits.replaceAll("\\s+(interface)\\s+", " ss");
		edits = edits.replaceAll("\\s+throws\\s*;", " ss");
		edits = edits.replaceAll("\\s+volatile\\s*;", " ee");
			
		//--------------------------------------
		// Loop Modifiers
		//--------------------------------------	
			
		edits = edits.replaceAll("\\s+continue\\s*;", " ll");
		edits = edits.replaceAll("\\s+break\\s*;", " lll");

		
		//--------------------------------------
		// Do in a do while loop is useless,
		// no need to include it as a important 
		// word
		//--------------------------------------	
		
		edits = edits.replaceAll("\\s+do\\s*;", " ");
			
		//--------------------------------------
		// Exception handlers
		//--------------------------------------
			
		edits = edits.replaceAll("\\s+try\\s*;", " ee");
		edits = edits.replaceAll("\\s+catch\\s*;", " ee");
		edits = edits.replaceAll("\\s+finally\\s*;", " ee");
		edits = edits.replaceAll("\\s+throw\\s*;", " ee");
		
		
		//--------------------------------------
		// Switch statements
		//--------------------------------------
			
		edits = edits.replaceAll("\\s+switch\\s*;", " cc");
		edits = edits.replaceAll("\\s+case\\s*;", " cc");
			
		//--------------------------------------
		// Enum statements
		//--------------------------------------
			
		edits = edits.replaceAll("\\s+enum\\s*;", "q");
			
		//--------------------------------------
		// Inheritance
		//--------------------------------------
			
		edits = edits.replaceAll("\\s+extends\\s*;", "pp");
		edits = edits.replaceAll("\\s+implements\\s*;", "pp");
		
		//--------------------------------------
		// Function Calls
		//--------------------------------------
		
		edits = edits.replaceAll("\\s+super\\s*;", "rr");
		edits = edits.replaceAll("\\s+return\\s*;", "rr");
		edits = edits.replaceAll("\\s+void\\s*;", " ");
			
		//--------------------------------------
		// A few cleanup of spaces and syntax
		//--------------------------------------	
		
		edits = edits.replaceAll("[\\s\\t]", "");
		edits = edits.replaceAll(";", "");
		edits = edits.replaceAll("\\(\\)", "");
		
		//--------------------------------------
		// Most other key words are now so rare
		// that I would be impressed to see them
		// Hence, they will be allowed
		//--------------------------------------	
	
		edits = edits.trim();
		return edits;
	}	
}
