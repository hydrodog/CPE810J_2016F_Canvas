package edu.stevens.canvas.graph;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author: Zeming Wang
 * @modified:
 * This class is for the GUI part of graph group.
 * It will build an interface to react with the user including buttons to choose the mode, type of graph
 * 11.16:
 * Change the buttons to radio buttons
 * Reallocate the GUI
 * Talk with the download group, and find that the data they get doesn't show us the type of assignment
 * Which means we cannot directly know which assignment is quiz or test or project.
 * Talk with the download group, know that data is store in .txt file
 * Add read function to this class
 * 11.19~12.2:
 * See that there should be pie chart and bar chart, add these two buttons and action listeners.
*/

public class Graph_GUI extends JFrame implements ActionListener, AdjustmentListener {
	private JTextField inputText;
	
	// These are used to choose the data range
	// is the target data include one assignment, all assignment or one category of assignment?
	// Assignment Type: Quiz, Test, Project, Assignment, All (and the weight of each type)
	private final JLabel assignmentType = new JLabel("Assignment Type:");
	private final String[] assignmentTypeName = {"Assignment", "Quiz", "Test", "Project", "All"};
	private JRadioButton[] assignmentTypeButton = new JRadioButton[assignmentTypeName.length];
	
	private final String[] assignmentTypePercent = {"Assignment_p", "Quiz_p", "Test_p", "Project_p", "All_p"};
	private JTextField[] assignmentTypePercentField = new JTextField[assignmentTypePercent.length];
	
	// Assignment Num: All Assignment, One Assignment and Assignment Name
	private final JLabel assignmentNum = new JLabel("Assignment Num:");
	private final String[] assignmentNumName = {"All Assignment", "One Assignment"};
	private JRadioButton[] assignmentNumButton = new JRadioButton[assignmentNumName.length];
	private final JLabel assignmentName = new JLabel("Assignment Name:");
	private JTextField assignmentChoosen = new JTextField();
	
	// Student Type: All Student, Single Student and Student Name
	private final JLabel studentType = new JLabel("Student Num:");
	private final String[] studentTypeName = {"All Student", "Single Student"};
	private JRadioButton[] studentTypeButton = new JRadioButton[studentTypeName.length];
	private final JLabel studentName = new JLabel("Student Name:");
	private JTextField studentChoosen = new JTextField();
	
	// Graph Type: Histogram, Pie Chart
	private final JLabel graphType = new JLabel("Graph Type:");
	private final String[] graphTypeName = {"Histogram", "Pie Chart"};
	private JRadioButton[] graphTypeButton = new JRadioButton[graphTypeName.length];
	
	// Graph Attribute: Width of Group, the width means how many data are there in a group
	private final JLabel lb1 = new JLabel("Graph Option:");
	private JLabel lb2 = new JLabel("Width of Group: 10");
	private JScrollBar jsb = new JScrollBar(JScrollBar.HORIZONTAL, 10, 10, 1, 30);
	
	// Button: Calculate the Statistics, Draw the Graph
	private JButton buttonDraw = new JButton("Draw");
	private JButton buttonCalculate = new JButton("Calculate");
	
	// Show Statistics Result
	private JTextArea outputText = new JTextArea("");
	
	// Menu
	private JMenuBar menubar;
	private JMenu menuFile;
	private File file;
	private JMenuItem openFile, saveFile, exit;
	private FileDialog openDia, saveDia;
	
	// Option boolean or String
	private boolean allStudent = false, allAssignment = false;
	private String assignmentTypeChoosen, graphTypeChoosen;
	
	// Font
	private final Font f = new Font("Helvetica", Font.BOLD, 30);
	
	// Graph
	private String savePath;
	private Drawing_GUI dgui;
	
	// Constructor of the frame
	public Graph_GUI(){
		super();
		init();
		this.setSize(2000, 1000);
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("Graph GUI");
		this.setLocation(100, 100);
		this.setResizable(false);
		//this.pack();
	}
	
	private void init() {
		// Set the menu
		menubar = getMenu();
		setJMenuBar(menubar);
		
		inputText = new JTextField(14);
		
		// Assignment Type, setup buttons and panel, and the text area to let user input the weight of each type of assignment.
		JPanel commandPanel1 = new JPanel();
		commandPanel1.setLayout(new GridLayout(6, 1, 50, 20));
		assignmentType.setFont(f);
		commandPanel1.add(assignmentType);
		ButtonGroup assignmentTypeGroup = new ButtonGroup();
		for (int i = 0; i < assignmentTypeName.length; i++) {
			assignmentTypeButton[i] = new JRadioButton(assignmentTypeName[i]);
			assignmentTypeButton[i].setFont(f);
			assignmentTypeGroup.add(assignmentTypeButton[i]);
			assignmentTypeButton[i].setForeground(Color.blue);
			assignmentTypeButton[i].addActionListener(this);
			
			assignmentTypePercentField[i] = new JTextField();
			assignmentTypePercentField[i].setFont(f);
			
			JPanel temp = new JPanel();
			temp.setLayout(new GridLayout(1, 2, 50, 20));
			
			temp.add(assignmentTypeButton[i]);
			temp.add(assignmentTypePercentField[i]);
			
			commandPanel1.add(temp);
		}
		assignmentTypePercentField[4].setEditable(false);
		
		// Assignment Num and Assignment Name
		JPanel commandPanel3 = new JPanel();
		commandPanel3.setLayout(new GridLayout(5, 1, 50, 20));
		assignmentNum.setFont(f);
		commandPanel3.add(assignmentNum);
		ButtonGroup assignmentNumGroup = new ButtonGroup();
		for (int i = 0; i < assignmentNumName.length; i++) {
			assignmentNumButton[i] = new JRadioButton(assignmentNumName[i]);
			assignmentNumButton[i].setFont(f);
			commandPanel3.add(assignmentNumButton[i]);
			assignmentNumGroup.add(assignmentNumButton[i]);
			assignmentNumButton[i].setForeground(Color.blue);
			assignmentNumButton[i].addActionListener(this);
		}
		assignmentName.setFont(f);
		commandPanel3.add(assignmentName);
		assignmentChoosen.setFont(f);
		commandPanel3.add(assignmentChoosen);
		
		// Student Type and Student Name
		JPanel commandPanel2 = new JPanel();
		commandPanel2.setLayout(new GridLayout(5, 1, 50, 20));
		studentType.setFont(f);
		commandPanel2.add(studentType);
		ButtonGroup studentTypeGroup = new ButtonGroup();
		for (int i = 0; i < studentTypeName.length; i++) {
			studentTypeButton[i] = new JRadioButton(studentTypeName[i]);
			studentTypeButton[i].setFont(f);
			commandPanel2.add(studentTypeButton[i]);
			studentTypeGroup.add(studentTypeButton[i]);
			studentTypeButton[i].setForeground(Color.blue);
			studentTypeButton[i].addActionListener(this);
		}
		studentName.setFont(f);
		commandPanel2.add(studentName);
		studentChoosen.setFont(f);
		commandPanel2.add(studentChoosen);
		
		// Graph Option
		JPanel commandPanel4 = new JPanel();
		commandPanel4.setLayout(new GridLayout(6, 1, 50, 20));
		graphType.setFont(f);
		commandPanel4.add(graphType);
		ButtonGroup graphTypeGroup = new ButtonGroup();
		for (int i = 0; i < graphTypeName.length; i++) {
			graphTypeButton[i] = new JRadioButton(graphTypeName[i]);
			graphTypeButton[i].setFont(f);
			commandPanel4.add(graphTypeButton[i]);
			graphTypeGroup.add(graphTypeButton[i]);
			graphTypeButton[i].setForeground(Color.blue);
			graphTypeButton[i].addActionListener(this);
		}
		lb1.setFont(f);
		lb2.setFont(f);
		jsb.addAdjustmentListener(this);
		lb2.setForeground(Color.blue);
		commandPanel4.add(lb1);
		commandPanel4.add(lb2);
		commandPanel4.add(jsb);
		
		// Button
		JPanel buttonArea = new JPanel();
		buttonArea.setLayout(new GridLayout(1, 2, 50, 20));
		buttonCalculate.setFont(f);
		buttonCalculate.addActionListener(this);
		buttonDraw.setFont(f);
		buttonDraw.addActionListener(this);
		buttonArea.add(buttonCalculate);
		buttonArea.add(buttonDraw);
		
		// add these components to the window
		Container c = this.getContentPane();
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		outputText.setFont(f);
		outputText.setEditable(false);
		left.add("Center", outputText);
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		JPanel select = new JPanel();
		select.setLayout(new GridLayout(2, 2, 50, 20));
		select.add(commandPanel1);
		select.add(commandPanel4);
		select.add(commandPanel3);
		select.add(commandPanel2);
		right.add("Center", select);
		right.add("South", buttonArea);
		c.setLayout(new GridLayout(1, 2, 50, 20));
		c.add(left);
		c.add(right);
	}
	
	// The actionlistener reacts to the operation on Menu
	class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Exit")) {
				System.exit(0); 
			}
			
			if (e.getActionCommand().equals("Open a file..")) {
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();
				String fileName = openDia.getFile();
				
				if (dirPath == null || fileName == null)
					return ;
				
				inputText.setText("");
				
				file = new File(dirPath, fileName);
				
				try {
					BufferedReader bufr = new BufferedReader(new FileReader(file));
					// Read the text file by line.
						
					String line = null;
						
					while((line = bufr.readLine()) != null) {
						//where should we put these input text?
						//Now we know that the input data will be store in .txt
						//But what's the format, and will there be any available data that is sorted by other group?
					}
					bufr.close();
				}
				catch (IOException ex) {
					throw new RuntimeException("Failed to open");
				}
			}
			
			if (e.getActionCommand().equals("Save the graph")) {
				if (file == null) {
					saveDia.setVisible(true);
					String dirPath = saveDia.getDirectory();
					String fileName = saveDia.getFile();

					if (dirPath == null || fileName == null)
						return ;
					savePath = dirPath + fileName;
					
					try {
						dgui.save(savePath);
					}
					catch (Exception ex) {//It should be IOExceotion
						//throw new RuntimeException("Failed to save");
					}
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();
		if (label.equals(studentTypeName[0])) {//all student
			allStudent = true;
		}
		else if (label.equals(studentTypeName[1])) {//single student
			allStudent = false;
		}
		
		if (label.equals(assignmentNumName[0])) {//all assignment
			allAssignment = true;
		}
		
		else if (label.equals(assignmentNumName[1])) {//one assignment
			allAssignment = false;
		}
		
		if (label.equals(graphTypeName[0])) {//histogram
			graphTypeChoosen = "bar";
		}
		else if (label.equals(graphTypeName[1])) {//pie chart
			graphTypeChoosen = "pie";
		}
		
		if (label.equals(assignmentTypeName[0])) {//quiz
			assignmentTypeChoosen = assignmentTypeName[0];
		}
		else if (label.equals(assignmentTypeName[1])) {//test
			assignmentTypeChoosen = assignmentTypeName[1];
		}
		else if (label.equals(assignmentTypeName[2])) {//project
			assignmentTypeChoosen = assignmentTypeName[2];
		}
		else if (label.equals(assignmentTypeName[3])) {//assignment or homework
			assignmentTypeChoosen = assignmentTypeName[3];
		}
		else if (label.equals(assignmentTypeName[4])) {//all
			assignmentTypeChoosen = assignmentTypeName[4];
		}
		
		// draw the graph
		if (label.equals("Draw")) {
			// get the graph option
			int widthOfGroup = jsb.getValue();
			
			// get the assignment name
			String assignment = assignmentChoosen.getText();
			
			// get the percentage
			int[] percent = new int[4];
			int p_sum = 0;
			for (int i = 0; i < percent.length; i++) {
				if (!assignmentTypePercentField[i].getText().equals("")) {
					percent[i] = Integer.parseInt(assignmentTypePercentField[i].getText());
				}
				else {
					percent[i] = 0;
				}
				p_sum += percent[i];
			}
			boolean z1 = percent[0] >= 0 && percent[1] >= 0 && percent[2] >= 0 && percent[3] >= 0;
			assignmentTypePercentField[4].setText(p_sum + "");
			
			boolean z2 = widthOfGroup == 1 || widthOfGroup == 2 || widthOfGroup == 3 || widthOfGroup == 4
					|| widthOfGroup == 5 || widthOfGroup == 10 || widthOfGroup == 20;
			
			double width = widthOfGroup;
			
			if (z1 && z2 && (p_sum == 100 || !assignmentTypeChoosen.equals("All"))) {
				// get the grade and the number of each group
				GradeGroup gg = new GradeGroup(allStudent, allAssignment, assignmentTypeChoosen, assignment, width, percent);
				double full = gg.getFull();
				int group = (int) (full / width);
				ArrayList<Integer> num = gg.getNum();
				ArrayList<Double> grade = gg.getGrade();
					
				if (gg.getDone()) {
					dgui = new Drawing_GUI(num, grade, graphTypeChoosen, full, group);
				}
			}
			if (!z1) {
				outputText.setText("Each percentage must not be less than 0!");
			}
			if (p_sum != 100 && assignmentTypeChoosen.equals("All")) {
				outputText.setText("The sum of percentage must be 100!");
			}
			if (!z2) {
				outputText.setText("Bad width!");
			}
		}
		
		if (label.equals("Calculate")) {
			// get the assignment name
			String assignment = assignmentChoosen.getText();
						
			// get the percentage
			int[] percent = new int[4];
			int p_sum = 0;
			for (int i = 0; i < percent.length; i++) {
				if (!assignmentTypePercentField[i].getText().equals("")) {
					percent[i] = Integer.parseInt(assignmentTypePercentField[i].getText());
				}
				else {
					percent[i] = 0;
				}
				p_sum += percent[i];
			}
			boolean z1 = percent[0] >= 0 && percent[1] >= 0 && percent[2] >= 0 && percent[3] >= 0;
			assignmentTypePercentField[4].setText(p_sum + "");
			
			if (z1 && (p_sum == 100 || !assignmentTypeChoosen.equals("All"))) {
				// get the grade
				GradeGroup gg = new GradeGroup(allStudent, allAssignment, assignmentTypeChoosen, assignment, percent);
				ArrayList<Double> grade = gg.getGrade();
				
				if (gg.getDone()) {
					SummaryStat ss = new SummaryStat(grade);
					String str = ss.getStr().toString();
					outputText.setText(str);
				}
			}
			if (!z1) {
				outputText.setText("Each percentage must not be less than 0!");
			}
			if (p_sum != 100 && assignmentTypeChoosen.equals("All")) {
				outputText.setText("The sum of percentage must be 100!");
			}
		}
	}
	
	JMenuBar getMenu() {// Set the menu and menubar
		MenuListener myMenuListener = new MenuListener();
		menubar = new JMenuBar();
		menuFile = new JMenu("File");
		menuFile.setFont(f);
		openFile = new JMenuItem("Open a file..");
		openFile.setFont(f);
		// What should the open file do? to load a file that store the grade?
		saveFile = new JMenuItem("Save the graph");
		saveFile.setFont(f);
		// Save file should save the image
		exit = new JMenuItem("Exit");
		exit.setFont(f);
		openFile.addActionListener(myMenuListener);
		saveFile.addActionListener(myMenuListener);
		exit.addActionListener(myMenuListener);
		menuFile.add(openFile);
		menuFile.add(saveFile);
		menuFile.addSeparator();
		menuFile.add(exit);
		menubar.add(menuFile);
		openDia = new FileDialog(this, "my open file", FileDialog.LOAD);
		saveDia = new FileDialog(this, "my save file", FileDialog.SAVE);
		return menubar;
	}
	
	public void adjustmentValueChanged(AdjustmentEvent e) {
		lb2.setText("Width of Group: " + e.getValue());
	}
	
	// This part is for testing this GUI
	public static void main(String args[]) {
		Graph_GUI c1 = new Graph_GUI();
		c1.setVisible(true);
	}
}
