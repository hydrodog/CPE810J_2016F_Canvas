import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.TextArea;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;

class Compile extends JFrame {
	private TextArea rstDis;
	private JButton run;
	Compile() {
		
		rstDis = new TextArea();
		run = new JButton("run");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compile();
			}
		});
		this.setLayout(new GridLayout(2,1));
		this.add(run);
		this.add(rstDis);
		this.setVisible(true);
		this.setBounds(300, 100, 650, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void compile() {
		try {
		    Process pro = Runtime.getRuntime().exec("javac HelloWorld.java"); //need to modify to get the path of the file.
		    String line = null;
		    BufferedReader in = new BufferedReader(
			    new InputStreamReader(pro.getErrorStream()));
		    while ((line = in.readLine()) != null) { //display the error location of compiling
		    	rstDis.append(line + "\r\n");
			// System.out.println("javac Main.java" + " The error : " + " " + line); 
		    }
		    pro.waitFor();
		    int exitValue = pro.exitValue();

		//     System.out.println("javac Main.java" + " exitValue() " + pro.exitValue()); //show "0" if compile successfully

		    if (exitValue != 1) {
			pro = Runtime.getRuntime().exec("java HelloWorld");
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
			// System.out.println("java Main" + " exitValue() " + pro.exitValue()); //show "0" when running successfully 

		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException { //this is the test code, we could run it successfully.
 		Compile java = new Compile();

 		java.compile();
	}
}