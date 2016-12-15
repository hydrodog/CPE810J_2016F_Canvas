package edu.stevens.canvas.graph;

import java.awt.*;

/**
 * class to draw the line
 * @author Lan Chang
 *
 */

public class Line extends Shape {
	private double x2, y2;
	
	public Line(double x, double y, double x2, double y2) {
		super(x, y);
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) x, (int) y, (int) x2, (int) y2);
	}
}