/**
	 @author: Binglin Xie, Junkai Cui
	 @modified: Yifei Yao
	 
*/

/* 
 * To post grade to canvas
 * Informations you should provide:
 *	courseId 
 *	assignmentId;
 *	studentId;
 *	token;
 *	grade;
 *  call Grade.updateGrade() to update the grade
 *  to do: 
 *         finish comment method;
 *         merge with download team and add download method.
 *         The test result can be found when you open DOV DEV - Java Team Testing - Test Student
           TEST RESULT URL: https://sit.instructure.com/courses/133/gradebook/speed_grader?assignment_id=46026#%7B%22student_id%22%3A%2222347%22%7D
 */
package update;

import java.io.*;
import java.net.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import update.SQLhelper;

public class Grade {// The information we need to identify a course and a student
	private String assignmentId;
    private String courseId;
    private String studentId;
    private static String targetURL;
    private String token;
    private double grade;
        // Methods used to get and set the required information
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public static String getTargetURL() {
		return targetURL;
	}
	public static void setTargetURL(String targetURL) {
		Grade.targetURL = targetURL;
	}
	public Grade(String courseId,String assignmentId,String studentId,String token,double grade) {
		this.grade = grade;
		this.courseId=courseId;
    	this.assignmentId=assignmentId;
    	this.studentId=studentId;
    	this.token=token;
    	this.grade=grade;
	}
	// Upload the grade to Canvas
	public void updateGrade(){
    	Grade.targetURL = "https://canvas.instructure.com/api/v1"
    			+ "/courses/"+this.courseId
    			+ "/assignments/"+this.assignmentId
    			+ "/submissions/update_grades"
    			+ "?access_token="
    			+ this.token
    			+ "&grade_data["+this.studentId+"]"+"[posted_grade]="+this.grade
    			+ "&grade_data["+this.studentId+"]"+"[text_comment]=Testing!";
    	// How to realize the function
	try {

			URL targetUrl = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ httpConnection.getResponseCode());
			}

			BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));

			String output;
			System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
			}

			httpConnection.disconnect();
			//return "success!";

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }

		}
	//Testing upload function
	public static void main(String[] args){
		
		String token = "1030~CcjXAdiqvyaYOiEsqfBiIFGZx3hN7T6s2wO5dPVYNU3OYvdKNfV6b7KUP0t13G7F";
		SQLhelper sqlHelper = new SQLhelper();
		Connection conn = sqlHelper.getSQL();
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select * from grade");
			while (result.next()) {
				//System.out.println(result.getString("lastname"));
				String courseId = result.getString("courseId");
				String assignmentId = result.getString("assignmentId");
				String userId =result.getString("userId");
				double grade = result.getDouble("grade");
				Grade test = new Grade(courseId, assignmentId, userId, token, grade);
				test.updateGrade();
				//System.out.println(courseId+ assignmentId + userId + grade);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	    
   }
    	
    }
	
	


