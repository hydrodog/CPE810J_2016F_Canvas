package Download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
					String ss=null;
					String []s1=new String[8];
					ArrayList<String> l=new ArrayList<String>();
					String regex = "(\"id\":+\\d+)|(\"name\":\"(.*?)\")|(\"sis_user_id\":\"(.*?)\")|(\"login_id\":\"(.*?)\")";
					Pattern p2=Pattern.compile(regex);
		  			Matcher m2=p2.matcher(output);	
		  			while(m2.find()){
		  				s=m2.group(0);	  				
		  				//aa.append(s);	  	
		  				l.add(s);
		  			}		  			
		  			//System.out.println(l);
//		  			String regex1="(edu\")";
		  			for(int i=0;i<l.size();i++){
		  				System.out.println(l.get(i));
		  			}
//		  			String str=aa.toString();
//		  			String regex1="(edu\")";
//		  			String str1[]=str.split(regex1);
//		  			for(int i=0;i<=str1.length;i++){
//		  				System.out.println(str1[i]);
//		  			}
//		  			Pattern p3=Pattern.compile(regex1);
//		  			Matcher m3=p3.matcher(aa);	
//		  			while(m3.find()){
//		  				s1=m3.group();
//		  			}
		  			
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
	     	    String token="Your token";
	     	    Downloadallstudent test = new Downloadallstudent(courseId,token);
	     	    test.printurl();
	     	    test.get();
	        }

}
