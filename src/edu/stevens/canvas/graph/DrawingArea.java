package edu.stevens.canvas.graph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

/**
 * class to draw the graph
 * @author Lan Chang
 *
 */

public class DrawingArea extends JPanel {
	private ArrayList<Shape> shapes;
	private ArrayList<Integer> num;
	private ArrayList<Double> grade;
	private int group;
	private double fullScore;
	private String graphTypeChoosen;
	private double width, height;
	private BufferedImage image;
	
	public DrawingArea(ArrayList<Integer> num, ArrayList<Double> grade, String graphTypeChoosen, double fullScore, int group) {
		this.num = num;
		this.grade = grade;
		this.graphTypeChoosen = graphTypeChoosen;
		this.fullScore = fullScore;
		this.group = group;
	}
	
	public void paint(Graphics g) {
		// get the size
		width = this.getWidth();
		height = this.getHeight();
		
		// set the background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int)width, (int)height);
		
		// draw the graph
		if (graphTypeChoosen == "pie") {
			drawingPie();
			for (Shape r : shapes) {
				r.paint(g);
			}
		}
		if (graphTypeChoosen == "bar") {
			drawingHistogram();
			for (Shape r : shapes) {
				r.paint(g);
			}
		}
	}
	
	public void drawingHistogram() {
		shapes = new ArrayList<Shape>();
		
		// draw the axis
		double bar_width = width * 0.8 / group;
		shapes.add(new Line(width * 0.05, height * 0.10, width * 0.05, height * 0.90));
		shapes.add(new Line(width * 0.05, height * 0.90, width * 0.90, height * 0.90));
		shapes.add(new Line(width * 0.05, height * 0.10, width * 0.90, height * 0.10));
		shapes.add(new Line(width * 0.90, height * 0.90, width * 0.90, height * 0.10));
		shapes.add(new Str(width * 0.05, height * 0.05, "Number of Student"));
		shapes.add(new Str(width * 0.92, height * 0.95, "Grade"));
		for (int i = 0; i <= group; i++) {
			shapes.add(new Line(width * 0.075 + bar_width * i, height * 0.9, width * 0.075 + bar_width * i, height * 0.91));
			shapes.add(new Str(width * 0.06 + bar_width * i, height * 0.95, i * (fullScore / group) + ""));
		}
		
		// get the max number of the grade group
		double num_max = 0;
		for (int i = 0; i < num.size(); i++) {
			if (num.get(i) > num_max) {
				num_max = num.get(i);
			}
		}
		
		// draw the bar
		for (int i = 0; i < num.size(); i++) {
			int h = (int) (num.get(i) * height * 0.80 / (num_max * 2));
			if (h != 0) {
				shapes.add(new Str(width * 0.075 + bar_width * i, height * 0.90 - h, num.get(i) + ""));
				shapes.add(new Rect(width * 0.075 + bar_width * i + 1, height * 0.90 - h, bar_width - 2, h));
			}
		}
		
		// draw the distribution
		double avg = average(grade);
		double var = variance(grade);
		double y_max = Gauss(avg / fullScore * width * 0.8, avg, var);
		for (int i = (int) (width * 0.075); i < (int) (width * 0.875); i++) {
			double y1_temp = Gauss(i - width * 0.075, avg, var);
			double y2_temp = Gauss(i + 1 - width * 0.075, avg, var);
			double y1 = y1_temp / y_max * height * 0.75;
			double y2 = y2_temp / y_max * height * 0.75;
			shapes.add(new Line(i, height * 0.90 - y1, i + 1, height * 0.90 - y2));
		}
		
		// draw the 25%, 50%, 75%
		Collections.sort(grade);		
		int x25 = (int) (grade.get((int) ((grade.size() + 1) * 0.25)) * width * 0.8 / fullScore + width * 0.075);
		int x50 = (int) (grade.get((int) ((grade.size() + 1) * 0.50)) * width * 0.8 / fullScore + width * 0.075);
		int x75 = (int) (grade.get((int) ((grade.size() + 1) * 0.75)) * width * 0.8 / fullScore + width * 0.075);
		for (int i = (int) (width * 0.075); i <= (int) (width * 0.875); i++) {
			if (i == x25 || i == x50 || i == x75) {
				double y_temp = Gauss(i - width * 0.075, avg, var);
				shapes.add(new Line(i, height * 0.90 - y_temp / y_max * height * 0.75, i, height * 0.90));
			}
		}
		
		image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int)width, (int)height);
		for (Shape r1 : shapes) {
			r1.paint(g);
		}
	}
	
	public void drawingPie() {
		shapes = new ArrayList<Shape>();
		
		// get the radius and center point
		double r = Math.min(width, height) / 2;
		double startX = width / 3 - r / 2;
		double startY = height / 2 - r / 2;
		
		// get the sum number of the grade group
		double sum = 0;
		for (int i = 0; i < num.size(); i++) {
			sum += num.get(i);	
		}
		
		// set the color
		Color[] color = {new Color(199, 237, 233), new Color(175, 215, 237), new Color(92, 167, 186), new Color(255, 66, 93), new Color(147, 224, 255)};
		
		// draw the pie
		double startAngle = 90;
		int j = 0;
		for (int i = 0; i < num.size(); i++) {
			if (num.get(i) != 0) {
				double angle = num.get(i) * 360.0 / sum;
				double midAngle = startAngle + angle / 2;
				double rcos = r * Math.cos(midAngle / 180 * Math.PI);
				double rsin = r * Math.sin(midAngle / 180 * Math.PI);
				double x = rcos / 1.5;
				double y = -rsin / 1.5;
				double dx = rcos / 1.8 - rcos / 2;
				double dy = rsin / 2 - rsin / 1.8;
				
				shapes.add(new Arc(startX + dx, startY + dy, r, r, startAngle, angle, color[j % 5]));
				shapes.add(new Str(startX + r/2 + x, startY + r/2 + y, (j + 1) + ""));
				
				startAngle += angle;
				j++;
			}
		}
		
		String s = "): ";
		for (int i = 0, k = 0; i < num.size(); i++) {
			if (num.get(i) != 0) {
				int pct = (int) (num.get(i) * 10000 / sum);
				if (k + 1 == j) {
					s = "]: ";
				}
				shapes.add(new Str(width * 0.7, height / (j + 1) * (k + 1), "[" + (k + 1) + "] [" + i * fullScore / group + ", " +  (i + 1) * fullScore / group + s + (double)pct / 100 + "%"));
				k++;
			}
		}
		
		image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int)width, (int)height);
		for (Shape r2 : shapes) {
			r2.paint(g);
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
	
	public double Gauss(double x, double avg, double var) {
		return (1 / Math.sqrt(2 * Math.PI * var)) * Math.pow(Math.E, - (x / (width * 0.8 / fullScore) - avg) * (x / (width * 0.8 / fullScore) - avg) / (2 * var));
	}
	
	public void saveImage(String savePath) {
		try {
			ImageIO.write(image, "PNG", new File(savePath + ".png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}