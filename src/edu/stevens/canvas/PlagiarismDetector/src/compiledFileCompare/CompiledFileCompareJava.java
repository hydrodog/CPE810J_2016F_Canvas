package compiledFileCompare;

import java.io.File;

public class CompiledFileCompareJava extends CompiledFileCompare{

	public CompiledFileCompareJava(File[] filePair, String type) {
		super(filePair, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double similarity() {
		// TODO Auto-generated method stub
		File file1 = super.filePair[0];
		File file2 = super.filePair[1];
		String direction1 = file1.getPath();
		String direction2 = file2.getPath();
		compile.CompileJava java1 = new compile.CompileJava(file1, direction1);
		compile.CompileJava java2 = new compile.CompileJava(file2, direction2);
		java1.compileSourceCode();
		java2.compileSourceCode();
		File file11 = new File(file1.getPath().replaceAll(".java", ".class"));
		File file22 = new File(file2.getPath().replaceAll(".java", ".class"));
		binaryCodeCompare.BinaryCodeCompare bCC = new binaryCodeCompare.BinaryCodeCompare(file11, file22);
		return bCC.getSimilarity();
	}

}
