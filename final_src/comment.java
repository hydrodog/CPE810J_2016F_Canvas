//package ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class comment {
	public void comment(String str, String path) {
		String name = "comment.txt";
		File file = new File(path, name);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String line = str;
			bw.write(str);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
