import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

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
	
	public DrawingArea(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawingPie();
		
		for (Shape r : shapes) {
			r.paint(g);
		}
	}
	
	public void drawingHistogram() {
		shapes = new ArrayList<Shape>();
		shapes.add(new Line(150, 100, 150, 700));
		shapes.add(new Line(150, 700, 1350, 700));
		shapes.add(new Str(100, 80, "Number of Student"));
		shapes.add(new Str(1300, 750, "Grade"));
		
		for (int i = 0; i < 11; i++) {
			shapes.add(new Line(i * 100 + 200, 700, i * 100 + 200, 710));
			shapes.add(new Str(i * 100 + 200, 750, i * 10 + ""));
		}
		
		int startX = 200;
		int startY = 700;
		int w = 50;
		
		GradeGroup grade_group = new GradeGroup(allStudent, allAssignment, assignmentTypeChoosen);
		grade_group.cal();
		ArrayList<Integer> num = grade_group.getNum();
		
		for (int i = 0; i < num.size(); i++) {
			int n = num.get(i);
			int height = n * 40;
			if (n != 0) {
				shapes.add(new Str(startX + w * i, startY - height, n + ""));
				shapes.add(new Rect(startX + w * i + 1, startY - height, w - 2, height));
			}
		}
	}
	
	public void drawingPie() {
		shapes = new ArrayList<Shape>();
		int r = 600;
		int startX = (this.getSize().width/2) - (r/2);
		int startY = (this.getSize().height/2) - (r/2);
		
		GradeGroup grade_group = new GradeGroup(allStudent, allAssignment, assignmentTypeChoosen);
		grade_group.cal();
		ArrayList<Integer> num = grade_group.getNum();
		
		double sum_n = 0;
		for (int i = 0; i < num.size(); i++) {
			sum_n += num.get(i);	
		}
		
		double startAngle = 0;
		Color[] color = {
				new Color(254, 67, 101),
				new Color(252, 157, 154),
				new Color(249, 205, 173),
				new Color(200, 200, 169),
				new Color(131, 175, 155)
			};
		
		for (int i = 0, j = 0; i < num.size(); i++) {
			if (num.get(i) != 0) {
				
				double angle = num.get(i) / sum_n * 360;
				double midAngle = startAngle + angle / 2;
				double x1 = r/1.8 * Math.cos(midAngle / 180 * Math.PI);
				double y1 = - r/1.8 * Math.sin(midAngle / 180 * Math.PI);
				double x2 = r/2 * Math.cos(midAngle / 180 * Math.PI);
				double y2 = - r/2 * Math.sin(midAngle / 180 * Math.PI);
				double x3 = r/1.6 * Math.cos(midAngle / 180 * Math.PI);
				double y3 = - r/1.6 * Math.sin(midAngle / 180 * Math.PI);
				double dx = x1 - x2;
				double dy = y1 - y2;
				
				shapes.add(new Arc(startX + dx, startY + dy, r, r, startAngle, angle, color[j]));
				
				int pct = (int) (num.get(i) / sum_n * 10000);
				shapes.add(new Line(startX + r/2 + x1, startY + r/2 + y1, startX + r/2 + x3, startY + r/2 + y3));
				
				if (dx >= 0) {
					shapes.add(new Line(startX + r * 1.2, startY + r/2 + y3, startX + r/2 + x3, startY + r/2 + y3));
					shapes.add(new Str(startX + r * 1.2, startY + r/2 + y3, (double)pct / 100 + "%, [" + (i * 5) + ", " + ((i + 1) * 5) + "]"));
				}
				else {
					shapes.add(new Line(startX - r * 0.5, startY + r/2 + y3, startX + r/2 + x3, startY + r/2 + y3));
					shapes.add(new Str(startX - r * 0.5, startY + r/2 + y3, (double)pct / 100 + "%, [" + (i * 5) + ", " + ((i + 1) * 5) + "]"));
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
}
