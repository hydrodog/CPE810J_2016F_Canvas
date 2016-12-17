import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/*
* This is a class extends from parent class and specific for Python code.
* Parameters : String path and fileName is the directory and file name of the the code that you want to grade.
*	           
* Use ProcessBuilder to create a terminal set the current directory where the code to be graded in as its working directory.
* The result is captured and displayed onto the result TextArea in GUI.
* Grade the code by capturing the exit value of terminal: 0 means run well and grade as 100, while 1 means runing code fails and get 50.
*/

public class CodePy extends Code {
  
	public CodePy() {
        super();
    }
	
	public String result = "";
	public String sss = "";

    public void compile(String path, String fileName, File file) {

		Grade grade = new Grade();
        
        try {
            // Build a ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder("python", fileName);
            pb.directory(new File(path).getAbsoluteFile());                 // Set the working directory
            Process pro = pb.start();
            // Get the result
            String line = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getInputStream()));
            while ((line = in.readLine()) != null) { // Capture the output of the program
            	result += line + "\r\n";
            }

            line = null;
            in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));
            while ((line = in.readLine()) != null) { //display the error location when running the program
            	result += line + "\r\n";
            }
            pro.waitFor();
            // Get the exit value and grade the code.
            int exitValue = pro.exitValue();

            if (exitValue == 1) {            	
                sss = Integer.toString(50);
                grade.grade(sss, path);
            } else {
                sss = Integer.toString(100);
                grade.grade(sss, path);
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	
	
	public String getResult() {
		return result;
	}
	
	public String getGrade() {
		return sss;
	}
}
