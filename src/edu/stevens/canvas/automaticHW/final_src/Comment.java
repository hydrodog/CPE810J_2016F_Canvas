import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
* This is the Grade class to generate a comment.txt file into the directory where the code that you want to grade located in.
* Parameters : str is the comment added into the file, and it's get from TextArea in the GUI.
*	       String path is the directory of the the code that you want to grade.
* Use BufferedWriter to write the commment text into the comment.txt file.
*/

public class Comment {
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
