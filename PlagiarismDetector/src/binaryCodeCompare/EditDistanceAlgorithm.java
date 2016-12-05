/*
 * Author: Su Pengyu
 */
package binaryCodeCompare;

public class EditDistanceAlgorithm {
	public double EDAValue (byte[] a, byte[] b) {
		Matrix data = new Matrix(a.length + 1, b.length + 1);
		for(int i = 0; i < data.rows; i++){
			data.set(i, i, 0);
		}
		for(int i = 0; i < data.cols; i++){
			data.set(i, 0, i);
		}
		
		for(int i = 1; i < data.rows; i++) {
			for(int j = 1; j < data.cols; j++) {
				if(a[i - 1] == b[j - 1]) {
					data.set(data.get(i - 1, j - 1), i, j);
				}else{
					int temp = min(data.get(i - 1, j - 1), data.get(i - 1, j), data.get(i, j - 1));
					data.set(temp + 1, i, j);
				}
			}
		}
		return (1 - data.get(data.rows - 1, data.cols - 1) / (double)max(data.rows - 1, data.cols - 1));
	}
	
	private int min(int i, int j, int k) {
		if(i < j) {
			if(i < k) {
				return i;
			}else {
				return k;
			}
		}else {
			if(j < k) {
				return j;
			}else {
				return k;
			}
		}
	}
	
	private int max(int i, int j) {
		if(i > j) {
			return i;
		}else{
			return j;
		}
	}
	
	private class Matrix {
		private int rows;
		private int cols;
		private int[] data;
		
		public Matrix(int rows, int cols) {
			this.rows = rows;
			this.cols = cols;
			data = new int[rows * cols];
		}
		
		public void set(int value, int i, int j) {
			data[i * cols + j] = value;
		}
		
		public int get(int i, int j) {
			return data[i * cols + j];
		}
	}	
}
