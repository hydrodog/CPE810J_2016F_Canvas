/*
 * 
 * Author: Gaojian Chen
 * Modifier: Yang Zhang, Siyu Sun
 * 
 * 
 * 
 * */
package Download;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * This class download one submission of students assignment from Canvas
 * via API, and save it as specific file name. User needs to provide 
 * assignments Id and student Id and the java will automatically download
 * files and save it to folder
 * 
 * 
 * */
public class DownloadAssignment {
	private String courseId;
    private static String assignmentId;
    private static String userId;
    private String token;   
    private static String url;
    private static String filename;
    private static String targetURL;
    private static String output;
    
    public DownloadAssignment(String courseId,String assignmentId,String studentId,String token){
    	this.courseId=courseId;
    	this.assignmentId=assignmentId;
    	this.userId=studentId;
    	this.token=token;
    /*
     * The targetURL is the curl format API from Canvas. By inputing courseId,
     * assignmentId, userId and token, we can get access to the submission info
     * in canvas, it is a JSON format message which contains download URL of 
     * submission.
     * 
     * */	
    	DownloadAssignment.targetURL = "https://sit.instructure.com/api/v1"
    			+ "/courses/"+this.courseId
    			+ "/assignments/"+this.assignmentId
    			+ "/submissions/"+this.userId
    			+ "?access_token="
    			+ this.token
    			;
    	
    }
    /*
     * Function get() will build the link and get content from targetURL
     * we using String "output" to save these contents
     * */
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
//				System.out.println(output);
	    	 	String s=null,s2=null,ss=null,ss2=null;
	    	 	String []s1=new String[3];
	    	 	String []ss1=new String[5];
	    	 	/*
	    	 	 * Here we need the assignment URL from the JSON.
	    	 	 * To download the assignment
	    	 	 * We put the url that found in Jason to the String url
	    	 	 * */
	   	   		String regex = "(\")(https:\\/\\/sit.instructure.com\\/files(.*?))(\")";
	   	   		Pattern p2=Pattern.compile(regex);
	  			Matcher m2=p2.matcher(output);	   	   		
	  			while(m2.find()){
	  				s=m2.group(2);	  					  				
	  			}	  			
	  			url=s;
	  			
	  			/*We need to show up the filename for the assignment
	  			 * For the people who want to check
	  			 * */
	  			String regex1="(filename\":\")(.*?)(\")";
	  			Pattern p3=Pattern.compile(regex1);
	  			Matcher m3=p3.matcher(output);	   	   		
	  			while(m3.find()){
	  				ss=m3.group(2);	  				  				
	  			}
	  			
	  			filename=ss;	
	  			
	  			System.out.println("The filename is: "+ss);
	  			System.out.println("The assignment address is: "+url);	  							
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

       public static void main(String[] args) throws IOException{//a test example
    	    String courseId="133";    	   
    	    String token="your token";
    	    Scanner input=new Scanner(System.in);
    	    System.out.println("Please input the assignmentid you want download:");
    	    assignmentId=input.nextLine();
    	    Scanner input1=new Scanner(System.in);
    	    System.out.println("Please input the userId:");
    	    userId=input1.nextLine();
    	    DownloadAssignment test2 = new DownloadAssignment(courseId,assignmentId,userId,token);
    	    test2.printurl();
    	    test2.get(); 	        	    
    	    
    	    URL link=new URL(url);
    	    ReadableByteChannel rbc = Channels.newChannel(link.openStream());
   		 	FileOutputStream fos = new FileOutputStream(filename);//save the file to folder
   		 	fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);   		 
       }
    }
