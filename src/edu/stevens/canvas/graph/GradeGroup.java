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
	private boolean allStudent;
	private boolean allAssignment;
	private static String assignmentTypeChoosen;
	private static ArrayList<Double> grade;
	private static ArrayList<Integer> num;
	private int m;
	private double fullScore;
	
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, double fullScore, int m) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.m = m;
		this.fullScore = fullScore;
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		getGradeFile();
		cal();
	}
	
	public void getGradeFile() {
		int index = -1;
		String filename = assignmentTypeChoosen + ".txt";
		
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
}
