/* 
 * To post grade to canvas
 * Informations you should provide:
 *	courseId 
 *	assignmentId;
 *	userId;
 *	token;
 *	targetURL;
 *	grade;
 *  call UpdateGrade.post() to send the grade
 *  
 */
import java.io.*;
import java.io.IOException;
import java.net.*;

public class UpdateGrade {
	private String courseId;
    private String assignmentId;
    private String userId;
    private String token;
    private static String targetURL;
    private double grade;
    public UpdateGrade(String courseId,String assignmentId,String studentId,String token,double grade){
    	this.courseId=courseId;
    	this.assignmentId=assignmentId;
    	this.userId=studentId;
    	this.token=token;
    	this.grade=grade;
    	UpdateGrade.targetURL = "https://canvas.instructure.com/api/v1"
    			+ "/courses/"+this.courseId
    			+ "/assignments/"+this.assignmentId
    			+ "/submissions/update_grades"
    			+ "?access_token="
    			+ this.token
    			+ "&grade_data["+this.userId+"]"+"[posted_grade]="+this.grade
    			+ "&grade_data["+this.userId+"]"+"[text_comment]=Testing!";
    	
    }
    public void post(){
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
        public void printurl(){
        	System.out.println(targetURL);
        }
       
       public static void main(String[] args){//a test example
    	    String courseId="10300000000000133";
    	    String assignmentId="10300000000046026";
    	    String userId="10300000000022347";
    	    String token="change token here!";
    	    UpdateGrade test = new UpdateGrade(courseId,assignmentId,userId,token,9);
    	    test.printurl();
    	    test.post();
    	    
       }
  
    	
    }
	

