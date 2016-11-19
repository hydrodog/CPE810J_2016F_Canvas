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
		 URL link = new URL("https://canvas.instructure.com/files/1030~2198944/download?download_frd=1&verifier=GQiL2ORgUV663l63jChXHabqj1syAYcqSHQVtm1D");
				 ReadableByteChannel rbc = Channels.newChannel(link.openStream());
		 FileOutputStream fos = new FileOutputStream("01.jpg");
		 fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

		 System.out.println("Finished");

	}

}
 

