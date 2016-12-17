import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
* This is the Grade class to generate a grade.txt file into the directory where the code that you want to grade located in.
* Parameters : str is the score and it is a String.
*	       String path is the directory of the the code that you want to grade.
* Use BufferedWriter to write into the grade.txt file.
*/

public class Grade {	
	public void grade(String str, String path) {
		String name = "grade.txt";
		File file = new File(path, name);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String str1 = str ;
			bw.write(str1);
			bw.newLine();
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
