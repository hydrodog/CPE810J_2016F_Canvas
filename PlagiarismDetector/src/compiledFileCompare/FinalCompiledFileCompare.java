package compiledFileCompare;

import java.io.File;

public class FinalCompiledFileCompare {
	public double getSimilarity(File[] filePair, String type) {
		CompiledFileCompare x;
		if(type.equals("java")) {
			x = new CompiledFileCompareJava(filePair,type);
			return x.similarity();
		} else if(type.equals("c++")) {
			x = new CompiledFileCompareCpp(filePair,type);
			return x.similarity();
		} else if(type.equals("python")) {
			x = new CompiledFileComparePy (filePair,type);
			return x.similarity();
		} else {
			System.out.println("Please check file type. (c++,java,python)");
			return 0;
		}
	}
}
