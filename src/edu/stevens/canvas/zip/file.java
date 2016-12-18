package edu.stevens.canvas.zip;

import java.io.Serializable;
import javax.xml.bind.*;

public class file implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String filename;
	String fileext;
	boolean isAdd;
	boolean isChecked;

	public file(String n, String e) {
		this.filename = n;
		this.fileext = e;
		if (n.equals("*")) {
			this.filename = "";
		}
		if (e.equals("*")) {
			this.fileext = "";
		}
	}

	public file() {
		this.fileext = null;
		this.filename = null;
		this.isAdd = false;
		this.isChecked = false;
	}

	public String getFileName() {
		return this.filename;
	}

	public String getFileExt() {
		return this.fileext;
	}

	public Boolean getIsAdd() {
		return this.isAdd;
	}

	public Boolean getIsChecked() {
		return this.isChecked;
	}

	public String toString() {
		if (filename.equals("") == true && fileext.equals("") == true) {
			return "*.~";
		}
		if (filename.equals("") == true && fileext.equals("") == false) {
			return "*." + fileext;
		}
		if (filename.equals("") == false && fileext.equals("") == true) {
			return filename + ".~";
		} else {
			return filename + "." + fileext;
		}
	}
}