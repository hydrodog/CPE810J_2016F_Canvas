package edu.stevens.canvas.graph;

import java.awt.*;

public class Rect {
	private double x, y, width, height;
	
	public Rect(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)x + 1, (int)y + 1, (int)width - 2, (int)height - 2);
	}
}