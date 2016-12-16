/* 
 * Bug Fixed. 
 * Add the function to generate email text.
 * Update some methods. 
 * - 11/15/2016
 * 
 * Add sendMail code.
 * - 11/14/2016
 * 
 * Add file extension code.
 * Add unzip code
 * - 11/10/2016
 * 
 * by: Peiying Cao, Zhaolun Song, Shenwei Chen
 * 
 * 11/15/2016
 */

package edu.stevens.canvas.zip;

import java.awt.Desktop;
import java.net.URI;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

// need JavaMail API
import javax.mail.*;
import javax.mail.internet.*;




public class ZipFile {
	/* Please change the hw to your own zip file */
	public File hw = new File(
			"/Users/Steveisno1/Documents/16-17Fall/EE810Java/" + "Final Project/ZIPRULE/src/sample files.zip");
	public int stuID = 0; // get student information
	public String stuName = null;
	public String stuEmail = null;
	/* Please change the path to your own file path */
	public String inPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/"
			+ "Final Project/ZIPRULE/src/sample files.zip";
	public String outPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/" + "Final Project/ZIPRULE/src";
	Email email;
	public ZipFile(int id, String nm, String em, String hw, String fileRoot) throws Exception {
		/* These information are from other groups... */
		stuID = id;
		stuName = nm;
		stuEmail = em;
		inPath = fileRoot + "/" + hw + ".zip";
		outPath = fileRoot;
		this.hw = new File(inPath);
		new decompress(id, nm, em, hw, fileRoot);
		email = new Email(id, nm, em, hw, fileRoot);
	}

	// if homework format wrong, send email to student, let them re-submit
	public boolean isZip(String ext) {
		if (ext.equals("zip"))
			return true;
		else
			return false;
	}

	// generate email text
	public String writeEmail(List<String> wrong) {
		return email.writeEmail(wrong);
	}

	/* send email to student */
	public void sendEmail(String text) {
		email.sendEmail(text);
	}

	// automatically compile homework
	public boolean runFile(File f) {
		return false;
	}

	// send warning to student
	public void warnHw() {
		System.out.println("Warning! This homework can't be compiled!");
	}

	public static void main(String[] agrs) throws Exception {
		ruleGUI rg = new ruleGUI("demo");
	}
}