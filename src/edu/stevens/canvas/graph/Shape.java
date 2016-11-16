import java.awt.*;

abstract class Shape {
	protected double x,y;
	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void paint(Graphics g);
}