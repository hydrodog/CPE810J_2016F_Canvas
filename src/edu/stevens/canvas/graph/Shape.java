package edu.stevens.canvas.graph;

import java.awt.*;
import java.awt.event.MouseAdapter;

abstract class Shape {
	protected double x,y;
	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void paint(Graphics g);

	public void addMouseMotionListener(MouseAdapter mouseAdapter) {	
	}

	public void setToolTipText(String string) {
	}
}