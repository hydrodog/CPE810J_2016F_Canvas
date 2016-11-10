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
		drawingLabel(g);
		
		if (allStudent == true) {
			if (allAssignment == true) {
				drawingHistogramAll();
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
		int x1 = 150;
		int x2 = 1350;
		int y1 = 100;
		int y2 = 700;
		g.drawLine(x1, y1, x1, y2);
		g.drawLine(x1, y2, x2, y2);
	}
	
	public void drawingLabel(Graphics g) {
		Font font = new Font("Helvetica", Font.BOLD, 35);
		g.setFont(font);
		g.drawString("number(student)", 100, 100);
		g.drawString("grade", 1300, 750);
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
		int w = 100;
		
		int[] h = {0, 1, 1, 1, 2, 4, 10, 15, 20, 10}; // get the arraylist from GradeGroup
		
		for (int i = 0; i < 10; i++) {
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