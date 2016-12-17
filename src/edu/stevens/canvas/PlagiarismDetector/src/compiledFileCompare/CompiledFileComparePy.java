package compiledFileCompare;

import java.io.File;

public class CompiledFileComparePy extends CompiledFileCompare{

	public CompiledFileComparePy(File[] filePair, String type) {
		super(filePair, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double similarity() {
		// TODO Auto-generated method stub
		File file1 = super.filePair[0];
		File file2 = super.filePair[1];
		compile.CompilePython pyc1 = new compile.CompilePython(file1);
		compile.CompilePython pyc2 = new compile.CompilePython(file2);
		pyc1.compileSourceCode();
		pyc2.compileSourceCode();
		String name1, direction1;
		String name2, direction2;
		name1 = file1.getName(); direction1 = file1.getPath();
		name2 = file2.getName(); direction2 = file2.getPath();
		direction1 = direction1.replace(name1, "__pycache__//" + name1.replace(".py", ".cpython-35.pyc"));
		direction2 = direction2.replace(name2, "__pycache__//" + name2.replace(".py", ".cpython-35.pyc"));
		File file11 = new File(direction1);
		File file22 = new File(direction2);
		binaryCodeCompare.BinaryCodeCompare bCC = new binaryCodeCompare.BinaryCodeCompare(file11, file22);
		return bCC.getSimilarity();
	}

}
