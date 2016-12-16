package ui;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class childPy extends father {
  
	public childPy() {
        super();
    }
	
	private String result = "";
	private String sss = "";

    public void compile(String path, String fileName, File file) {
		grade grade1 = new grade();
        String aaa = peelF(fileName);
////        System.out.println(aaa);
////        System.out.println(path);
        try {
            ProcessBuilder pb = new ProcessBuilder("python",fileName);
            pb.directory(new File(path).getAbsoluteFile());
            Process pro = pb.start();
            String line = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getInputStream()));
            while ((line = in.readLine()) != null) { //display the output of the java program in console
            	result += line + "\r\n";
////            	rstDis.append(line + "\r\n");
            }
            line = null;
            in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));
////            while ((line = in.readLine()) != null) { //display the error location when running the java program
////                rstDis.append(line + "\r\n");
////            }
            pro.waitFor();
            int exitValue = pro.exitValue();
            if (exitValue == 1) {            	
                sss = Integer.toString(50);
                grade1.grade(sss, path);
////                gra.setText(sss);
            } else {
                sss = Integer.toString(90);
                grade1.grade(sss, path);
////                gra.setText(sss);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
