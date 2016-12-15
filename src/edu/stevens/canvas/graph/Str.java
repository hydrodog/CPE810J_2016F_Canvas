package edu.stevens.canvas.graph;

import java.awt.*;

/**
 * class to draw the String
 * @author Lan Chang
 *
 */

public class Str extends Shape {
	private String str;
	
	public Str(double x, double y, String str) {
		super(x, y);
		this.str = str;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.PLAIN, 25));
		g.drawString(str, (int) x, (int) y);
	}
}