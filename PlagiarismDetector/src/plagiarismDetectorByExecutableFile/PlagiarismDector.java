package plagiarismDetectorByExecutableFile;

import java.io.File;
import java.util.ArrayList;

import binaryCodeCompare.BinaryCodeCompare;

public class PlagiarismDector {
	private ArrayList<File> fileList = new ArrayList<File>();
	private String type;
	/*
	 * Since this program need to first compile source file into 
	 * executable file, users are supposed to provide a direction 
	 * to store these compiled files.
	 */
	private String tempFileDirection;   //Example("e:/temp/")
	
	public PlagiarismDector(ArrayList<File> fileList, String type, String tempFileDirection) {
		this.fileList = fileList;
		this.type = type;
		this.tempFileDirection = tempFileDirection;
	}
	
	public ArrayList<File[]> getSuspectedFilePair() {
		if(type == "java") {
			return getSuspectedFilePairJava();
		}else if(type == "c++") {
			return getSuspectedFilePairC__();
		}else if(type == "python") {
			return getSuspectedFilePairPython();
		}else {
			System.out.println("Please check type of files!");
			return null;
		}
	}
	
	private ArrayList<File[]> getSuspectedFilePairJava() {
		ArrayList<File[]> suspectedFilePair = new ArrayList<File[]>();
		for(int i = 0; i < fileList.size(); i++){
			for(int j = i + 1; j < fileList.size(); j++){
				File file1 = fileList.get(i);
				File file2 = fileList.get(j);
				compile.CompileJava java1 = new compile.CompileJava(file1, tempFileDirection);
				compile.CompileJava java2 = new compile.CompileJava(file2, tempFileDirection);
				java1.compileSourceCode();
				java2.compileSourceCode();
				BinaryCodeCompare x = new BinaryCodeCompare(file1.getName(),file2.getName(),"java",tempFileDirection);
				
				if(x.getSimilarity() > 0.5) {
					File[] file = new File[2];
					file[0] = file1;
					file[1] = file2;
					suspectedFilePair.add(file);
					System.out.println("\nWarning!Find plagiarism!\n");
				}else {
					System.out.println("\nNot find plagiarism!\n");
				}
			}
		}
		return suspectedFilePair;
	}
	
	private ArrayList<File[]> getSuspectedFilePairC__() {
		return null;
	}
	
	private ArrayList<File[]> getSuspectedFilePairPython() {
		return null;
	}
}