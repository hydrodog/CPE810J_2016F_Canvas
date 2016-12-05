package plagiarismDetectorByExecutableFile;

import java.io.File;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		ArrayList<File> f = new ArrayList<File>();
		f.add(new File("e:/temp/Hello1.java"));
		f.add(new File("e:/temp/Hello2.java"));
		PlagiarismDector p = new PlagiarismDector(f,"java","e:/temp/");
		ArrayList<File[]> f_ = p.getSuspectedFilePair();
		
		System.out.println("hi");
	}
}
