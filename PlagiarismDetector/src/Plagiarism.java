package Plagiarism;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
/*
 * author: Yu Yu 
 * modified： 
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Plagiarism{
	public ArrayList<String> List = new ArrayList<String>();
	public ArrayList<String> List2 = new ArrayList<String>();
	//read file by lines
	public String readFile(String fileName){
		String res = "";
		File file = new File(fileName);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		while ((tempString = reader.readLine())!=null){
			//System.out.println(line+":"+tempString);
			res += tempString.trim();
			line++;
		}
		
		reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (reader !=null){
				try{
					reader.close();
				}catch(IOException e1){
					
				}
			}
		}
		return res;
		
	}

	
	public static void main(String args[]){
		Ss s = new Ss();
		int size = 0;
		
		String path1 = "/Users/Yuyu/Downloads/PlagarismDetector.java";
		String path2 = "/Users/Yuyu/Downloads/Complex-1.java";
		Plagiarism p = new Plagiarism();
		String s1 = p.readFile(path1);
		String s2 = p.readFile(path2);
		List<String> l = s.getSameStr(s1, s2);
		for(String str : l){
			System.out.println(str.length());
			System.out.println("plagiarism ratio"+(double)str.length()*100/s1.length()+"%");
		}
		
	}
}












