/**
 *  @author: Yang Zhang. Gaojian Chen  
 * 
 *  
 */
package edu.stevens.canvas.download;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class DownloadFile {

  public static void main(String[] args) throws IOException {
		 
		 String fileName = "a.csv"; //The file that will be saved on your computer
		 URL link = new URL("https://instructure-uploads.s3.amazonaws.com/account_10300000000000001/attachments/2198944/ArduinoUno_R3_Front.jpg?AWSAccessKeyId=AKIAJFNFXH2V2O7RPCAA&Expires=1479666403&Signature=KSqS1m6fpe0CelmxbZzsN7GadTY%3D&response-content-disposition=attachment%3B%20filename%3D%22ArduinoUno_R3_Front.jpg%22%3B%20filename%2A%3DUTF-8%27%27ArduinoUno%255FR3%255FFront.jpg");
				 ReadableByteChannel rbc = Channels.newChannel(link.openStream());
		 FileOutputStream fos = new FileOutputStream("01.zip");
		 fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		 
     //Code to download
//		 InputStream in = new BufferedInputStream(link.openStream());
//		 ByteArrayOutputStream out = new ByteArrayOutputStream();
//		 byte[] buf = new byte[1024];
//		 int n = 0;
//		 while (-1!=(n=in.read(buf)))
//		 {
//		    out.write(buf, 0, n);
//		 }
//		 out.close();
//		 in.close();
//		 byte[] response = out.toByteArray();
// 
//		 FileOutputStream fos = new FileOutputStream(fileName);
//		 fos.write(response);
//		 fos.close();
     //End download code
		 
		 System.out.println("Finished");

	}

}
 

