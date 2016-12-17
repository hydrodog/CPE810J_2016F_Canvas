import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/*
* This is a class extends from parent class and specific for C++ code.
* Parameters : String path and fileName is the directory and file name of the the code that you want to grade.
*	           
* Use ProcessBuilder to create a terminal set the current directory where the code to be graded in as its working directory.
* The result is captured and displayed onto the result TextArea in GUI.
* Grade the code by capturing the exit value of terminal twice for compile and run respectily: if compile return 1, means fails and grade as 25;
*                                                                                              if compile return 0, while run return 1, means it compile well but run fails, and will get 50;
*                                                                                              if both return 0, then get 100;
* Method peelF is the get the prefix of file name, which means the string before '.', for instance, "Test.cpp" will return "Test".
*/

public class CodeCpp extends Code {
   
    CodeCpp() {
        super();
    }
	
	public String result = "";
	public String sss = "";
	
    public void compile(String path, String fileName, File file) {
        Grade grade = new Grade();
        String aaa = peelF(fileName);
        
        try {
            // Build a ProcessBuilder to compile the code 
            ProcessBuilder pb = new ProcessBuilder("g++", fileName, "-o", aaa);
            pb.directory(new File(path).getAbsoluteFile());                       // Set the working directory
            Process pro = pb.start();

            String line = null;
            // Use BufferedReader to get the error stream
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));

           while ((line = in.readLine()) != null) {                             //display the error location of compiling
        	   result += line + "\r\n";
            }

            pro.waitFor();

            int exitValue = pro.exitValue();
            // if compile fails
            if (exitValue == 1) {
                sss = Integer.toString(25);
                grade.grade(sss, path);

            } else {
                // Run the code
                String command = "./"+aaa;
                pb = new ProcessBuilder(command);
                pb.directory(new File(path).getAbsoluteFile());
                pro = pb.start();
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getInputStream()));
                while ((line = in.readLine()) != null) { //display the output of the program
                	result += line + "\r\n";
                }
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getErrorStream()));
                while ((line = in.readLine()) != null) { //display the error location 
                	result += line + "\r\n";             
                }
                pro.waitFor();
                exitValue = pro.exitValue();
                // Capture the exit Value
                if (exitValue == 1) {
                    sss = Integer.toString(50);
                    grade.grade(sss, path);
                } else {
                    sss = Integer.toString(100);
                    grade.grade(sss, path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Get file name without postfix
	public String peelF(String str) {
        if (str == null) 
            return str;
        int len = str.length();
        int i = str.indexOf('.');
        String rst = str.substring(0, i);
        return rst;
    }

	public String getResult() {
		return result;
	}
	
	public String getGrade() {
		return sss;
	}
}
