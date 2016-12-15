package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompilePython extends Compile {
    public CompilePython() {
        super();
    }
    public void compile(String path, String fileName, File file) {
        String sss = "";
        String aaa = peel(fileName);
        System.out.println(aaa);
        System.out.println(path);
        try {
            ProcessBuilder pb = new ProcessBuilder("python",fileName);
            pb.directory(new File(path).getAbsoluteFile());
            Process pro = pb.start();
            String line = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getInputStream()));
            while ((line = in.readLine()) != null) { //display the output of the java program in console
                rstDis.append(line + "\r\n");
            }
            line = null;
            in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));
            while ((line = in.readLine()) != null) { //display the error location when running the java program
                rstDis.append(line + "\r\n");
            }
            pro.waitFor();
            int exitValue = pro.exitValue();
            if (exitValue == 1) {
                sss = Integer.toString(50);
                grade(sss, path);
                gra.setText(sss);
            } else {
                sss = Integer.toString(90);
                grade(sss, path);
                gra.setText(sss);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

