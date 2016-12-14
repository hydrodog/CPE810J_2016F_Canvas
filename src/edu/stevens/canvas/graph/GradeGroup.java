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
	private static HashMap<String, Double> multiGrade;
	private int group;
	private double fullScore, width, fullScore_temp;
	private boolean done;
	private int p1, p2, p3, p4;
	
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String assignment, int[] percent) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.assignment = assignment;
		p1 = percent[0];
		p2 = percent[1];
		p3 = percent[2];
		p4 = percent[3];
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			done = true;
		}
		
		if (this.allStudent == true && this.allAssignment == true && !assignmentTypeChoosen.equals("All")) {
			getMultiGradeFile();
			done = true;
		}
		
		if (this.allStudent == true && this.allAssignment == true && assignmentTypeChoosen.equals("All")) {
			getAllGradeFile();
			done = true;
		}
	}
	
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String assignment, double width, int[] percent) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.assignment = assignment;
		this.width = width;
		p1 = percent[0];
		p2 = percent[1];
		p3 = percent[2];
		p4 = percent[3];
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			cal();
			done = true;
		}
		
		if (this.allStudent == true && this.allAssignment == true && !assignmentTypeChoosen.equals("All")) {
			getMultiGradeFile();
			cal();
			done = true;
		}
		
		if (this.allStudent == true && this.allAssignment == true && assignmentTypeChoosen.equals("All")) {
			getAllGradeFile();
			cal();
			done = true;
		}
	}
	
	public void getAllGradeFile() {
		assignmentTypeChoosen = "Assignment";
		getMultiGradeFile();
		HashMap<String, Double> gradeAssignment = multiGrade;
		double fullAssignment = fullScore_temp;
		
		assignmentTypeChoosen = "Quiz";
		getMultiGradeFile();
		HashMap<String, Double> gradeQuiz = multiGrade;
		double fullQuiz = fullScore_temp;
		
		assignmentTypeChoosen = "Test";
		getMultiGradeFile();
		HashMap<String, Double> gradeTest = multiGrade;
		double fullTest = fullScore_temp;
		
		assignmentTypeChoosen = "Project";
		getMultiGradeFile();
		HashMap<String, Double> gradeProject = multiGrade;
		double fullProject = fullScore_temp;
		
		HashMap<String, Double> gradeAll = new HashMap<String, Double> ();
		
		for (HashMap.Entry<String, Double> entry : gradeAssignment.entrySet()) {
			gradeAll.put(entry.getKey(), entry.getValue() / fullAssignment * p1);
		}
		
		for (HashMap.Entry<String, Double> entry : gradeQuiz.entrySet()) {
			if (!gradeAll.containsKey(entry.getKey())) {
				gradeAll.put(entry.getKey(), entry.getValue() / fullQuiz * p2);
			}
			else {
				gradeAll.put(entry.getKey(), gradeAll.get(entry.getKey()) + entry.getValue() / fullQuiz * p2);
			}
		}
		
		for (HashMap.Entry<String, Double> entry : gradeTest.entrySet()) {
			if (!gradeAll.containsKey(entry.getKey())) {
				gradeAll.put(entry.getKey(), entry.getValue() / fullTest * p3);
			}
			else {
				gradeAll.put(entry.getKey(), gradeAll.get(entry.getKey()) + entry.getValue() / fullTest * p3);
			}
		}
		
		for (HashMap.Entry<String, Double> entry : gradeProject.entrySet()) {
			if (!gradeAll.containsKey(entry.getKey())) {
				gradeAll.put(entry.getKey(), entry.getValue() / fullProject * p4);
			}
			else {
				gradeAll.put(entry.getKey(), gradeAll.get(entry.getKey()) + entry.getValue() / fullProject * p4);
			}
		}
		
		grade = new ArrayList<Double> ();
		
		for (HashMap.Entry<String, Double> entry : gradeAll.entrySet()) {
			grade.add(entry.getValue());
		}
		
		fullScore = 100;
		
		group = (int) (100 / width);
	}
	
	public void getMultiGradeFile() {
		String assignmentRegex = "[Aa]ssignment[0-9]*[.txt]*";
		String projectRegex = "[Pp]roject[0-9]*[.txt]*";
		String testRegex = "[Tt]est[0-9]*[.txt]*";
		String quizRegex = "[Qq]uiz[0-9]*[.txt]*";
		
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].getName().matches(assignmentRegex) && assignmentTypeChoosen.matches(assignmentRegex)) {
				index.add(i);
			}
			if (fileList[i].getName().matches(projectRegex) && assignmentTypeChoosen.matches(projectRegex)) {
				index.add(i);
			}
			if (fileList[i].getName().matches(testRegex) && assignmentTypeChoosen.matches(testRegex)) {
				index.add(i);
			}
			if (fileList[i].getName().matches(quizRegex) && assignmentTypeChoosen.matches(quizRegex)) {
				index.add(i);
			}
		}
		
		fullScore_temp = 0;
		
		multiGrade = new HashMap<String, Double> ();
		
		for (int i = 0; i < index.size(); i++) {
			Scanner fileInput;
			try {
				fileInput = new Scanner(fileList[index.get(i)]);
				
				fullScore_temp += Double.parseDouble(fileInput.next());
				
				while (fileInput.hasNext()) {
					String id_temp = fileInput.next();
					double grade_temp = Double.parseDouble(fileInput.next());
					if (!multiGrade.containsKey(id_temp)) {
						multiGrade.put(id_temp, grade_temp);
					}
					else {
						double grade_sum = multiGrade.get(id_temp);
						multiGrade.put(id_temp, grade_sum + grade_temp);
					}
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		grade = new ArrayList<Double> ();
		
		for (HashMap.Entry<String, Double> entry : multiGrade.entrySet()) {
			grade.add(entry.getValue() / fullScore_temp * 100);
		}
		
		fullScore = 100;
		
		group = (int) (100 / width);
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
		
		Scanner fileInput;
		try {
			fileInput = new Scanner(fileList[index]);
			fullScore = Double.parseDouble(fileInput.next());
			group = (int) (fullScore / width);
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
		
		for (int i = 0; i < group; i++) {
			num.add(0);
		}
		for (int i = 0; i < grade.size(); i++) {
			if (grade.get(i) != fullScore) {
				num.set((int) (grade.get(i) / width), num.get((int) (grade.get(i) / width)) + 1);
			}
			else {
				num.set(group - 1, num.get(group - 1) + 1);
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
	
	public double getFull() {
		return fullScore;
	}
}