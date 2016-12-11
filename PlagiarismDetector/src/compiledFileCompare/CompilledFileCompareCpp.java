package compiledFileCompare;

import java.io.File;

public class CompilledFileCompareCpp extends CompiledFileCompare{

	public CompilledFileCompareCpp(File[] filePair, String type) {
		super(filePair, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double similarity() {
		// TODO Auto-generated method stub
		File file1 = super.filePair[0];
		File file2 = super.filePair[1];
		compile.CompileCpp cpp1 = new compile.CompileCpp(file1);
		compile.CompileCpp cpp2 = new compile.CompileCpp(file2);
		cpp1.compileSourceCode();
		cpp2.compileSourceCode();
		File file11 = new File(file1.getPath().replaceAll(".cpp", ".obj"));
		File file22 = new File(file2.getPath().replaceAll(".cpp", ".obj"));
		binaryCodeCompare.BinaryCodeCompare bCC = new binaryCodeCompare.BinaryCodeCompare(file11, file22);
		return bCC.getSimilarity();
	}

}
