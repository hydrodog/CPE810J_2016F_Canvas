package edu.stevens.canvas.graph;

import java.io.*;
import java.util.*;

/**
 * class to get the grade file
 * @author Lan Chang
 *
 */

public class GradeGroup {
	private static File[] fileList;
	private boolean allStudent, allAssignment;
	private String assignmentTypeChoosen, assignment;
	private static ArrayList<Double> grade;
	private static ArrayList<Integer> num;
	private int m;
	private double fullScore;
	private boolean done;
	
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String assignment) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.assignment = assignment;
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			done = true;
		}
	}
	
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String assignment, double fullScore, int m) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.assignment = assignment;
		this.fullScore = fullScore;
		this.m = m;
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			cal();
			done = true;
		}
	}
	
	public void getSingleGradeFile() {
		int index = -1;
		String filename = assignment + ".txt";
		
		for (int i = 0; i < fileList.length; i++) {
			if (filename.equals(fileList[i].getName())) {
				index = i;
			}
		}
		
		grade = new ArrayList<Double> ();
		
		try {
			Scanner fileInput = new Scanner(fileList[index]);
			while (fileInput.hasNext()) {
				String id_temp = fileInput.next();
				String grade_temp = fileInput.next();
				grade.add(Double.parseDouble(grade_temp));
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public void cal() {		
		num = new ArrayList<Integer> ();
		for (int i = 0; i < m; i++) {
			num.add(0);
		}
		for (int i = 0; i < grade.size(); i++) {
			if (grade.get(i) != fullScore) {
				num.set((int) (grade.get(i) / (fullScore / m)), num.get((int) (grade.get(i) / (fullScore / m))) + 1);
			}
			else {
				num.set(m - 1, num.get(m - 1) + 1);
			}
		}
	}
	
	public ArrayList<Integer> getNum() {
		return num;
	}
	
	public ArrayList<Double> getGrade() {
		return grade;
	}
	
	public boolean getDone() {
		return done;
	}
}