import java.io.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
// import java.awt.TextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Compile extends JFrame {
    private TextArea rstDis;
    private JButton run, graSave;
    private JTextField gra;
    private JLabel graLab;
    private JPanel gradArea;
    Compile() {

        rstDis = new TextArea();
        run = new JButton("run");
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runCpp();
            }
        });
        graLab = new JLabel("Grade");
        gra = new JTextField("", 2);
        graSave = new JButton("Save Grade");
        gradArea = new JPanel();
        gradArea.setLayout(new GridLayout(3,1));
        gradArea.add(graLab);
        gradArea.add(gra);
        gradArea.add(graSave);

        // gra.setText(read());

        graSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ss = gra.getText();
                grade(ss);
            }
        });

        this.setLayout(new GridLayout(3,1));
        this.add(run);
        this.add(rstDis);
        this.add(gradArea);
        this.setVisible(true);
        this.setBounds(300, 100, 650, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void runJava() {
        String sss = "";
        try {
            Process pro = Runtime.getRuntime().exec("cd /Users/yucheng/Desktop/No1");
            pro.waitFor();
            pro = Runtime.getRuntime().exec("javac Main.java"); //need to modify to get the path of the file.
            String line = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));
            while ((line = in.readLine()) != null) { //display the error location of compiling
                rstDis.append(line + "\r\n");
                // System.out.println("javac Main.java" + " The error : " + " " + line);
            }
            pro.waitFor();
            int exitValue = pro.exitValue();

                // System.out.println("javac Main.java" + " exitValue() " + pro.exitValue()); //show "0" if compile successfully

            if (exitValue == 1) {
                sss = Integer.toString(25);
                grade(sss);
                gra.setText(sss);
            } else {
                pro = Runtime.getRuntime().exec("java Main");
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getInputStream()));
                while ((line = in.readLine()) != null) { //display the output of the java program in console
                    rstDis.append(line + "\r\n");
                    // System.out.println("java Main" + " The output of this program : " + " " + line);
                }
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getErrorStream()));
                while ((line = in.readLine()) != null) { //display the error location when running the java program
                    rstDis.append(line + "\r\n");
                    // System.out.println("java Main" + " The error : " + " " + line);
                }
                pro.waitFor();
                exitValue = pro.exitValue();
                if (exitValue == 1) {
                    sss = Integer.toString(50);
                    grade(sss);
                    gra.setText(sss);
                } else {
                    sss = Integer.toString(90);
                    grade(sss);
                    gra.setText(sss);
                }
                // System.out.println("java Main" + " exitValue() " + pro.exitValue()); //show "0" when running successfully
                // read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runCpp() {
        String sss = "";
        try {
            Process pro = Runtime.getRuntime().exec("cd /Users/yucheng/Desktop/No1");
            pro.waitFor();
            pro = Runtime.getRuntime().exec("g++ Hello.cpp -o Hello"); //need to modify to get the path of the file.
            String line = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));
            while ((line = in.readLine()) != null) { //display the error location of compiling
                rstDis.append(line + "\r\n");
                // System.out.println("g++ Hello.cpp" + " The error : " + " " + line);
            }
            pro.waitFor();
            int exitValue = pro.exitValue();

            // System.out.println("g++ Hello.cpp" + " exitValue() " + pro.exitValue()); //show "0" if compile successfully

            if (exitValue == 1) {
                sss = Integer.toString(25);
                grade(sss);
                gra.setText(sss);
            } else {
                pro = Runtime.getRuntime().exec("./Hello");
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getInputStream()));
                while ((line = in.readLine()) != null) { //display the output of the java program in console
                    rstDis.append(line + "\r\n");
                    // System.out.println("run Hello" + " The output of this program : " + " " + line);
                }
                line = null;
                in = new BufferedReader(
                        new InputStreamReader(pro.getErrorStream()));
                while ((line = in.readLine()) != null) { //display the error location when running the java program
                    rstDis.append(line + "\r\n");
                    // System.out.println("run Hello" + " The error : " + " " + line);
                }
                pro.waitFor();
                exitValue = pro.exitValue();
                if (exitValue == 1) {
                    sss = Integer.toString(50);
                    grade(sss);
                    gra.setText(sss);
                } else {
                    sss = Integer.toString(90);
                    grade(sss);
                    gra.setText(sss);
                }
                // System.out.println("run Hello" + " exitValue() " + pro.exitValue()); //show "0" when running successfully
                // read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void runPython() {
        String sss = "";
        try {
            Process pro = Runtime.getRuntime().exec("python /Users/yucheng/Desktop/No1/Hi.py");
            String line = null;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pro.getInputStream()));
            while ((line = in.readLine()) != null) { //display the output of the java program in console
                rstDis.append(line + "\r\n");
                // System.out.println("run Hello" + " The output of this program : " + " " + line);
            }
            line = null;
            in = new BufferedReader(
                    new InputStreamReader(pro.getErrorStream()));
            while ((line = in.readLine()) != null) { //display the error location when running the java program
                rstDis.append(line + "\r\n");
                // System.out.println("run Hello" + " The error : " + " " + line);
            }
            pro.waitFor();
            int exitValue = pro.exitValue();
            if (exitValue == 1) {
                sss = Integer.toString(50);
                grade(sss);
                gra.setText(sss);
            } else {
                sss = Integer.toString(90);
                grade(sss);
                gra.setText(sss);
            }
            // System.out.println("run Hello" + " exitValue() " + pro.exitValue()); //show "0" when running successfully
            // read();
            } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public void grade(String str) {
        String file = "grade.txt";
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
    public void read() {
        String file = "grade.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                gra.setText(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return line;
    }
    public static void main(String[] args) throws IOException { //this is the test code, we could run it successfully.
        Compile java = new Compile();

        // java.compile();
    }
}
