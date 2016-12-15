package edu.stevens.canvas.zip;

import java.io.*;
import java.util.*;

public class saveStudent {
	
	public static void main(String[] args) {/*
		List<PassStudent> list = new ArrayList<PassStudent>();
		PassStudent stu1 = new PassStudent("stu1", "stu1/src/main.java", "Yu-Dong Yao", 10000000);
		PassStudent stu2 = new PassStudent("stu2", "stu2/src/main.java", "Dov Kruger", 10000001);
		PassStudent stu3 = new PassStudent("stu3", "stu3/src/main.java", "Hong Man", 10000002);
		list.add(stu1);
		list.add(stu2);
		list.add(stu3);
		String root = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/Final Project/ZIPRULE";
		try {
			FileOutputStream fos = new FileOutputStream(root + "/students.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PassStudent> newlist = new ArrayList<PassStudent>();
		try {
			FileInputStream fis = new FileInputStream(root + "/students.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			newlist = (ArrayList<PassStudent>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(newlist.get(2).CWID);*/
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		StudentInfo stu1 = new StudentInfo("Student1", 10000001, "schen31@stevens.edu", "stu1");
		StudentInfo stu2 = new StudentInfo("Student2", 10000002, "schen31@stevens.edu", "stu2");
		StudentInfo stu3 = new StudentInfo("Student3", 10000003, "schen31@stevens.edu", "stu3");
		StudentInfo stu4 = new StudentInfo("Student4", 10000004, "schen31@stevens.edu", "stu4");
		StudentInfo stu5 = new StudentInfo("Student5", 10000005, "schen31@stevens.edu", "stu5");
		StudentInfo stu6 = new StudentInfo("Student6", 10000006, "schen31@stevens.edu", "stu6");
		StudentInfo stu7 = new StudentInfo("Student7", 10000007, "schen31@stevens.edu", "stu7");
		list.add(stu1);
		list.add(stu2);
		list.add(stu3);
		list.add(stu4);
		list.add(stu5);
		list.add(stu6);
		list.add(stu7);
		String root = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/Final Project/ZIPRULE/src/Download";
		try {
			FileOutputStream fos = new FileOutputStream(root + "/studentInfo.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}