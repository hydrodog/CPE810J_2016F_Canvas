/* 
 * To post grade to canvas
 * Informations you should provide:
 *	courseId 
 *	assignmentId;
 *	studentId;
 *	token;
 *	grade;
 *  call Grade.updateGrade() to update the grade
 *  to do: support more type of grade, for example : ABC , 1-5, 1-100....;
 *         finish comment method;
 *  
 */
package update;

import java.io.*;
import java.net.*;
import java.net.URL;

public class Grade {
	private String assignmentId;
    private String courseId;
    private String studentId;
    private static String targetURL;
    private String token;
    private double grade;
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
	public void updateGrade(){
    	Grade.targetURL = "https://canvas.instructure.com/api/v1"
    			+ "/courses/"+this.courseId
    			+ "/assignments/"+this.assignmentId
    			+ "/submissions/update_grades"
    			+ "?access_token="
    			+ this.token
    			+ "&grade_data["+this.studentId+"[posted_grade]="+this.grade
    			+ "&grade_data["+this.studentId+"]"+"[text_comment]=hello!";
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
	public static void main(String[] args){//a test example
	    String courseId="10300000000000133";
	    String assignmentId="10300000000046026";
	    String userId="10300000000022347";
	    String token="1030~BxcmBG0xRZw720NNodMrxAH9FIA7HlH9T48yi0I5SDLqzJc4tmoMsWiSS9tMx36S";
	    Grade test = new Grade(courseId,assignmentId,userId,token,6);
	    //test.printurl();
	    test.updateGrade();
	    
   }
    	
    }
	
	


