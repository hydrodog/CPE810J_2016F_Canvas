package edu.stevens.canvas.graph;

import java.awt.*;

/**
 * abstract class to draw the shape
 * @author Lan Chang
 *
 */

abstract class Shape {
	protected double x, y;
	
	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void paint(Graphics g);
}