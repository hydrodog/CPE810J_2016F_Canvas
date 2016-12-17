package javaPlagiarism;

import java.io.FileReader;
import java.util.ArrayList;

public class VariableEditor
{
	public ArrayList<String> variableNames = new ArrayList<String>();
	
	//--------------------------------------
	//Retrieve all variables names from 
	// the file
	//--------------------------------------
	
	public VariableEditor()
	{
		variableNames = new ArrayList<String>();
	}
	
	public void setVariableNames(ArrayList<String> variableNames)
	{
		this.variableNames = variableNames;
	}
	
	public ArrayList<String> getVariableNames()
	{
		return this.variableNames;
	}
	//--------------------------------------
	//Retrieve all variable data types, 
	//primitives explicitly defined
	//Does not count variables re-cast for a 
	//moment, just variable types initially 
	//put on the stack at initialization
	//--------------------------------------
	
	//--------------------------------------
	//Overall score of variable similarity
	//Does grade whether the same types were 
	//used and only names were changed
	//--------------------------------------
	
	public double calculateScores(VariableEditor editor2)
	{
		int matches = 0;
		
		ArrayList<String> variableNames2 = editor2.getVariableNames();
		for(int i = 0; i<this.variableNames.size(); i++)
		{
			if(this.variableNames.contains(variableNames2.get(i)))
				matches++;
					
		}
		double score = (double)matches/(double)variableNames.size();
		
		if(score > 1)
			score = 1;
		return score;
	}
}
