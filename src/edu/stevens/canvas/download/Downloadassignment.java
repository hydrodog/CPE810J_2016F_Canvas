
/*
 * 
 * Author: Gaojian Chen
 * 
 * */
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloadassignment {


	  public static void main(String[] args) throws IOException {
			 
			 
			 URL link = new URL("https://instructure-uploads.s3.amazonaws.com/account_10300000000000001/attachments/2241842/Seal_of_Stevens_Institute_of_Technology.svg.png?AWSAccessKeyId=AKIAJFNFXH2V2O7RPCAA&Expires=1481481771&Signature=ejuyqsh9NCV4lq9rIE%2F9uHDgAjs%3D&response-content-disposition=attachment%3B%20filename%3D%22Seal_of_Stevens_Institute_of_Technology.svg.png%22%3B%20filename%2A%3DUTF-8%27%27Seal%255Fof%255FStevens%255FInstitute%255Fof%255FTechnology.svg.png"); //The file that you want to download
			 ReadableByteChannel rbc = Channels.newChannel(link.openStream());
			 FileOutputStream fos = new FileOutputStream("c.png");
			 fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			 System.out.println("Finished");
	  }
}