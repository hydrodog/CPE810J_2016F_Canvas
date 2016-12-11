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

	
	public double pla(){
		Ss s = new Ss();
		int size = 0;
		
		String path1 = "/Users/Yuyu/Documents/workspace/Plagiarism/src/Plagiarism/Test1.java";
		String path2 = "/Users/Yuyu/Documents/workspace/Plagiarism/src/Plagiarism/Test2.java";
		Plagiarism p = new Plagiarism();
		String s1 = p.readFile(path1);
		int length = s1.length();
		String s2 = p.readFile(path2);
		java.util.List<String> l = s.getSameStr(s1, s2);
		double res = 0;
		String same = "";
		
		while(l.size()!=0){
			System.out.println("----------"+l.size());
			String str = l.get(0);
			if(str.length()<10)
				break;
			System.out.println("**********"+str.length());
			same = str;
			res += (double)str.length()*100/length;
			s1 = s1.replace(str, "");
			s2 = s2.replace(str, "");
			l = s.getSameStr(s1, s2);
			System.out.println(s1);
			System.out.println(s2);
		}
		
		System.out.println("plagiarism ratio"+res+"%");
return res/100;
	}
}












