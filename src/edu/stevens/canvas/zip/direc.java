package edu.stevens.canvas.zip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.*;

public class direc implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4503890192053685686L;
	public List<file> have;
	public List<file> nothave;
	public List<direc> d;
	public String name;
	public direc() {
		name = null;
		have = null;
		nothave = null;
		d = null;
	}
	public direc(String s) {
		name = s;
		have = new ArrayList<file>();
		nothave = new ArrayList<file>();
		d = new ArrayList<direc>();
	}
	public void setHave(List<file> h) {
		this.have = h;
	}
	public void setNothave(List<file> n) {
		this.have = n;
	}
	public void setD(List<direc> d) {
		this.d = d;
	}
	public void setName(String n) {
		this.name = n;
	}
	public List<file> getHave() {
		return this.have;
	}
	public List<file> getNothave() {
		return this.nothave;
	}
	public List<direc> getD() {
		return this.d;
	}
	public String getName() {
		return this.name;
	}
}