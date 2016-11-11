package edu.stevens.canvas.graph;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * class to draw the graph
 * @author Lan Chang
 *
 */

public class DrawingArea extends JPanel {
	private Drawing_GUI gui;
	private ArrayList<Rect> bars;
	private boolean allStudent = true;
	private boolean allAssignment = true;
	private String assignmentTypeChoosen;
	
	public DrawingArea(Drawing_GUI gui) {
		this.gui = gui;
		this.allStudent = gui.getAllStudent();
		this.allAssignment = gui.getAllAssignment();
		this.assignmentTypeChoosen = gui.getAssignmentTypeChoosen();
	}
	
	public void paint(Graphics g) {
		bars = new ArrayList<Rect>();
		
		drawingBackgroud(g);
		
		if (allStudent == true) {
			if (allAssignment == true) {
				drawingHistogramAll();
			}
		}
		
		if (allStudent == true) {
			if (allAssignment == false) {
				//
			}
		}
		
		// more selection
		
		g.setColor(Color.BLACK);
		for (Rect r : bars) {
			r.paint(g);
		}
	}
	
	public void drawingBackgroud(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawLine(150, 100, 150, 700);
		g.drawLine(150, 700, 1350, 700);
		
		g.setFont(new Font("Helvetica", Font.BOLD, 35));
		g.drawString("Number of Student", 100, 80);
		g.drawString("Grade", 1300, 750);
		g.drawString("100", 1200, 750);
		g.drawString("90", 1100, 750);
		g.drawString("80", 1000, 750);
		g.drawString("70", 900, 750);
		g.drawString("60", 800, 750);
		g.drawString("50", 700, 750);
		g.drawString("40", 600, 750);
		g.drawString("30", 500, 750);
		g.drawString("20", 400, 750);
		g.drawString("10", 300, 750);
		g.drawString("0", 200, 750);
	}
	
	public void drawingHistogramAll() {
		int startX = 200;
		int startY = 700;
		int w = 50;
		
		int[] h = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 3, 5, 10, 12, 18, 18, 10, 8, 2};
		
		for (int i = 0; i < 20; i++) {
			int h2 = h[i] * 20;
			bars.add(new Rect(startX + w * i, startY - h2, w, h2));
		}
	}
	
	public void drawingHistogramSingle() {
		
	}
	
	public void drawingHistogram() {
		
	}
	
	public void clear() {
		repaint();
	}
}