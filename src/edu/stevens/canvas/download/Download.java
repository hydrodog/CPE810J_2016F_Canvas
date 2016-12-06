/**
 *  @author: Yang Zhang. Gaojian Chen. Siyu Sun  
 * 
 *  
 */
package edu.stevens.canvas.download;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Using courseId,assignmentId,userId,token to
 * get access to a student grade URL in Canvas 
 * 
 *
 */
public class Download extends Canvas{
    private static String targetURL;
    private  static double grade;
    private static String output;
    public Download(String courseId,String assignmentId,String userId,String token){
    	super(courseId,assignmentId,userId,token);
    	Download.targetURL = "https://canvas.instructure.com/api/v1"
    			+ "/courses/"+super.getcouseId()
    			+ "/assignments/"+super.getassignmentId()
    			+ "/submissions/"+super.getuserId()
    			+ "?access_token="
    			+ super.gettoken();
    	
    }
   /**
    *Using Canvas API to get the grade information 
    *After getting the output(contain id, grade, time, etc.)
    *Using Regex to get the grade and assignments id
    * Other group can using getGrade() to get grade information
    */
    public void get(){
    	try {

			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ httpConnection.getResponseCode());
			}

			BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));
			
			System.out.println("Output from Server:");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
	    	 	String s=null,s1=null;
	   	   		String regex = "(\"id\":+?)(\\d+)";
	   	   		String regex1 ="(\"grade\":\"+?)(\\d+)(\")";
	   	   		Pattern p2=Pattern.compile(regex);
	  			Matcher m2=p2.matcher(output);	   	   		
	  			while(m2.find()){
	  				s=m2.group(2);	  				  				
	  			}
	  			Pattern p3=Pattern.compile(regex1);
	  			Matcher m3=p3.matcher(output);
	  			while(m3.find()){
	  				s1=m3.group(2);	  				  				
	  			}
	  			grade = Double.parseDouble(s1);
	  			System.out.println("User:"+super.getuserId()+" grade:"+grade);
	  			
				
			}

			httpConnection.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }
		}
    
        public void printurl(){
        	System.out.println(targetURL);
        }
        
       public static double getGrade(){
    	   return grade;
       }
}
