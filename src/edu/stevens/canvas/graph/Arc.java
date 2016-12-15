package edu.stevens.canvas.graph;

import java.awt.*;

/**
 * class to draw the arc
 * @author Lan Chang
 *
 */

public class Arc extends Shape {
	private double width, height, stareAngle, angle;
	private Color color;
	
	public Arc(double x, double y, double width, double height, double stareAnglee, double angle, Color color) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.stareAngle = stareAnglee;
		this.angle = angle;
		this.color = color;
	}
	
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillArc((int) x, (int) y, (int) width, (int) height, (int) stareAngle, (int) angle);
	}
}