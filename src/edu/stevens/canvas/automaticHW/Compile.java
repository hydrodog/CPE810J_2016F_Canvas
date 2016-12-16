import java.io.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.TextArea;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class Compile extends JFrame {
	private TextArea rstDis, display;
	private JTextArea comArea;
	private JButton run, graSave, comSave;
	private JTextField gra;
	private JLabel graLab, comLab, rstLab;
	private JPanel gradArea, comPanel, rstPanel, panel, disPanel, runPanel, pane3;
	private MenuBar bar;         
	private Menu fileMenu;
	private MenuItem openItem, saveItem, closeItem, nextItem;
	private FileDialog openDia, saveDia, nextDia;
	private File file;
	private String dirpath, fileName;

	Compile() {
		
		rstDis = new TextArea();
		// run button
		run = new JButton("run");
		runPanel = new JPanel();
		runPanel.setLayout(new GridBagLayout());
		
		// grade area
		graLab = new JLabel("Grade");
		gra = new JTextField("", 3);
		graSave = new JButton("Save Grade");

		// comment area
		comLab = new JLabel("Comment ");
		comArea = new JTextArea("");
		comArea.setLineWrap(true);
		comSave = new JButton("Save Comment");

		// menu bar
		bar = new MenuBar(); // 
		fileMenu = new Menu("file");// Initial menu
		// fileMenu.setShortcut(new MenuShortcut(KeyEvent.VK_F)); 
		openItem = new MenuItem("open(O)");// create open item
		openItem.setShortcut(new MenuShortcut(KeyEvent.VK_O));   //set shortcut for OpenItem 
		saveItem = new MenuItem("save");// create save item
		saveItem.setShortcut(new MenuShortcut(KeyEvent.VK_S));
		closeItem = new MenuItem("quit");
		closeItem.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
		nextItem = new MenuItem("next");
		nextItem.setShortcut(new MenuShortcut(KeyEvent.VK_N));
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(nextItem);
		fileMenu.add(closeItem);
		bar.add(fileMenu);
		this.setMenuBar(bar);

		display = new TextArea();
		rstLab = new JLabel("Result");

		openDia = new FileDialog(this, "open", FileDialog.LOAD);
		saveDia = new FileDialog(this, "save", FileDialog.SAVE);
		nextDia = new FileDialog(this, "next", FileDialog.LOAD);

		this.setTitle("Automatic HomeWork");
		this.setBounds(300, 100, 700, 600);
		this.setLayout(new GridBagLayout());

		// Add components into frame
		GridBagConstraints c = new GridBagConstraints();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; 
		c.gridy = 0;
		c.gridwidth = 8;
		c.gridheight = 7;
		this.add(display, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 9; 
		c.gridy = 1;
		c.gridwidth = 1;
		this.add(run, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 9; 
		c.gridy = 4;
		c.gridwidth = 1;
		this.add(graLab, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 8; 
		c.gridy = 5;
		c.gridwidth = 2;
		this.add(gra, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 9; 
		c.gridy = 6;
		c.gridwidth = 1;
		this.add(graSave, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 9; 
		c.gridy = 7;
		c.gridwidth = 1;
		this.add(comLab, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 9; 
		c.gridy = 8;
		c.gridwidth = 2;
		c.gridheight = 1;
		this.add(comArea, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 9; 
		c.gridy = 9;
		c.gridwidth = 1;
		this.add(comSave, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; 
		c.gridy = 7;
		c.gridwidth = 1;
		this.add(rstLab, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0; 
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(rstDis, c);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myEvent();
	}

	// Event method for components
	private void myEvent() {
		// action of openItem;
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				openDia.setVisible(true);
				
				dirpath = openDia.getDirectory();
				fileName = openDia.getFile();
				
				if (dirpath == null || fileName == null)
					return;
				else
					display.setText(null);
				file = new File(dirpath, fileName);

				// Clear the result Display
				if (!"".equals(rstDis.getText())) {
					rstDis.setText(null);
				}
				

				// Display the code file into TextArea
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
				// Run button action
				run.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// compile the code and grade in the dirtory the code belongs to
						compile(dirpath, fileName, file);
					}
				});
				// Button to save the grade into the certain path
				graSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = gra.getText();
						grade(ss, dirpath);
					}
				});
				// Button to save comments into the certain path
				comSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = comArea.getText();
						comment(ss, dirpath);
					}
				});
			}
		});
		// next Item listener
		nextItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nextDia.setVisible(true);
				
				dirpath = nextDia.getDirectory();
				fileName = nextDia.getFile();
				
				if (dirpath == null || fileName == null)
					return;
				else
					display.setText(null);
				file = new File(dirpath, fileName);

				// Clear the result Display
				if (!"".equals(rstDis.getText())) {
					rstDis.setText(null);
				}
				

				// Display the code file into TextArea
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
				// Run button action
				run.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// compile the code and grade in the dirtory the code belongs to
						compile(dirpath, fileName, file);
					}
				});
				// Button to save the grade into the certain path
				graSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = gra.getText();
						grade(ss, dirpath);
					}
				});
				// Button to save comments into the certain path
				comSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = comArea.getText();
						comment(ss, dirpath);
					}
				});
			}
		});
		
		// save event listen
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (file == null) {
				saveDia.setVisible(true);
				String dirpath = saveDia.getDirectory();
				String fileName = saveDia.getFile();
				
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
		// Close event listener
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
				
			}

			pro.waitFor();
			int exitValue = pro.exitValue();

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
				}
				line = null;
				in = new BufferedReader(
					new InputStreamReader(pro.getErrorStream()));
				while ((line = in.readLine()) != null) { //display the error location when running the java program
					rstDis.append(line + "\r\n");
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
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	// Grade method
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
	// Comment method
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
	// Peel method to peel the postfix of file name, and used in terminal (java fileName)
	public String peel(String str) {
		if (str == null) 
			return str;
		int len = str.length();
		int i = str.indexOf('.');
		String rst = str.substring(0, i);
		return rst;
	}

	public static void main(String[] args) throws IOException { 
 		Compile java = new Compile();
	}
}
