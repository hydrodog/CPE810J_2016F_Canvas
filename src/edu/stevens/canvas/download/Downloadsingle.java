package testpl;
import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloadsingle {
	private String courseId;
   private String userId;
   private String token;
   private static String targetURL;
   private  static double grade;
   static String output;
   public Downloadsingle(String courseId,String userId,String token){
   	this.courseId=courseId;
   	this.userId=userId;
   	this.token=token;
   	
   	Downloadsingle.targetURL = "https://canvas.instructure.com/api/v1"
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
   		
//   	return output;
		}
   
       public void printurl(){
       	System.out.println(targetURL);
       }
       
      public static double getGrade(){
   	   return grade;
      }
      public static void main(String[] args){//a test example
   	    String courseId="133";
   	    String userId="20168";
   	    String token= "your-token";
   	     Downloadsingle test =  new Downloadsingle(courseId,userId,token);
   	     test.printurl();
   	     test.get();
   	     
 
      }

}