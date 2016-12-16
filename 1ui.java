package ui;

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
import javax.swing.JTextArea;

public class ui extends JFrame {
	private TextArea rstDis, display;
	private JTextArea comArea;
	private JButton run, graSave, comSave;
	private JTextField gra;
	private JLabel graLab, comLab, rstLab;
	private JPanel gradArea, comPanel, rstPanel, panel, disPanel, runPanel, pane3;
	private MenuBar bar;       // Menu bar  
	private Menu fileMenu;
	private MenuItem openItem, nextItem, closeItem;
	private FileDialog openDia, nextDia;
	private File file;
	private String dirpath, fileName;
	private String result = "";
	private String sss = "";
	grade grade1 = new grade();
	comment comment1 = new comment();

	ui() {
		
		rstDis = new TextArea();
		run = new JButton("run");
		runPanel = new JPanel();
		runPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		graLab = new JLabel("Grade");
		gra = new JTextField("", 3);
		graSave = new JButton("Save Grade");
		
		comLab = new JLabel("Comment ");
		comArea = new JTextArea("");
		comArea.setLineWrap(true);
		comSave = new JButton("Save Comment");

		bar = new MenuBar(); // 
		fileMenu = new Menu("file");// Initial menu
		openItem = new MenuItem("open");// create open item
		nextItem = new MenuItem("next");// create next item
		closeItem = new MenuItem("quit");
		fileMenu.add(openItem);
		fileMenu.add(nextItem);
		fileMenu.add(closeItem);
		bar.add(fileMenu);
		this.setMenuBar(bar);

		display = new TextArea();
		rstLab = new JLabel("Result");

		openDia = new FileDialog(this, "open", FileDialog.LOAD);
		nextDia = new FileDialog(this, "save", FileDialog.SAVE);

		this.setTitle("Automatic HomeWork");
		this.setBounds(300, 100, 700, 600);
		this.setLayout(new GridBagLayout());
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

	private void myEvent() {
        
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
						if(get(dirpath + fileName).equals("java")) {
							childJava java = new childJava();
							java.compile(dirpath, fileName, file);
							result = java.getResult();
							System.out.println(result);
							rstDis.append(result + "\r\n");
							sss = java.getGrade();
							gra.setText(sss);
						} else if(get(dirpath + fileName).equals("cpp")) {
							System.out.println("1");
							childCpp cpp = new childCpp();
							cpp.compile(dirpath, fileName, file);
							result = cpp.getResult();
							System.out.println(result);
							rstDis.append(result + "\r\n");
							sss = cpp.getGrade();
							gra.setText(sss);
						} else if(get(dirpath + fileName).equals("py")) {
							childPy py = new childPy();
							py.compile(dirpath, fileName, file);
							result = py.getResult();
							System.out.println(result);
							rstDis.append(result + "\r\n");
							sss = py.getGrade();
							gra.setText(sss);
						}	else {
							
						}					
					}
				});

				graSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = gra.getText();
						grade1.grade(ss, dirpath);
					}
				});
				
				comSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ss = comArea.getText();
						comment1.comment(ss, dirpath);
					}
				});

			}

		});
		
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

	public static void main(String[] args) throws IOException { //this is the test code, we could run it successfully.
 		ui java = new ui();
	}

public String get(String str) {
	String suffix = "";
	String prefix = "";
	char a = '.';
	char b = '/';
	int ad1 = 0, ad2 = 0;
		char[] ss = str.toCharArray();
		for(int i = 0; i < ss.length; i++) {
			if(ss[i] == a) {
				ad1 = i;
				for(int j = i + 1; j < ss.length; j++)        					
					suffix += ss[j];
			}
		}
		for(int x = ad1; x > 0; x--) {
			if(ss[x] == b) {
				ad2 = x;
				break;
			}
		}
		for(int y = 0; y < ad2; y++) {     					
			prefix += ss[y];
		}
		return suffix;
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
}}
