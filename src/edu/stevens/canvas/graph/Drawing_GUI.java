package edu.stevens.canvas.graph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Drawing_GUI extends JFrame {
	public Drawing_GUI(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String chartTypeChoosen, double fullScore, int m) {
		this.setTitle("Drawing GUI");
		this.setSize(1500, 1000);
		Container c = getContentPane();
		
		JLabel title = new JLabel("Graph");
		title.setFont(new Font("Helvetica", Font.BOLD, 50));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(title, BorderLayout.NORTH);
		
		DrawingArea d = new DrawingArea(allStudent, allAssignment, assignmentTypeChoosen, chartTypeChoosen, fullScore, m);
		c.add(d, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Drawing_GUI(true, false, "assignment1", "pie", 100, 20);
	}
}
