package javaPlagiarism;

import java.io.*;
import java.util.*;
import pla.Plagiarism;


//--------------------------------------
// Head Class for all the assessing of java 
// syntax code plagiarism
//--------------------------------------

public class SyntaxScore {

	
	public double getScore(String file1, String file2) throws IOException, FileNotFoundException
	{
		double score = 0;
		
		VariableEditor variable1 = new VariableEditor();
		VariableEditor variable2 = new VariableEditor();
		
		
		File file10 = new File(file1);
		File file20 = new File(file2);
		
		File edits1;
		File edits2; 
		
		CodeCruncher crunch = new CodeCruncher();
		
		edits1 = crunch.crunchCode(file10, variable1);
		edits2 = crunch.crunchCode(file20, variable2);
		
		double variableScore = variable1.calculateScores(variable2);
		
		Plagiarism plagiarism = new Plagiarism();
		double codeScore = plagiarism.pla(edits1.getPath(), edits2.getPath());
		
		score = .1*variableScore + .9*codeScore;
		
		return score;
	}
}
