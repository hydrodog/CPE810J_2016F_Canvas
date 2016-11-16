import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * class to display the drawing GUI
 * @author Lan Chang
 *
 */

public class Drawing_GUI extends JFrame {
	private boolean allStudent;
	private boolean allAssignment;
	private String assignmentTypeChoosen;
	
	public Drawing_GUI(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		
		this.setTitle("Drawing GUI");
		this.setSize(1500, 1000);
		this.setResizable(false);
		Container c = getContentPane();
		
		JLabel title = new JLabel("Graph");
		title.setFont(new Font("Helvetica", Font.BOLD, 50));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(title, BorderLayout.NORTH);
		
		DrawingArea d = new DrawingArea(allStudent, allAssignment, assignmentTypeChoosen);
		c.add(d, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Drawing_GUI(true, false, "assignment2");
	}
}
