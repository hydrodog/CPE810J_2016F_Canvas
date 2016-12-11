/*
 * Author: Su Pengyu
 * BinaryCodeCompare integrate the fuction of
 * EDA and Reader.
 */
package binaryCodeCompare;

import java.io.File;

public class BinaryCodeCompare {
	private File file1;
	private File file2;
	
	public BinaryCodeCompare(File file1, File file2) {
		this.file1 = file1;
		this.file2 = file2;
	}
	
	public double getSimilarity() {
		Reader aCode = new Reader(file1);
		Reader bCode = new Reader(file2);
		return binaryCodeSimilarity(aCode.getBytes(), bCode.getBytes());
	}
	
	private double binaryCodeSimilarity(byte[] a, byte[] b){
		EditDistanceAlgorithm f = new EditDistanceAlgorithm();
		return f.EDAValue(a,b);
	}
}
