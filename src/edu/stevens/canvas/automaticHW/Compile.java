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

public class Compile extends JFrame {
	private TextArea rstDis, comArea, display;
	private JButton run, graSave, comSave;
	private JTextField gra;
	private JLabel graLab, comLab;
	private JPanel gradArea, comPanel;
	private MenuBar bar;       // Menu bar  
	private Menu fileMenu;
	private MenuItem openItem, nextItem, closeItem;
	private FileDialog openDia, nextDia;
	private File file;
	private String dirpath, fileName;

	// public void setPath(String str) {
	// 	this.dirpath = str;
	// }
	// public void setFileName(String str) {
	// 	this.fileName = str;
	// }


	Compile() {
		
		rstDis = new TextArea();
		// run button
		run = new JButton("run");
		// grade area
		graLab = new JLabel("Grade");
		gra = new JTextField("", 2);
		graSave = new JButton("Save Grade");
		gradArea = new JPanel();
		gradArea.setLayout(new GridLayout(3,1));
		gradArea.add(graLab);
		gradArea.add(gra);
		gradArea.add(graSave);

		// comment area
		comLab = new JLabel("Comment: ");
		comArea = new TextArea();
		comSave = new JButton("Save Comment");
		comPanel = new JPanel();
		comPanel.setLayout(new GridLayout(3,1));
		comPanel.add(comLab);
		comPanel.add(comArea);
		comPanel.add(comSave);


		
		bar = new MenuBar(); // 
		display = new TextArea();

		fileMenu = new Menu("file");// Initial menu

		openItem = new MenuItem("open");// create open item
		nextItem = new MenuItem("next");// create next item
		closeItem = new MenuItem("quit");

		fileMenu.add(openItem);
		fileMenu.add(nextItem);
		fileMenu.add(closeItem);

		bar.add(fileMenu);
		this.setMenuBar(bar);

		openDia = new FileDialog(this, "open", FileDialog.LOAD);
		nextDia = new FileDialog(this, "save", FileDialog.SAVE);

		this.setTitle("Automatic HomeWork");
		this.setBounds(300, 100, 650, 600);
		this.setLayout(new GridLayout(5,1));
		this.add(comPanel);
		this.add(display);
		this.add(run);
		this.add(rstDis);
		this.add(gradArea);
		this.setVisible(true);
		// this.setBounds(300, 100, 650, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

		myEvent();
	}

	private void myEvent() {
        
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openDia.setVisible(true);
				
				dirpath = openDia.getDirectory();
				fileName = openDia.getFile();
				// System.out.println(dirpath);
				
				
				if (dirpath == null || fileName == null)
					return;
				else
					display.setText(null);
				file = new File(dirpath, fileName);

				try {
					BufferedReader bufr = new BufferedReader(new FileReader(file));
					String line = null;
					while ((line = bufr.readLine()) != null) {
						display.append(line + "\r\n");
					}
					bufr.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				run.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						compile(dirpath, fileName, file);
					}
				});

				graSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = gra.getText();
						grade(ss, dirpath);
					}
				});
				
				comSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = comArea.getText();
						comment(ss, dirpath);
					}
				});

			}

		});
		
		// next event listen;
		nextItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (file == null) {
				nextDia.setVisible(true);
				String dirpath = nextDia.getDirectory();
				String fileName = nextDia.getFile();
				
				if (dirpath == null || fileName == null)
					return;
				else
					file=new File(dirpath,fileName);
				}
				try {
					BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
					
					String text = display.getText();
					bufw.write(text);
					
					bufw.close();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		
		closeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	
	public void compile(String path, String fileName, File file) {
		String sss = "";
		String aaa= peel(fileName);
		System.out.println(aaa);
		System.out.println(path);
		try {
			// String com = "cd" + path;
			// Process pro = Runtime.getRuntime().exec(com); //need to modify to get the path of the file.
			// pro.waitFor();
			ProcessBuilder pb = new ProcessBuilder("javac", fileName);
			pb.directory(new File(path).getAbsoluteFile());
			Process pro = pb.start();
			// Runtime.getRuntime().exec("javac " + path + fileName);
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

			if (exitValue == 1) {
				sss = Integer.toString(25);
				grade(sss, path);
				gra.setText(sss);
			} else {

				// pro = Runtime.getRuntime().exec("cd " + path);
				// pro.waitFor();
				// pro = Runtime.getRuntime().exec("java " + aaa);
				// pro = Runtime.getRuntime().exec("pwd");
				pb = new ProcessBuilder("java", aaa);
				pb.directory(new File(path).getAbsoluteFile());
				pro = pb.start();
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
					grade(sss, path);
					gra.setText(sss);
				} else {
					sss = Integer.toString(90);
					grade(sss, path);
					gra.setText(sss);
				}
				// System.out.println("java Main" + " exitValue() " + pro.exitValue()); //show "0" when running successfully 
				// read();
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
//	public void run() {
//        String sss = "";
//        try {
//            Process pro = Runtime.getRuntime().exec("g++ Hello.cpp -o Hello"); //need to modify to get the path of the file.
//            String line = null;
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(pro.getErrorStream()));
//            while ((line = in.readLine()) != null) { //display the error location of compiling
//                rstDis.append(line + "\r\n");
//                // System.out.println("g++ Hello.cpp" + " The error : " + " " + line);
//            }
//            pro.waitFor();
//            int exitValue = pro.exitValue();
//
//            // System.out.println("g++ Hello.cpp" + " exitValue() " + pro.exitValue()); //show "0" if compile successfully
//
//            if (exitValue == 1) {
//                sss = Integer.toString(25);
//                grade(sss);
//                gra.setText(sss);
//            } else {
//                pro = Runtime.getRuntime().exec("./Hello");
//                line = null;
//                in = new BufferedReader(
//                        new InputStreamReader(pro.getInputStream()));
//                while ((line = in.readLine()) != null) { //display the output of the java program in console
//                    rstDis.append(line + "\r\n");
//                    // System.out.println("run Hello" + " The output of this program : " + " " + line);
//                }
//                line = null;
//                in = new BufferedReader(
//                        new InputStreamReader(pro.getErrorStream()));
//                while ((line = in.readLine()) != null) { //display the error location when running the java program
//                    rstDis.append(line + "\r\n");
//                    // System.out.println("run Hello" + " The error : " + " " + line);
//                }
//                pro.waitFor();
//                exitValue = pro.exitValue();
//                if (exitValue == 1) {
//                    sss = Integer.toString(50);
//                    grade(sss);
//                    gra.setText(sss);
//                } else {
//                    sss = Integer.toString(90);
//                    grade(sss);
//                    gra.setText(sss);
//                }
//                // System.out.println("run Hello" + " exitValue() " + pro.exitValue()); //show "0" when running successfully
//                // read();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//     public void run() {
//         String sss = "";
//         try {
//             Process pro = Runtime.getRuntime().exec("python Hi.py");
//             String line = null;
//             BufferedReader in = new BufferedReader(
//                     new InputStreamReader(pro.getInputStream()));
//             while ((line = in.readLine()) != null) { //display the output of the java program in console
//                 rstDis.append(line + "\r\n");
//                 // System.out.println("run Hello" + " The output of this program : " + " " + line);
//             }
//             line = null;
//             in = new BufferedReader(
//                     new InputStreamReader(pro.getErrorStream()));
//             while ((line = in.readLine()) != null) { //display the error location when running the java program
//                 rstDis.append(line + "\r\n");
//                 // System.out.println("run Hello" + " The error : " + " " + line);
//             }
//             pro.waitFor();
//             int exitValue = pro.exitValue();
//             if (exitValue == 1) {
//                 sss = Integer.toString(50);
//                 grade(sss);
//                 gra.setText(sss);
//             } else {
//                 sss = Integer.toString(90);
//                 grade(sss);
//                 gra.setText(sss);
//             }
//             // System.out.println("run Hello" + " exitValue() " + pro.exitValue()); //show "0" when running successfully
//             // read();
//             } catch (InterruptedException e1) {
//             e1.printStackTrace();
//         } catch (IOException e1) {
//             e1.printStackTrace();
//         }
//     }
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
		// return line;
	}
	public String peel(String str) {
		if (str == null) 
			return str;
		int len = str.length();
		int i = str.indexOf('.');
		String rst = str.substring(0, i);
		return rst;
	}
	public static void main(String[] args) throws IOException { //this is the test code, we could run it successfully.
 		Compile java = new Compile();
		// System.out.println(dirpath);
		// System.out.println(fileName);

 		// java.compile();
		// String s = java.peel("HelleWorld.java");
		// System.out.println(s);
	}
}

class Code {
	private String path;
	private String fileName;
	public void setPath(String str) {
		this.path = str;
	}
	public void setName(String str) {
		this.fileName = str;
	}
}