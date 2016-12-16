package edu.stevens.canvas.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class getFiles {
	public File folder;
	public File[] listOfFiles;
	public List<String> console = new ArrayList<String>();
	public direc RealDirectory;
	public List<file> ruleshave = new ArrayList<file>();
	public List<file> rulesnothave = new ArrayList<file>();
	public List<file> files = new ArrayList<file>();
	public List<String> fileNames;
	public List<String> filePath;
	public List<String> nameWithoutExt; // file name without extension
	public List<String> extension;
	public List<String> wrongFiles = new ArrayList<String>();
	boolean istrue = true;

	// get folder
	public getFiles(ZipFile Z) {
		// ...
		File zip = Z.hw;
		String folderName = zip.getName(); // get the folder name
		int pos = folderName.lastIndexOf(".");
		if (pos > 0) {
			folderName = folderName.substring(0, pos);
		}
		String ext = zip.getName(); // get the folder extension
		pos = ext.lastIndexOf('.');
		if (pos > 0) {
			ext = ext.substring(pos + 1);
		}
		String folder = Z.outPath + "/" + folderName;
		this.folder = new File(folder);
		this.listOfFiles = this.folder.listFiles();
		RealDirectory = new direc(folderName);
		setDirectory(RealDirectory, listOfFiles);
	}

	public void setDirectory(direc D, File[] LIST) {
		for (File file : LIST) {
			if (file.isFile() == true && file.isHidden() == false) {
				String[] temp = file.getName().split(Pattern.quote("."));
				file f = new file(temp[0], temp[1]);
				D.have.add(f);
			}
			if (file.isDirectory() == true) {
				File[] newlist = file.listFiles();
				D.d.add(new direc(file.getName()));
				int index = -1;
				for (int i = 0; i < D.d.size(); i++) {
					if (D.d.get(i).name.equals(file.getName()) == true) {
						index = i;
						break;
					}
				}
				if (index != -1) {
					setDirectory(D.d.get(index), newlist);
				}
			}
		}
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
		filePath = new ArrayList<String>();
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
				ext = path.substring(n + 1);
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
		// otherGroup other = new otherGroup();
		// setRules(other.getRulesY(), other.getRulesN());
	}

	// contains the required files
	public boolean contains(List<file> l, file f) {
		// if(f.filename.equals("") == false && f.fileext.equals("") == false) {
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).filename.equals(f.filename) == true && l.get(i).fileext.equals(f.fileext) == true) {
				return true;
			}
			if (l.get(i).filename.equals("") == true && l.get(i).fileext.equals(f.fileext) == true) {
				return true;
			}
			if (l.get(i).filename.equals(f.filename) == true && l.get(i).fileext.equals("") == true) {
				return true;
			}
			if (l.get(i).filename.equals("") == true && l.get(i).fileext.equals("") == true) {
				return true;
			}
		}
		return false;
		// }
		/*
		 * else if(f.filename.equals("") == true && f.fileext.equals("") ==
		 * false) { for(int i = 0; i < l.size(); i++) {
		 * if(l.get(i).fileext.equals(f.fileext) == true) { return true; } }
		 * return false; } else if(f.filename.equals("") == false &&
		 * f.fileext.equals("") == true) { for(int i = 0; i < l.size(); i++) {
		 * if(l.get(i).filename.equals(f.filename) == true) { return true; } }
		 * return false; } else { return true; }
		 */
	}

	public boolean isContain(direc d, file f) {
		for (int i = 0; i < d.have.size(); i++) {
			if (d.have.get(i).filename.equals(f.filename) == true && d.have.get(i).fileext.equals(f.fileext) == true) {
				return true;
			}
			if (d.have.get(i).filename.equals("") == true && d.have.get(i).fileext.equals(f.fileext) == true) {
				return true;
			}
			if (d.have.get(i).filename.equals(f.filename) == true && d.have.get(i).fileext.equals("") == true) {
				return true;
			}
			if (d.have.get(i).filename.equals("") == true && d.have.get(i).fileext.equals("") == true) {
				return true;
			}
		}
		return false;
	}

	public void checkRules(direc reald, direc setd) {
		if (reald.name.equalsIgnoreCase(setd.name) == false) {
		}
		// else {
		for (int ii = 0; ii < setd.have.size(); ii++) {
			int index = -1;
			boolean isTrue = false;
			for (int i = 0; i < reald.have.size(); i++) {
				if (reald.have.get(i).filename.equalsIgnoreCase(setd.have.get(ii).filename) == true
						&& reald.have.get(i).fileext.equalsIgnoreCase(setd.have.get(ii).fileext) == true) {
					isTrue = true;
					index = i;
					break;
				}
				if (setd.have.get(ii).filename.equalsIgnoreCase("") == true
						&& reald.have.get(i).fileext.equalsIgnoreCase(setd.have.get(ii).fileext) == true) {
					isTrue = true;
					index = i;
					break;
				}
				if (reald.have.get(i).filename.equalsIgnoreCase(setd.have.get(ii).filename) == true
						&& setd.have.get(ii).fileext.equalsIgnoreCase("") == true) {
					isTrue = true;
					index = i;
					break;
				}
				if (setd.have.get(ii).filename.equalsIgnoreCase("") == true
						&& setd.have.get(ii).fileext.equalsIgnoreCase("") == true) {
					isTrue = true;
					index = i;
					break;
				}
			}
			if (isTrue == true) {
				reald.have.get(index).isChecked = true;
			} else {
				istrue = false;
				console.add("Don't have the file \"" + setd.have.get(ii).toString() + "\" in the directory \""
						+ reald.name + "\"");
				// System.out.println("You don't have the file \"" +
				// setd.have.get(ii).toString() + "\" in the directory \"" +
				// setd.name + "\"");
			}
		}
		for (int i = 0; i < reald.have.size(); i++) {
			int index = -1;
			boolean isFalse = false;
			for (int ii = 0; ii < setd.nothave.size(); ii++) {
				if (reald.have.get(i).filename.equalsIgnoreCase(setd.nothave.get(ii).filename) == true
						&& reald.have.get(i).fileext.equalsIgnoreCase(setd.nothave.get(ii).fileext) == true) {
					isFalse = true;
					index = i;
					break;
				}
				if (setd.nothave.get(ii).filename.equalsIgnoreCase("") == true
						&& reald.have.get(i).fileext.equalsIgnoreCase(setd.nothave.get(ii).fileext) == true) {
					isFalse = true;
					index = i;
					break;
				}
				if (reald.have.get(i).filename.equalsIgnoreCase(setd.nothave.get(ii).filename) == true
						&& setd.nothave.get(ii).fileext.equalsIgnoreCase("") == true) {
					isFalse = true;
					index = i;
					break;
				}
				if (setd.nothave.get(ii).filename.equalsIgnoreCase("") == true
						&& setd.nothave.get(ii).fileext.equalsIgnoreCase("") == true) {
					isFalse = true;
					index = i;
					break;
				}
			}
			if (isFalse == false) {
				// reald.have.get(index).isChecked = true;
			} else {
				if (reald.have.get(index).isChecked != true) {
					console.add("Can't have the file \"" + reald.have.get(index).toString() + "\" in the directory \""
							+ reald.name + "\"");
				}
			}
		}
		boolean isFound = false;
		int ind = -1;
		for (int j = 0; j < setd.d.size(); j++) {
			for (int jj = 0; jj < reald.d.size(); jj++) {
				if (reald.d.get(jj).name.equalsIgnoreCase(setd.d.get(j).name) == true) {
					isFound = true;
					ind = jj;
					break;
				}
			}
			if (isFound == false) {
				istrue = false;
				console.add("Don't have the directory \"" + setd.d.get(j).name + "\"");
			} else {
				checkRules(reald.d.get(ind), setd.d.get(j));
				isFound = false;
			}
		}
		// }
	}
}