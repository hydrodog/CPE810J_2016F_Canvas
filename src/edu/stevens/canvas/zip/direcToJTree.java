package edu.stevens.canvas.zip;

import javax.swing.tree.*;

public class direcToJTree {
	direc D;
	DefaultMutableTreeNode root;
	public direcToJTree(direc d, DefaultMutableTreeNode r) {
		D = d;
		root = r;
	}
	public void convert(direc D, DefaultMutableTreeNode R) {
		boolean isH = false;
		boolean isN = false;
		for(int i = 0; i < D.have.size(); i++) {
			DefaultMutableTreeNode temp = new DefaultMutableTreeNode(D.have.get(i).toString());
			R.add(temp);
			isH = true;
		}
		for(int i = 0; i < D.nothave.size(); i++) {
			DefaultMutableTreeNode temp = new DefaultMutableTreeNode("Not Allowed: " + D.nothave.get(i));
			R.add(temp);
			isN = true;
		}
		if(isH == false && isN == false) {
			DefaultMutableTreeNode temp1 = new DefaultMutableTreeNode("Allows all files");
			R.add(temp1);
		}
		
		for(int i1 = 0; i1 < D.d.size(); i1++) {
			DefaultMutableTreeNode temp1 = new DefaultMutableTreeNode(D.d.get(i1).name);
			R.add(temp1);
			DefaultMutableTreeNode RR = null;
			for(int j = 0; j < R.getChildCount(); j++) {
				if(R.getChildAt(j).toString().equals(D.d.get(i1).name)) {
					RR = (DefaultMutableTreeNode) R.getChildAt(j);
				}
			}
			convert(D.d.get(i1), RR);
		}
	}
}