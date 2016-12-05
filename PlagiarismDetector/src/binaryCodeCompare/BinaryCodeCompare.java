package binaryCodeCompare;

public class BinaryCodeCompare {
	public double binaryCodeSimilarity(byte[] a, byte[] b){
		EditDistanceAlgorithm f = new EditDistanceAlgorithm();
		return f.EDAValue(a,b);
	}
}
