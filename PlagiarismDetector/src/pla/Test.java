package Plagiarism;

import java.io.File;
import java.util.Scanner;

public class Test {
	public float structcheck(String file1, String file2) throws Exception {
		Scanner file1in = new Scanner(new File(file1.replace("java", "txt")));
		Scanner file2in = new Scanner(new File(file2.replace("java", "txt")));
		
		float same = 0;
		float total = 0;
		int flag = 0;
		while (file1in.hasNextLine() || file2in.hasNextLine()) {

			String token1 = "";
			String token2 = "";

			if (file1in.hasNextLine()) {
				token1 = file1in.nextLine();
			}
			if (file2in.hasNextLine()) {
				token2 = file2in.nextLine();
			}
			System.out.println("lllll"+token1);

			if (token1.replace(" ", "").equals("Code:") && token2.replace(" ", "").equals("Code:")) {
				flag = 1;
				System.out.println("lllll"+token1);
				System.out.println("lllll"+token2);
			}
			if (token1.replace(" ", "").equals("LineNumberTable:")
					&& token2.replace(" ", "").equals("LineNumberTable:")) {
				flag = 0;
			}
			if (flag == 1) {
				if (token1.equals(token2)) {
					same++;
				}
				total++;
			}
		}
		return (100 * (same / total));
	}
	public static void main(String[] args) throws Exception{
		Test t = new Test();
		String path1 = "/Users/Yuyu/Documents/workspace/Plagiarism/src/Plagiarism/Test.java";
		String path2 = "/Users/Yuyu/Documents/workspace/Plagiarism/src/Plagiarism/Test.java";
		float f = t.structcheck(path1, path2);
		System.out.println(f);
	}
	
	
	
	
}
