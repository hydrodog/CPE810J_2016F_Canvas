package edu.stevens.canvas.graph;

import java.io.*;
import java.util.*;

/**
 * class to get the grade file and number file
 * @author Lan Chang
 *
 */

public class GradeGroup {
	private static File[] fileList;
	private static ArrayList<Double> grade;
	private static ArrayList<Integer> num;
	private String assignmentTypeChoosen;
	private String assignment;
	private String student;
	private StringBuilder sb;
	private int group, p1, p2, p3, p4;
	private double fullScore, width, fullScore_temp, fullAssignment, fullQuiz, fullTest, fullProject;
	private boolean allStudent, allAssignment, done;
	private HashMap<String, Double> multiGrade, gradeAll, gradeAssignment, gradeQuiz, gradeTest, gradeProject;
	
	// constructor for calculate
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String assignment, String student, int[] percent) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.assignment = assignment;
		this.student = student;
		p1 = percent[0];
		p2 = percent[1];
		p3 = percent[2];
		p4 = percent[3];
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		// all student one assignment
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			done = true;
		}
		
		// all student category assignment
		if (this.allStudent == true && this.allAssignment == true && !assignmentTypeChoosen.equals("All")) {
			getMultiGradeFile();
			done = true;
		}
		
		// all student all assignment
		if (this.allStudent == true && this.allAssignment == true && assignmentTypeChoosen.equals("All")) {
			getAllGradeFile();
			done = true;
		}
		
		// single student all assignment
		if (this.allStudent == false && this.allAssignment == true && assignmentTypeChoosen.equals("All") && !this.student.equals("")) {
			getStudentFile();
			done = true;
		}
	}
	
	// constructor for draw
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
		
		// all student one assignment
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			cal();
			done = true;
		}
		
		// all student category assignment
		if (this.allStudent == true && this.allAssignment == true && !assignmentTypeChoosen.equals("All")) {
			getMultiGradeFile();
			cal();
			done = true;
		}
		
		// all student all assignment
		if (this.allStudent == true && this.allAssignment == true && assignmentTypeChoosen.equals("All")) {
			getAllGradeFile();
			cal();
			done = true;
		}
	}
	
	// get the single student grade file
	public void getStudentFile() {
		getAllGradeFile();
		
		sb = new StringBuilder("");
		
		sb.append("Grade: " + "\r\n" + "\r\n");
		sb.append("Student: " + student + "\r\n" + "\r\n");
		sb.append("Assignment: " + gradeAssignment.get(student) / fullAssignment * 100 + "\r\n" + "\r\n");
		sb.append("Quiz: " + gradeQuiz.get(student) / fullQuiz * 100 + "\r\n" + "\r\n");
		sb.append("Test: " + gradeTest.get(student) / fullTest * 100 + "\r\n" + "\r\n");
		sb.append("Project: " + gradeProject.get(student) / fullProject * 100 + "\r\n" + "\r\n");
		sb.append("All: " + gradeAll.get(student) + "\r\n" + "\r\n");
	}
	
	// get the all student all assignment grade file
	public void getAllGradeFile() {
		// get assignment grade file
		assignmentTypeChoosen = "Assignment";
		getMultiGradeFile();
		gradeAssignment = multiGrade;
		fullAssignment = fullScore_temp;
		
		// get quiz grade file
		assignmentTypeChoosen = "Quiz";
		getMultiGradeFile();
		gradeQuiz = multiGrade;
		fullQuiz = fullScore_temp;
		
		// get test grade file
		assignmentTypeChoosen = "Test";
		getMultiGradeFile();
		gradeTest = multiGrade;
		fullTest = fullScore_temp;
		
		// get project grade file
		assignmentTypeChoosen = "Project";
		getMultiGradeFile();
		gradeProject = multiGrade;
		fullProject = fullScore_temp;
		
		// store all assignment grade file with ID
		gradeAll = new HashMap<String, Double> ();
		
		// assignment part
		for (HashMap.Entry<String, Double> entry : gradeAssignment.entrySet()) {
			gradeAll.put(entry.getKey(), entry.getValue() / fullAssignment * p1);
		}
		
		// quiz part
		for (HashMap.Entry<String, Double> entry : gradeQuiz.entrySet()) {
			if (!gradeAll.containsKey(entry.getKey())) {
				gradeAll.put(entry.getKey(), entry.getValue() / fullQuiz * p2);
			}
			else {
				gradeAll.put(entry.getKey(), gradeAll.get(entry.getKey()) + entry.getValue() / fullQuiz * p2);
			}
		}
		
		// test part
		for (HashMap.Entry<String, Double> entry : gradeTest.entrySet()) {
			if (!gradeAll.containsKey(entry.getKey())) {
				gradeAll.put(entry.getKey(), entry.getValue() / fullTest * p3);
			}
			else {
				gradeAll.put(entry.getKey(), gradeAll.get(entry.getKey()) + entry.getValue() / fullTest * p3);
			}
		}
		
		// project part
		for (HashMap.Entry<String, Double> entry : gradeProject.entrySet()) {
			if (!gradeAll.containsKey(entry.getKey())) {
				gradeAll.put(entry.getKey(), entry.getValue() / fullProject * p4);
			}
			else {
				gradeAll.put(entry.getKey(), gradeAll.get(entry.getKey()) + entry.getValue() / fullProject * p4);
			}
		}
		
		// store all assignment grade file without ID
		grade = new ArrayList<Double> ();
		
		for (HashMap.Entry<String, Double> entry : gradeAll.entrySet()) {
			grade.add(entry.getValue());
		}
		
		fullScore = 100;
		
		group = (int) (100 / width);
	}
	
	// get the all student category assignment grade file
	public void getMultiGradeFile() {
		// regex for file name
		String assignmentRegex = "[Aa]ssignment[0-9]*[.txt]*";
		String projectRegex = "[Pp]roject[0-9]*[.txt]*";
		String testRegex = "[Tt]est[0-9]*[.txt]*";
		String quizRegex = "[Qq]uiz[0-9]*[.txt]*";
		
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		// find the index of assignment we want
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
		
		// store category assignment grade file with ID
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
		
		// store category assignment grade file without ID
		grade = new ArrayList<Double> ();
		
		for (HashMap.Entry<String, Double> entry : multiGrade.entrySet()) {
			grade.add(entry.getValue() / fullScore_temp * 100);
		}
		
		fullScore = 100;
		
		group = (int) (100 / width);
	}
	
	// get the all student one assignment grade file
	public void getSingleGradeFile() {
		int index = -1;
		String filename = assignment + ".txt";
		
		// get the file index
		for (int i = 0; i < fileList.length; i++) {
			if (filename.equals(fileList[i].getName())) {
				index = i;
			}
		}
		
		// store one assignment grade file without ID
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
	
	// calculate the number of student in each grade group
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
	
	// return the number of student in each group array list
	public ArrayList<Integer> getNum() {
		return num;
	}
	
	// return the grade array list
	public ArrayList<Double> getGrade() {
		return grade;
	}
	
	// return if it is done
	public boolean getDone() {
		return done;
	}
	
	// return the full score
	public double getFull() {
		return fullScore;
	}
	
	// return the single student's grade
	public StringBuilder getStr() {
		return sb;
	}
}