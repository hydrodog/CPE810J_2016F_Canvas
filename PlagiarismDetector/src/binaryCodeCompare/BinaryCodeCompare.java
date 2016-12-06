/*
 * Author: Su Pengyu
 * BinaryCodeCompare integrate the fuction of
 * EDA and Reader.
 */
package binaryCodeCompare;

import java.io.File;

public class BinaryCodeCompare {
	private String aName;
	private String bName;
	private String type;
	private String direction;
	
	public BinaryCodeCompare(String a, String b, String type, String direction) {
		this.aName = a;
		this.bName = b;
		this.type = type;
		this.direction = direction;
	}
	
	public double getSimilarity() {
		Reader aCode = new Reader(aName, direction, type);
		Reader bCode = new Reader(bName, direction, type);
		return binaryCodeSimilarity(aCode.getBytes(), bCode.getBytes());
	}
	
	private double binaryCodeSimilarity(byte[] a, byte[] b){
		EditDistanceAlgorithm f = new EditDistanceAlgorithm();
		return f.EDAValue(a,b);
	}
}
