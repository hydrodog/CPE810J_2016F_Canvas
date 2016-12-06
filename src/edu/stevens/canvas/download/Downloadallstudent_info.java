package Download;

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


public class Downloadallstudent_info {
	
		private String courseId;
	    private String token;
	    private static String targetURL;
	    private double grade;
	    public Downloadallstudent_info(String courseId,String token){
	    	this.courseId=courseId;
	    	this.token=token;
	    	
	    	Downloadallstudent_info.targetURL = "https://canvas.instructure.com/api/v1"
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
					//System.out.println(output);
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

	        public static void main(String[] args) throws Exception{//a test example
	     	    String courseId="10300000000000133";
	     	    String token="";
	     	    Downloadallstudent_info test = new Downloadallstudent_info(courseId,token);
	     	    
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
