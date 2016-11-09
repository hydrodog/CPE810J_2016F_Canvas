package edu.stevens.canvas.zip;
import java.awt.Desktop;
import java.net.URI;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ZipFile {
	//get homework name(include format?)
	private File hw = null;
	//get student information
	private int stuID = 0;
	private String stuName = null;
	private String stuEmail = null;
	public ZipFile() throws Exception {
		
    	hw = new File("/Users/duck/Desktop/Midterm.docx");
		stuEmail = "pcao2@stevens.edu";
		
		if(isZip(hw) == false) {
			sendEmail();
		}
		
		if(runFile(hw) == false) {
			warnHw();
		}
		
	}
	
	// if homework format wrong, send email to student, let them re-submit
	private boolean isZip(File f) {
		return false;
	}

	/*send email to student*/
	public void sendEmail() throws Exception{
	
		Desktop d = Desktop.getDesktop();
		
		d.mail(new URI("mailto:"+ stuEmail));
		
	}
	//automatically compile homework
	public boolean runFile(File f) {
		return false;
	}
	// send warning to student
	public void warnHw() {
		System.out.println("hahaha");
	}
	
	//zip file depression
	private static void depre(String inPath, String outPath) {
		try {
			ZipInputStream zipInput = new ZipInputStream(new FileInputStream(inPath));
			BufferedInputStream bufInput = new BufferedInputStream(zipInput);
			BufferedOutputStream bufOutput = null;
			File outFile = null;
			FileOutputStream fileOut = null;
			ZipEntry zipEntry = null;
		
			try {
				while ((zipEntry = zipInput.getNextEntry()) != null) {
						if (zipEntry.isDirectory()) 
							continue;
						outFile = new File(outPath, zipEntry.getName());
						if (!outFile.exists()) 
				/*??*/		(new File(outFile.getParent())).mkdirs();
						fileOut = new FileOutputStream(outFile);
						bufOutput = new BufferedOutputStream(fileOut);
						int r = bufInput.read();
						while (r != -1) 
							bufOutput.write(r);
						
					fileOut.close();
					bufOutput.close();
				}
				zipInput.close();
				bufInput.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/*file rule: only include .java file, .cpp file, .py file*/
	static class otherGroup {
		private List<String> rules = new ArrayList<String>();
		public otherGroup() {
			rules.add("java");
			rules.add("cpp");
			rules.add("py");
		}
		public List<String> getRules() {
			return rules;
		}
	}

	class getFiles {
		private File folder;
		private File[] listOfFiles;
		private List<String> rules;
		private List<String> fileNames;
		private List<String> filePath;
		private List<String> extension;
		
		// get folder
		public getFiles(String directory) {
			this.folder = new File(directory);
			this.listOfFiles = folder.listFiles();
		}
		
		// set file rules
		public void setRules(List<String> rules) {
			this.rules = rules;
		}
		
		// set file names
		public void setName() {
			fileNames = new ArrayList<String>();
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        fileNames.add(file.getName());
			    }
			}
		}
		
		// get file names
		public List<String> getName() {
			return fileNames;
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
			otherGroup other = new otherGroup();
			setRules(other.getRules());
		}
		
		// check the extension with the rules
		public void checkRules() {
			for(int i = 0; i < extension.size(); i++) {
				//System.out.println(rules.contains(extension.get(i)));
				if(rules.contains(extension.get(i)) == false) {
					System.out.println("YOU GET THE WRONG RULES!");
					System.out.println("\"" + fileNames.get(i) + "\"" + " is not allowed!");
				}
			}
		}
	}
	
	
	//main
	public static void main(String[] agrs) throws Exception {
		new ZipFile();
		
		// change the directory here...
		String folder = "data/samples";
		getFiles f = new getFiles(folder);
		f.setAll();
		/*for (int i = 0; i < f.getName().size(); i++) {
		    System.out.println(f.getName().get(i));
		}
		for (int i = 0; i < f.getPath().size(); i++) {
		    System.out.println(f.getPath().get(i));
		    System.out.println(f.getExtension().get(i));
		}*/
		f.checkRules();
	}
}
