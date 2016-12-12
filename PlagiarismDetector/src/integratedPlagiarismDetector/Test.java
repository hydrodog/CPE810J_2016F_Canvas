package integratedPlagiarismDetector;

import java.io.File;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		File f1 = new File("e:HelloWorld1.cpp");
		File f2 = new File("e:HelloWorld2.cpp");
		ArrayList<File> f = new ArrayList<File>();
		f.add(f1);
		f.add(f2);
		PlagiarismDector p = new PlagiarismDector(f,"c++");
		p.getSuspectedFilePair();
	}
}
