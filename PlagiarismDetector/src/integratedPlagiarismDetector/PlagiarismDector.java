package integratedPlagiarismDetector;

import java.io.File;
import java.util.ArrayList;

public class PlagiarismDector {
	private ArrayList<File> fileList = new ArrayList<File>();
	private String type;
	
	public PlagiarismDector(ArrayList<File> fileList, String type, String tempFileDirection) {
		this.fileList = fileList;
		this.type = type;
	}
	
	public ArrayList<File[]> getSuspectedFilePair() {
		ArrayList<File[]> suspectedFilePair = new ArrayList<File[]>();
		for(int i = 0; i < fileList.size(); i++){
			for(int j = i + 1; j < fileList.size(); j++){
				File[] filePair = new File[2];
				filePair[0] = fileList.get(i);
				filePair[1] = fileList.get(j);
				double similarity = 0;
				
				compiledFileCompare.FinalCompiledFileCompare fCFC = new compiledFileCompare.FinalCompiledFileCompare();
				double compiledFileCompareSimilarity = fCFC.getSimilarity(filePair, type);
				similarity += compiledFileCompareSimilarity;
				
				if(similarity > 0.5) {
					suspectedFilePair.add(filePair);
					System.out.println("\nWarning!Find plagiarism between" + filePair[0].getName() +
							"and" + filePair[2] +"!\n");
				}else {
					System.out.println("\nNot find plagiarism!\n");
				}
			}
		}
		return suspectedFilePair;
	}
}