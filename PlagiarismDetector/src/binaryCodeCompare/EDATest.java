
package binaryCodeCompare;

public class EDATest {
	public static void main(String[] args) {
		byte[] a = { (byte)1, (byte)2};
		byte[] b = { (byte)1, (byte)4, (byte)5};
		EditDistanceAlgorithm x = new EditDistanceAlgorithm();
		System.out.println(x.EDAValue(a, b));
	}

}
