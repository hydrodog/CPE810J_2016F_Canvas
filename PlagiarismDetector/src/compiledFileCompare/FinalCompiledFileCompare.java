package compiledFileCompare;

import java.io.File;

public class FinalCompiledFileCompare {
	public double getSimilarity(File[] filePair, String type) {
		CompiledFileCompare x;
		if(type.equals("java")) {
			x = new CompiledFileCompareJava(filePair,type);
			double similarity = x.similarity();
			double ratio = 100 * similarity;
			System.out.println("Edit Distance between these two file is " + ratio + "%");
			return similarity;
		} else if(type.equals("c++")) {
			x = new CompiledFileCompareCpp(filePair,type);
			double similarity = x.similarity();
			double ratio = 100 * similarity;
			System.out.println("Edit Distance between these two file is " + ratio + "%");
			return similarity;
		} else if(type.equals("python")) {
			x = new CompiledFileComparePy (filePair,type);
			double similarity = x.similarity();
			double ratio = 100 * similarity;
			System.out.println("Edit Distance between these two file is " + ratio + "%");
			return similarity;
		} else {
			System.out.println("Please check file type! Only [c++,java,python] is acceptable!");
			return 0;
		}
	}
}
