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

public class ui extends JFrame {
	private TextArea rstDis, display;
	private JTextArea comArea;
	private JButton run, graSave, comSave;
	private JTextField gra;
	private JLabel graLab, comLab, rstLab;
	private JPanel gradArea, comPanel, rstPanel, panel, disPanel, runPanel, pane3;
	private MenuBar bar;       // Menu bar  
	private Menu fileMenu;
	private MenuItem openItem, saveItem, closeItem;
	private FileDialog openDia, saveDia, nextDia;
	private File file;
	private String dirpath, fileName;
	private String result = "";
	private String sss = "";
	grade grade1 = new grade();
	comment comment1 = new comment();
	childJava java = new childJava();
	childCpp cpp = new childCpp();
	childPy py = new childPy();


	ui() {
		
		rstDis = new TextArea();
		//run button
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
		bar = new MenuBar();
		fileMenu = new Menu("file");// Initial menu
		// fileMenu.setShortcut(new MenuShortcut(KeyEvent.VK_F)); 
		openItem = new MenuItem("open");// create open item
		openItem.setShortcut(new MenuShortcut(KeyEvent.VK_O));   //set shortcut for OpenItem
		saveItem = new MenuItem("save");// create save item
		saveItem.setShortcut(new MenuShortcut(KeyEvent.VK_S));
		closeItem = new MenuItem("quit");
		closeItem.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(closeItem);
		bar.add(fileMenu);
		this.setMenuBar(bar);

		display = new TextArea();
		rstLab = new JLabel("Result");

		openDia = new FileDialog(this, "open", FileDialog.LOAD);
		saveDia = new FileDialog(this, "save", FileDialog.SAVE);

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
				rstDis.setText("");
				comArea.setText("");
				gra.setText("");
				openDia.setVisible(true);
				
				dirpath = openDia.getDirectory();
				fileName = openDia.getFile();				
				if (dirpath == null || fileName == null)
					return;
				else
					display.setText(null);
				file = new File(dirpath, fileName);

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
			}
		});
		
		// Run button action
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rstDis.setText(" ");
				comArea.setText(" ");
				gra.setText(" ");
				result = "";
				if(get(dirpath + fileName).equals("java")) {
					java.compile(dirpath, fileName, file);
					result = java.getResult();
					rstDis.append(result + "\r\n");
					sss = java.getGrade();
					gra.setText(sss);
					java.result = "";
					java.sss = "";
					System.out.println("result = " + result);
				} else if(get(dirpath + fileName).equals("cpp")) {
					cpp.compile(dirpath, fileName, file);
					result = cpp.getResult();
					rstDis.append(result + "\r\n");
					sss = cpp.getGrade();
					gra.setText(sss);
					cpp.result = "";
					cpp.sss = "";			
				} else if(get(dirpath + fileName).equals("py")) {
					py.compile(dirpath, fileName, file);
					result = py.getResult();
					rstDis.append(result + "\r\n");
					sss = py.getGrade();
					gra.setText(sss);
					py.result = "";
					py.sss = "";
				} else {
					display.setText("Please choose the .java/.cpp/.py file for compiling.");
				}
			}
		});
		
		// Button to save the grade into the certain path
		graSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ss = gra.getText();
				grade1.grade(ss, dirpath);
			}
		});
		
		// Button to save comments into the certain path
		comSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ss = comArea.getText();
				comment1.comment(ss, dirpath);
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
}