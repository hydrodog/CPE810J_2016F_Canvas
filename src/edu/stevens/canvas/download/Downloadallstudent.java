/**
 *  @author: Yang Zhang. Gaojian Chen  
 * 
 *  
 */
package edu.stevens.canvas.download;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Downloadallstudent {
	
		private String courseId;
	    private String token;
	    private static String targetURL;
	    private double grade;
	    public Downloadallstudent(String courseId,String token){
	    	this.courseId=courseId;
	    	this.token=token;
	    	
	    	Downloadallstudent.targetURL = "https://canvas.instructure.com/api/v1"
	    			+ "/courses/"+this.courseId
	    			+"/students/"
	    			+ "?access_token="
	    			+ this.token
	    			;
	    	
	    }
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
				String output;
				System.out.println("Output from Server:\n");
				while ((output = responseBuffer.readLine()) != null) {
					System.out.println(output);
					StringBuffer aa=new StringBuffer();
					String s=null;
					String regex = "(\"id\":+\\d+)|(\"name\":\"(.*?)\")|(\"sis_user_id\":\"(.*?)\")|(\"login_id\":\"(.*?)\")";
					Pattern p2=Pattern.compile(regex);
		  			Matcher m2=p2.matcher(output);	
		  			while(m2.find()){
		  				s=m2.group();	  				
		  				aa.append(s);		  				
		  			}		  			
		  			System.out.println(aa);
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
	     	    String token=“YOUR-TOKEN”;
	     	    Downloadallstudent test = new Downloadallstudent(courseId,token);
	     	    test.printurl();
	     	    test.get();
	        }

}
