/* 
 * This file can extract all file extension in a folder, and compare with the canvas rules.
 * If the student get rules wrong, it can warn on the console now.
 * We will update it to linking to email instead of pring on console in future.
 * 
 * - Shenwei Chen
 */




import java.io.File;
import java.util.ArrayList;
import java.util.List;

// we need the rules from the instructor by other group, so this class is just a sample
class otherGroup {
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


public class fileExtension {
	public static void main(String[] args) {
		// change the directory here...
		String folder = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/Final Project/file extension/src/sample files";
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