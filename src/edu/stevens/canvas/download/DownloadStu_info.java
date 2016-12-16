/*
 * 
 * Author: Yang Zhang
 * Modifier: Gaojian Chen, Siyu Sun
 * 
 * 
 * 
 * */
package edu.stevens.canvas.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*The class download the information of students including 
 * 
 * */

/*The function of this class shows the content of console to a file of txt
 * and we can also see the content from the console at the same time
 * In this function,I override the write method
 * */
class MultiOutput extends OutputStream{
	
	 OutputStream output1;	 
	 OutputStream output2;	
	 public MultiOutput(OutputStream output1,OutputStream output2){
	        this.output1 = output1;
	        this.output2 = output2;
	     }
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		output1.write(b);
		output2.write(b);
	}
}
/*The class of Student is to save the Students' information 
 * */
class Student{
	private String UserId;
	private String StuName;
	private String SisUserId;
	private String Mail;
	
	public Student(String UserId,String StuName,String SisUserId,String Mail){
		this.UserId = UserId;
		this.StuName = StuName;
		this.SisUserId = SisUserId;
		this.Mail = Mail;		
	}
	
	public Student(String UserId,String StuName){
		this.UserId = UserId;
		this.StuName = StuName;
		this.SisUserId = null;
		this.Mail = null;		
	}
	
	public Student(String UserId,String StuName,String Mail){
		this.UserId = UserId;
		this.StuName = StuName;
		this.SisUserId = null;
		this.Mail = Mail;		
	}
	
	public String getUserId(){
		return UserId;
	}

	public String getStuName(){
		return StuName;
	}
	public String getSisUserId(){
		return SisUserId;
	}
	public String getMail(){
		return Mail;
	}
	
	public String toString(){
		return UserId+"      "+StuName+"      "+SisUserId+"      "+Mail;
	}
		
}
/*The function of this class is to download the all student information from Canvas 
 * via API,and save the information separately by User_id,Student name,Email and Student_id
 * And at the same time save the information into a txt .
 * When we download the assignment,we need to check the file of txt to see what the User_id is 
 * */

public class DownloadStu_info {	
		private String courseId;
	    private String token;
	    private static String targetURL;
	    static Student d,d1,d2,d3,d4,d5,d6,d7;
	    
		
	    public DownloadStu_info(String courseId,String token){
	    	this.courseId=courseId;
	    	this.token=token;	    	
	    	DownloadStu_info.targetURL = "https://sit.instructure.com/api/v1"
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
				//System.out.println("Output from Server:\n");
				while ((output = responseBuffer.readLine()) != null) {
					//System.out.println(output);
					StringBuffer aa=new StringBuffer();
					String s=null,ss=null,ss1=null,ss2=null;
					 
					int i=0,i1=0,i2=0,i3=0;
					String []s1=new String[8];
					
					ArrayList<String> l=new ArrayList<String>();
					String regex = "(\"id\":)(\\d+)";
					Pattern p2=Pattern.compile(regex);
		  			Matcher m2=p2.matcher(output);	
		  			while(m2.find()){
		  				s=m2.group(2);	  				  	
		  				s1[i]=s;
		  				i++;
		  			}	
		  			
		  			String []s2=new String[8];
					String regex1 = "(\"name\":\")(.*?)(\")";
					Pattern p3=Pattern.compile(regex1);
		  			Matcher m3=p3.matcher(output);	
		  			while(m3.find()){
		  				ss=m3.group(2);	  					  	
		  				s2[i1]=ss;
		  				i1++;
		  			}	
		  			
		  			String []s3=new String[8];
					String regex2 = "(\"sis_user_id\":\")(.*?)(\")";
					Pattern p4=Pattern.compile(regex2);
		  			Matcher m4=p4.matcher(output);	
		  			while(m4.find()){
		  				ss1=m4.group(2);	  					
		  				s3[i2]=ss1;
		  				i2++;			
		  			}	
		  			
		  			String []s4=new String[8];
					String regex3 = "(\"login_id\":\")(.*?)(\")";
					Pattern p5=Pattern.compile(regex3);
		  			Matcher m5=p5.matcher(output);	
		  			while(m5.find()){
		  				ss2=m5.group(2);	  					  	
		  				s4[i3]=ss2;
		  				i3++;
		  			}			  			
		  			 d=new Student(s1[0],s2[0],s3[0],s4[0]);	
		  			 d1=new Student(s1[1],s2[1],s3[1],s4[1]);
		  			 d2=new Student(s1[2],s2[2],s3[2],s4[2]);
		  			 d3=new Student(s1[3],s2[3],s3[3],s4[3]);
		  			 d4=new Student(s1[4],s2[4],s3[4],s4[4]);	
		  			 d5=new Student(s1[5],s2[5]);
		  			 d6=new Student(s1[6],s2[6],s4[5]);
		  			 d7=new Student(s1[7],s2[7],s3[5],s4[6]);	
		  			System.out.println("User_Id"+"    Student Name"+"        Student Id"+"     Mail");
		  			System.out.println(d.toString());
		  			System.out.println(d2.toString());
		  			System.out.println(d3.toString());
		  			System.out.println(d4.toString());
		  			System.out.println(d5.toString());
		  			System.out.println(d6.toString());
		  			System.out.println(d7.toString());
		     	   
		  			
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

	        public static void main(String[] args) throws Exception{//a test example
	     	    String courseId="133";
	     	    String token="your token";
	     	    DownloadStu_info test = new DownloadStu_info(courseId,token);
	     	    
	     	    
	     	    File write=new File("stu_info.dat");
	     	    write.createNewFile();
	     	    FileOutputStream fileOutputStream = new FileOutputStream(write);
	     	    PrintStream printStream = new PrintStream(fileOutputStream);  
	     	    MultiOutput multi = new MultiOutput(new PrintStream(fileOutputStream),System.out) ;       
	     	    System.setOut(new PrintStream(multi));
	     	    
	     	    test.printurl();
	     	    test.get();
	     	 
	     	
	        }

}
