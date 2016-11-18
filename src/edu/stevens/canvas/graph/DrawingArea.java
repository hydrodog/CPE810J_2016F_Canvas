package edu.stevens.canvas.graph;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * class to draw the graph
 * @author Lan Chang
 *
 */

public class DrawingArea extends JPanel {
	private boolean allStudent;
	private boolean allAssignment;
	private String assignmentTypeChoosen;
	private ArrayList<Shape> shapes;
	private ArrayList<Integer> num;
	private ArrayList<Double> grade;
	private int m = 20;
	private double full = 100;
	private String graphChoosen = "pie";
	
	public DrawingArea(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		
		GradeGroup grade_group = new GradeGroup(allStudent, allAssignment, assignmentTypeChoosen);
		num = grade_group.getNum();
		grade = grade_group.getGrade();
	}
	
	public void paint(Graphics g) {
		// set the background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// draw the graph
		if (graphChoosen == "pie") {
			drawingPie();
		}
		if (graphChoosen == "bar") {
			drawingHistogram();
		}		
		for (Shape r : shapes) {
			r.paint(g);
		}
	}
	
	public void drawingHistogram() {
		shapes = new ArrayList<Shape>();
		
		double width = 1000 / m;
		
		// draw the axis
		shapes.add(new Line(150, 150, 150, 700));
		shapes.add(new Line(150, 700, 1350, 700));
		shapes.add(new Str(150, 100, "Number of Student"));
		shapes.add(new Str(1300, 750, "Grade"));
		for (int i = 0; i <= m; i++) {
			shapes.add(new Line(i * width + 200, 700, i * width + 200, 710));
			shapes.add(new Str(i * width + 200, 750, i * (full / m) + ""));
		}
		
		// get the max number of the grade group
		double num_max = 0;
		for (int i = 0; i < num.size(); i++) {
			if (num.get(i) > num_max) {
				num_max = num.get(i);
			}
		}
		
		// get the x coordinate of 25%, 50%, 75%
		Collections.sort(grade);		
		int x25 = (int) (grade.get((int) ((grade.size() + 1) * 0.25)) * 1000 / full + 200);
		int x50 = (int) (grade.get((int) ((grade.size() + 1) * 0.5)) * 1000 / full + 200);
		int x75 = (int) (grade.get((int) ((grade.size() + 1) * 0.75)) * 1000 / full + 200);
		
		// get the y coordinate and the max of y
		ArrayList<Double> y = new ArrayList<Double> ();
		double y_max = 0;
		for (int i = 0; i <= 1000; i++) {
			double y_temp = (1 / Math.sqrt(2 * Math.PI * variance(grade))) * Math.pow(Math.E, - (i / (1000 / full) - average(grade)) * (i / (1000 / full) - average(grade)) / (2 * variance(grade)));
			y.add(y_temp);
			if (y_temp > y_max) {
				y_max = y_temp;
			}
		}
		
		// draw the bar
		for (int i = 0; i < num.size(); i++) {
			int height = (int) (num.get(i) * 500 / (num_max * 2));
			if (height != 0) {
				shapes.add(new Str(200 + width * i, 700 - height, num.get(i) + ""));
				shapes.add(new Rect(200 + width * i + 1, 700 - height, width - 2, height));
			}
		}
		
		// draw the distribution
		for (int i = 200; i < 1200; i++) {
			double y1 = y.get(i - 200) / y_max * 500;
			double y2 = y.get(i - 199) / y_max * 500;
			shapes.add(new Line(i, 700 - y1, i + 1, 700 - y2));
		}
		
		// draw the 25%, 50%, 75%
		for (int i = 200; i <= 1200; i++) {
			if (i == x25 || i == x50 || i == x75) {
				shapes.add(new Line(i, 700 - y.get(i - 200) / y_max * 500, i, 700));
			}
		}
	}
	
	public void drawingPie() {
		shapes = new ArrayList<Shape>();
		
		double r = 600;
		int startX = (int) ((this.getSize().width/2) - (r/2));
		int startY = (int) ((this.getSize().height/2) - (r/2));
		
		// get the sum number of the grade group
		double sum = 0;
		for (int i = 0; i < num.size(); i++) {
			sum += num.get(i);	
		}
		
		// set the color
		Color[] color = {new Color(199, 237, 233), new Color(175, 215, 237), new Color(92, 167, 186), new Color(255, 66, 93), new Color(147, 224, 255)};
		
		// draw the pie
		double startAngle = 0;
		for (int i = 0, j = 0; i < num.size(); i++) {
			if (num.get(i) != 0) {
				double angle = num.get(i) / sum * 360;
				double midAngle = startAngle + angle / 2;
				double x1 = r / 1.8 * Math.cos(midAngle / 180 * Math.PI);
				double y1 = - r / 1.8 * Math.sin(midAngle / 180 * Math.PI);
				double x2 = r / 2 * Math.cos(midAngle / 180 * Math.PI);
				double y2 = - r / 2 * Math.sin(midAngle / 180 * Math.PI);
				double x3 = r / 1.6 * Math.cos(midAngle / 180 * Math.PI);
				double y3 = - r / 1.6 * Math.sin(midAngle / 180 * Math.PI);
				double dx = x1 - x2;
				double dy = y1 - y2;
				
				shapes.add(new Arc(startX + dx, startY + dy, r, r, startAngle, angle, color[j]));
				
				int pct = (int) (num.get(i) / sum * 10000);
				shapes.add(new Line(startX + r/2 + x1, startY + r/2 + y1, startX + r/2 + x3, startY + r/2 + y3));
				
				if (dx >= 0) {
					shapes.add(new Line(startX + r * 1.2, startY + r/2 + y3, startX + r/2 + x3, startY + r/2 + y3));
					shapes.add(new Str(startX + r * 1.2, startY + r/2 + y3, (double)pct / 100 + "%, [" + (i * (full / m)) + ", " + ((i + 1) * (full / m)) + "]"));
				}
				else {
					shapes.add(new Line(startX - r * 0.5, startY + r/2 + y3, startX + r/2 + x3, startY + r/2 + y3));
					shapes.add(new Str(startX - r * 0.5, startY + r/2 + y3, (double)pct / 100 + "%, [" + (i * (full / m)) + ", " + ((i + 1) * (full / m)) + "]"));
				}
				
				startAngle += angle;
				j = (j + 1) % 5;
			}
		}
	}
	
	public void clear() {
		shapes.clear();
		repaint();
	}
	
	public static double variance(ArrayList<Double> list) {
		double var = 0;
		for (int i = 0; i < list.size(); i++) {
			var += (list.get(i) - average(list)) * (list.get(i) - average(list));
		}
		return var / list.size();
	}
	
	public static double average(ArrayList<Double> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++){
		   sum = sum + list.get(i);
		}
		double average = sum / list.size();
		return average;
	}
}
