package edu.stevens.canvas.zip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

class ruleGUI extends JFrame {
	private JLabel stuID, stuName, stuEmail, hwName, title, musthave, mustnot;
	private JTextField must_t, mustnot_t;
	private JTextArea process;
	private JButton ok, reset;
	private JTextArea t;
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
	public ruleGUI(String s) throws Exception {
		super(s);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		title = new JLabel("ZIP FILE RULES", SwingConstants.CENTER);
		stuID = new JLabel("Student CWID: " + "10404898", SwingConstants.CENTER);
		stuName = new JLabel("Student Name: " + "Shenwei Chen", SwingConstants.CENTER);
		stuEmail = new JLabel("Student Email: " + "schen31@stevens.edu", SwingConstants.CENTER);
		hwName = new JLabel("Homework: " + "example homework", SwingConstants.CENTER);
		musthave = new JLabel("Must have: ");
		mustnot = new JLabel("Must not have: ");
		must_t = new JTextField();
		mustnot_t = new JTextField();
		process = new JTextArea();
		ok = new JButton("OK");
		reset = new JButton("RESET");
		JPanel pane1 = new JPanel(new GridBagLayout());
		JPanel pane2 = new JPanel(new GridBagLayout());
		JPanel pane3 = new JPanel(new GridBagLayout());
		JPanel pane4 = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			//natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		pane1.add(title, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		pane2.add(stuID, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		pane2.add(stuName, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		pane2.add(stuEmail, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		
		c.ipady = 20;      //make this component tall
		c.gridx = 0;
		c.gridy = 1;
		pane1.add(pane2, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		pane3.add(musthave, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 0;
		pane3.add(must_t, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane3.add(mustnot, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 1;
		pane3.add(mustnot_t, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.ipady = 40;      //make this component tall
		c.gridx = 0;
		c.gridy = 2;
		pane1.add(pane3, c);
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 10;
		pane4.add(ok, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		pane4.add(reset, c);
		
		//c.gridwidth = 3;
		//c.ipady = 40;      //make this component tall
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.ipady = 10;
		pane1.add(pane4, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.ipady = 150;
		pane1.add(process, c);
		
		
		
		getContentPane().add(pane1);
		setSize(600,400);
		setLocationRelativeTo(null); 
		setVisible(true);
	}
}

