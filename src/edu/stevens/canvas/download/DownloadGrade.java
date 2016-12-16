/*
 * 
 * Author: Siyu Sun
 * Modifier: Yang Zhang,Gaojian Chen
 * 
 * 
 * */
package edu.stevens.canvas.download;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * This class downloads the all grades of one student from Canvas
 * via API, and save it as specific file name.
 * 
 * 
 * */
public class DownloadGrade {
	private String courseId;
    private String userId;
    private String token;
    private static String targetURL;
    private  static double grade;
    static String output;
    public DownloadGrade(String courseId,String userId,String token){
    	this.courseId=courseId;
    	this.userId=userId;
    	this.token=token;
    	
    	DownloadGrade.targetURL = "https://sit.instructure.com/api/v1"
    			+ "/courses/"+this.courseId
    			+"/analytics"
    			+ "/users/"+this.userId
    			+"/assignments/"
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

			
			//System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
			//	System.out.println(output);
				String s=null;
				ArrayList<String> l=new ArrayList<String>();
				String regex="(assignment_id\":[0-9]+)|(title\":\"(.*?)\")|(\"score\":[0-9]*.[0-9])";
				Pattern p=Pattern.compile(regex);
	  			Matcher m=p.matcher(output);
	  			while(m.find()){
	  				s=m.group();	  				
 				l.add(s);
	  			}
	  			for(int i=0;i<l.size();i++){
	  				System.out.println(l.get(i));
	  			}
			}

			httpConnection.disconnect();
			//return "success!";

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }
    		
//    	return output;
		}
    
        public void printurl(){
        	System.out.println(targetURL);
        }
   
       public static void main(String[] args){//a test example
    	    String courseId="133";
    	    String userId="20168";
    	    String token="your token";
    	     DownloadGrade test =  new DownloadGrade(courseId,userId,token);
    	     test.printurl();
    	     test.get();    	     
  
       }

}
