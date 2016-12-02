/* 
 * Updated GUI.
 * Able to set rules in GUI.
 * Rules are more flexible.
 * - 12/02/2016
 * 
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

class otherGroup {
	List<file> rules = new ArrayList<file>();
	List<file> rulesN = new ArrayList<file>();
	public otherGroup() {
		/*rules.add("java");
		rules.add("cpp");
		rules.add("py");*/
		// ...
		//file f = new file("*", "cpp");
		//rules.add(f);
		//...
	}
	
	public List<file> getRulesY() {
		return rules;
	}
	public List<file> getRulesN() {
		return rulesN;
	}
}

/*file rule: only include .java file, .cpp file, .py file*/

class getFiles {
	public File folder;
	public File[] listOfFiles;
	public List<file> ruleshave = new ArrayList<file>();
	public List<file> rulesnothave = new ArrayList<file>();
	public List<file> files = new ArrayList<file>();
	public List<String> fileNames;
	public List<String> filePath;
	public List<String> nameWithoutExt; // file name without extension
	public List<String> extension;
	public List<String> wrongFiles = new ArrayList<String>();
	public otherGroup other = new otherGroup();
	boolean isTrue = true;
	// get folder
	public getFiles(ZipFile Z) {
		
		
		// ...
		File zip = Z.hw;
		
		String folderName = zip.getName();	// get the folder name
		int pos = folderName.lastIndexOf(".");
		if (pos > 0) {
		    folderName = folderName.substring(0, pos);
		}
		String ext = zip.getName();	// get the folder extension
		pos = ext.lastIndexOf('.');
		if (pos > 0) {
		    ext = ext.substring(pos+1);
		}
		String folder = Z.outPath + "/" + folderName;
		this.folder = new File(folder);
		this.listOfFiles = this.folder.listFiles();
	}
	
	// set file rules
	public void setRules(List<file> rules1, List<file> rules2) {
		this.ruleshave = rules1;
		this.rulesnothave = rules2;
	}
	
	// set file names
	public void setName() {
		fileNames = new ArrayList<String>();
		nameWithoutExt = new ArrayList<String>();
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        fileNames.add(file.getName());
		        String[] temp = file.getName().split(Pattern.quote("."));
		        nameWithoutExt.add(temp[0]);
		        files.add(new file(temp[0], temp[1]));
		    }
		}
	}
	
	// get file names
	public List<String> getName() {
		return fileNames;
	}
	
	public List<String> getNameNoExt() {
		return nameWithoutExt;
	}
	
	// set the absolute path, shares the same index with List<String> fileNames
		public void setPath() {
			filePath= new ArrayList<String>();
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        filePath.add(file.getAbsolutePath());
			    }
			}
		}
	
	// get the absolute path
	public List<String> getPath() {
		return filePath;
	}
	
	// set file extension, shares the same index with List<String> fileNames
		public void setExtension() {
			extension = new ArrayList<String>();
			for (int i = 0; i < filePath.size(); i++) {
				String ext;
				String path = filePath.get(i);
				int n = path.lastIndexOf('.');
				if (n > 0) {
				    ext = path.substring(n+1);
				    extension.add(ext);
				}
			}
		}
	
	// get file extension
	public List<String> getExtension() {
		return extension;
	}
	
	// set together...
	public void setAll() {
		setName();
		setPath();
		setExtension();
		//otherGroup other = new otherGroup();
		setRules(other.getRulesY(), other.getRulesN());
	}
	// contains the required files
	public boolean contains(List<file> l, file f) {
		//if(f.filename.equals("") == false && f.fileext.equals("") == false) {
			for(int i = 0; i < l.size(); i++) {
				if(l.get(i).filename.equals(f.filename) == true && l.get(i).fileext.equals(f.fileext) == true) {
					return true;
				}
				if(l.get(i).filename.equals("") == true && l.get(i).fileext.equals(f.fileext) == true) {
					return true;
				}
				if(l.get(i).filename.equals(f.filename) == true && l.get(i).fileext.equals("") == true) {
					return true;
				}
				if(l.get(i).filename.equals("") == true && l.get(i).fileext.equals("") == true) {
					return true;
				}
			}
			return false;
		//}
		/*
		else if(f.filename.equals("") == true && f.fileext.equals("") == false) {
			for(int i = 0; i < l.size(); i++) {
				if(l.get(i).fileext.equals(f.fileext) == true) {
					return true;
				}
			}
			return false;
		}
		else if(f.filename.equals("") == false && f.fileext.equals("") == true) {
			for(int i = 0; i < l.size(); i++) {
				if(l.get(i).filename.equals(f.filename) == true) {
					return true;
				}
			}
			return false;
		}
		else {
			return true;
		}*/
	}
	
	
	// check the extension with the rules
	public void checkRules() {
		System.out.println("Checking the file rules...");
		int count = 0;
		int tot = ruleshave.size();
		boolean[] correct = new boolean[tot];
		for(int i = 0; i < extension.size(); i++) {
			if(contains(rulesnothave, files.get(i)) == true) {
				isTrue = false;
				if(count == 0) {
					System.out.println("You get the wrong rules!");
					count++;
				}
				System.out.println("\"" + fileNames.get(i) + "\"" 
						+ " is not allowed!");
				wrongFiles.add(fileNames.get(i));
			}
		}
		System.out.println();
	}
}

public class ZipFile {
	/* Please change the hw to your own zip file */
	public File hw = new File("/Users/Steveisno1/Documents/16-17Fall/EE810Java/"
			+ "Final Project/ZIPRULE/src/sample files.zip"); 
	public int stuID = 0;  //get student information
	public String stuName = null;
	public String stuEmail = null;
	/* Please change the path to your own file path*/
	public String inPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/"
			+ "Final Project/ZIPRULE/src/sample files.zip";
	public String outPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/"
			+ "Final Project/ZIPRULE/src";
	
	public ZipFile() throws Exception {
		/* These information are from other groups...*/
		stuID = 10404898;
		stuName = "Shenwei Chen";
		stuEmail = "schen31@stevens.edu";
		
		// uncompress the zip file
		deCom(inPath, outPath);
		
		
	}
	
	// if homework format wrong, send email to student, let them re-submit
	public boolean isZip(String ext) {
		if(ext.equals("zip"))
			return true;
		else
			return false;
	}

	// generate email text
	public String writeEmail(List<String> wrong) {
		String text;
		text = "Dear " + stuName + ":\n\nYou get the rules wrong for your homework.\n\n"
				+ "Here are the files that are not allowed:\n";
		for(int i = 0; i < wrong.size(); i++) {
			text = text + "\t" + wrong.get(i) + "\n";
		}
		text = text + "\nPlease resubmit the homework!\nThank you!\n\nCanvas system\n";
		String date = new SimpleDateFormat("MM/dd/yyyy   HH:mm:ss").
				format(Calendar.getInstance().getTime());
		text = text + date;
		return text; 
	}
	
	/*send email to student*/
	public void sendEmail(String text) {
		Properties p = new Properties();
		p.put("mail.smtp.auth","true");
		p.put("mail.smtp.port", "587");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(p,
		         new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		               return new PasswordAuthentication(
		                  "mycanvasmanager@gmail.com", "donttellothers");
		            }
		         });
		
		// Session session = Session.getDefaultInstance(p);
		
		try {
			System.out.println("Sending email, please wait...");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mycanvasmanager@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,	
					InternetAddress.parse(stuEmail));
			message.setSubject("Testing Subject");
			message.setText(text);

			Transport.send(message);

			System.out.println("Email has been sent successfully!\n");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	
	}
	//automatically compile homework
	public boolean runFile(File f) {
		return false;
	}
	// send warning to student
	public void warnHw() {
		System.out.println("Warning! This homework can't be compiled!");
	}
	
	//zip file decompression
	//problem about this part: cannot see file after decompression
	
	//Do we need to add a [Buffer_Size] here?
	public void deCom(String inPath, String outPath) {
		try {
			ZipInputStream zipInput = new ZipInputStream(new FileInputStream(inPath));
			BufferedInputStream bufInput = new BufferedInputStream(zipInput);
			BufferedOutputStream bufOutput = null;
			File outFile = null;
			FileOutputStream fileOut = null;
			ZipEntry zipEntry = null;
			int re = 0; //
			try {
				System.out.println("Unziping...");
				while ((zipEntry = zipInput.getNextEntry()) != null) {
						if (zipEntry.isDirectory()) {
							
						} else {
						outFile = new File(outPath, zipEntry.getName());
						if (!outFile.exists()) 
				/*??*/		(new File(outFile.getParent())).mkdirs();
						fileOut = new FileOutputStream(outFile);
						bufOutput = new BufferedOutputStream(fileOut);
						while ((re= bufInput.read()) != -1) 
							bufOutput.write(re);
						bufOutput.close();
						}		
				}
				System.out.println("Done!\n");
				fileOut.close();
				zipInput.close();
				bufInput.close();
				
			} catch (Exception e){
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] agrs) throws Exception {
		//ZipFile Z = new ZipFile();
		ruleGUI rg = new ruleGUI("demo");
	}
}
