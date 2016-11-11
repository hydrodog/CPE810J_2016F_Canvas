package edu.stevens.canvas.graph;

import java.awt.*;
import javax.swing.*;

/**
 * class to display the drawing GUI
 * @author Lan Chang
 *
 */

public class Drawing_GUI extends JFrame {
	// private Graph_GUI gui;
	private boolean allStudent = true;
	private boolean allAssignment = true;
	private String assignmentTypeChoosen;
	
	public Drawing_GUI() {  // Graph_GUI call this constructor
		//this.gui = ui;
		this.setTitle("Drawing GUI");
		this.setSize(1500, 1000);
		this.setResizable(false);
		Container c = getContentPane();
		
		JLabel title = new JLabel("Graph");
		title.setFont(new Font("Helvetica", Font.BOLD, 50));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(title, BorderLayout.NORTH);
		
		DrawingArea d = new DrawingArea(this);
		c.add(d, BorderLayout.CENTER);
		this.setVisible(true);
		
		// need to get allStudent, allAssignment, assignmentTypeChoosen
		// this.allStudent = ui.getAllStudent();
		// this.allAssignment = ui.getAllAssignment();
		// this.assignmentTypeChoosen = ui.getAssignmentTypeChoosen();
	}
	
	public boolean getAllStudent() {
		return allStudent;
	}
	
	public boolean getAllAssignment() {
		return allAssignment;
	}
	
	public String getAssignmentTypeChoosen() {
		return assignmentTypeChoosen;
	}
	
	public static void main(String[] args) {
		new Drawing_GUI();
	}
}