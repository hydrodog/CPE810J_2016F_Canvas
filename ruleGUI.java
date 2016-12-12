package edu.stevens.canvas.zip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

class file {
	String filename;
	String fileext;
	boolean isAdd;
	boolean isChecked;
	public file(String n, String e) {
		this.filename = n;
		this.fileext = e;
		if(n.equals("*")) {
			this.filename = "";
		}
		if(e.equals("*")) {
			this.fileext = "";
		}
	}
	public file() {
		this.fileext = "";
		this.filename = "";
	}
	public String toString() {
		if(filename.equals("") == true && fileext.equals("") == true) {
			return "*.~";
		}
		if(filename.equals("") == true && fileext.equals("") == false) {
			return "*." + fileext;
		}
		if(filename.equals("") == false && fileext.equals("") == true) {
			return filename + ".~";
		}
		else {
			return filename + "." + fileext;
		}
	}
}

class direc {
	public List<file> have;
	public List<file> nothave;
	public List<direc> d;
	public String name;
	public direc(String s) {
		name = s;
		have = new ArrayList<file>();
		nothave = new ArrayList<file>();
		d = new ArrayList<direc>();
	}
}

public class ruleGUI extends JFrame {
	private JLabel stuID, stuName, stuEmail, hwName, title, musthave, mustnot, directory;
	private JTextField must_t, mustnot_t, directories;
	private JTextArea process;
	private JButton ok, reset, setR, setD, removeD;
	private JTextArea t;
	private JScrollPane sp, treeView;
	private JList dir;
	private JTree tree;
	private String direct = "Current directory: /";
	public direc DIRECTORY;
	boolean isSend = false;
	public List<String> output = new ArrayList<String>();
	
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode f = null;
        f = new DefaultMutableTreeNode("Allows all files");
        top.add(f);
 /*
        category = new DefaultMutableTreeNode("src");
        top.add(category);
 
        //original Tutorial
        f = new DefaultMutableTreeNode(new file
            ("main",
            "cpp"));
        category.add(f);
 
        //Tutorial Continued
        f = new DefaultMutableTreeNode(new file
            ("zip",
            "java"));
        category.add(f);*/
    }
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
    DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            //There is no selection. Default to the root node.
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }
    
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child,
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode =
                new DefaultMutableTreeNode(child);
        
        treeModel.insertNodeInto(childNode, parent,
                                 parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public void searchDirect(direc D, String s, direc result) {
    	if(D.name.equals(s)) {
    		result = new direc(null);
    		result = D;
    		return;
    	}
    	else {
    		for(int i = 0; i < D.d.size(); i++) {
    			searchDirect(D.d.get(i), s, result);
    			if(result.equals(DIRECTORY) == false) {
    				return;
    			}
    		}
    	} 
    }
    
    public boolean isContains(direc D, String s) {
    	for(int i = 0; i < D.d.size(); i++) {
    		if(D.d.get(i).name.equals(s) == true) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public ruleGUI(String s) throws Exception {
		super(s);
		process = new JTextArea();
		process.setEditable(false);
		
		process.setText(process.getText() + "Unziping...\n");
		ZipFile Z = new ZipFile();
		process.setText(process.getText() + "Done!\n");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		title = new JLabel("ZIP FILE RULES", SwingConstants.CENTER);
		stuID = new JLabel("Student CWID: " + "10404898", SwingConstants.CENTER);
		stuName = new JLabel("Student Name: " + "Shenwei Chen", SwingConstants.CENTER);
		stuEmail = new JLabel("Student Email: " + "schen31@stevens.edu", SwingConstants.CENTER);
		hwName = new JLabel("Homework: " + "example homework", SwingConstants.CENTER);
		musthave = new JLabel("Must have: ");
		mustnot = new JLabel("Must not have: ");
		directory = new JLabel(direct, SwingConstants.CENTER);
		must_t = new JTextField();
		mustnot_t = new JTextField();
		directories = new JTextField();
		setD = new JButton("add new directory");
		removeD = new JButton("remove directory");

		
		String folderName = Z.hw.getName();	// get the folder name
		int pos = folderName.lastIndexOf(".");
		if (pos > 0) {
		    folderName = folderName.substring(0, pos);
		}
		
		DIRECTORY = new direc(folderName);
		
		DefaultMutableTreeNode top =
		        new DefaultMutableTreeNode(folderName);
		rootNode = top;
		treeModel = new DefaultTreeModel(rootNode);
		createNodes(rootNode);
		tree = new JTree(rootNode);
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		
		setD.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String D = directories.getText();
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                       tree.getLastSelectedPathComponent();
				//System.out.println(node);
				if(node.isLeaf() == false) {
				}
				else {
					node = (DefaultMutableTreeNode) node.getParent();
				}
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)
						node.getFirstChild();
				boolean isExist = false;
				//System.out.println(child);
				if(child.toString().equals(D) == true) {
					process.setText(process.getText() + "Directory already exist!\n");
					isExist = true;
				}
				while(child.getNextSibling() != null) {
					child = (DefaultMutableTreeNode)
							child.getNextSibling();
					//System.out.println(child);
					if(child.toString().equals(D) == true) {
						process.setText(process.getText() + "Directory already exist!\n");
						isExist = true;
					}
				}
				if(isExist == false) {
					DefaultMutableTreeNode category = null;
					DefaultMutableTreeNode f = null;
					
					List<String> list = new ArrayList<String>();
					
			        category = new DefaultMutableTreeNode(D);
			        list.add(category.toString());
					node.add(category);
					f = new DefaultMutableTreeNode("Allows all files");
			        category.add(f);
			        //addObject(category);
			        //DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
			        model.reload(node);
			        
					DefaultMutableTreeNode temp = node;
					//if((DefaultMutableTreeNode)temp.getParent() != null) {
						list.add(node.toString());
					//}
					while((DefaultMutableTreeNode)temp.getParent() != null) {
						list.add(((DefaultMutableTreeNode)temp.getParent()).toString());
						/*if((DefaultMutableTreeNode)((DefaultMutableTreeNode)temp.getParent()).getParent() == null) {
							break;
						}*/
						temp = (DefaultMutableTreeNode)temp.getParent();
						
					}
					direc d = DIRECTORY;
			        for(int i = list.size()-2; i >= 0; i--) {
			        	//System.out.println(list.get(i));
			        	if(isContains(d, list.get(i)) == false) {
			        		d.d.add(new direc(list.get(i)));
			        	}
			        	for(int ii = 0; ii < d.d.size(); ii++) {
			        		if(d.d.get(ii).name.equals(list.get(i)) == true) {
			        			d = d.d.get(ii);
			        			break;
			        		}
			        	}
					}
				}
				
		        /*
		        for(int i = 0; i < list.size(); i++) {
		        	System.out.println(list.get(i));
		        }
		        */
		        
		        //System.out.println(d.name);
		        //System.out.println(DIRECTORY.d.size());
		        /*
		        for(int i = 0; i < DIRECTORY.d.size(); i++) {
		        	process.setText(process.getText() + " " + DIRECTORY.d.get(i).name);
		        	if(DIRECTORY.d.get(i).d.size() != 0) {
		        		for(int ii = 0; ii < DIRECTORY.d.get(i).d.size(); ii++) {
		        			process.setText(process.getText() + " " + DIRECTORY.d.get(i).d.get(ii).name + "*");
		        			if(DIRECTORY.d.get(i).d.get(ii).d.size() != 0) {
				        		for(int iii = 0; iii < DIRECTORY.d.get(i).d.get(ii).d.size(); iii++) {
				        			process.setText(process.getText() + " " + DIRECTORY.d.get(i).d.get(ii).d.get(iii).name + "**");
				        		}
				        	}
		        		}
		        	}
		        }*/
		        //process.setText("1");
				must_t.setText("");
				mustnot_t.setText("");
				directories.setText("");
			}
		});
		
		removeD.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                       tree.getLastSelectedPathComponent();
				List<String> list = new ArrayList<String>();
				DefaultMutableTreeNode temp = node;
				//if((DefaultMutableTreeNode)temp.getParent() != null) {
					list.add(node.toString());
				//}
				while((DefaultMutableTreeNode)temp.getParent() != null) {
					list.add(((DefaultMutableTreeNode)temp.getParent()).toString());
					/*if((DefaultMutableTreeNode)((DefaultMutableTreeNode)temp.getParent()).getParent() == null) {
						break;
					}*/
					temp = (DefaultMutableTreeNode)temp.getParent();
					
				}
				direc d = DIRECTORY;
				direc t = null;
		        for(int i = list.size()-2; i >= 0; i--) {
		        	//System.out.println(list.get(i));
		        	//d.d.add(new direc(list.get(i)));
		        	for(int ii = 0; ii < d.d.size(); ii++) {
		        		if(d.d.get(ii).name.equals(list.get(i)) == true) {
		        			t = d;
		        			d = d.d.get(ii);
		        			break;
		        		}
		        	}
				}
		        //System.out.println(d.name);
		        for(int i = 0; i < t.d.size(); i++) {
		        	if(t.d.get(i).name.equals(d.name)) {
		        		t.d.remove(i);
		        		break;
		        	}
		        }
		        
				if(node.isLeaf() == false) {
					DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) node.getParent();
					node1.remove(node);
					model.reload(node1);
				}
				for(int i = 0; i < DIRECTORY.d.size(); i++) {
		        	process.setText(process.getText() + " " + DIRECTORY.d.get(i).name);
		        	if(DIRECTORY.d.get(i).d.size() != 0) {
		        		for(int ii = 0; ii < DIRECTORY.d.get(i).d.size(); ii++) {
		        			process.setText(process.getText() + " " + DIRECTORY.d.get(i).d.get(ii).name + "*");
		        			if(DIRECTORY.d.get(i).d.get(ii).d.size() != 0) {
				        		for(int iii = 0; iii < DIRECTORY.d.get(i).d.get(ii).d.size(); iii++) {
				        			process.setText(process.getText() + " " + DIRECTORY.d.get(i).d.get(ii).d.get(iii).name + "**");
				        		}
				        	}
		        		}
		        	}
		        }
				must_t.setText("");
				mustnot_t.setText("");
				directories.setText("");
			}
		});
		
		
		
		sp = new JScrollPane(process);
		sp.setBounds(10,60,780,500);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		treeView = new JScrollPane(tree);
		treeView.setBounds(10,60,780,500);
		treeView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ok = new JButton("OK");
		reset = new JButton("RESET");
		setR = new JButton("SET");
		
		setR.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// ...
				String s1 = must_t.getText();
				String s2 = mustnot_t.getText();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                       tree.getLastSelectedPathComponent();
				if(node.isLeaf() == false) {
				}
				else {
					node = (DefaultMutableTreeNode) node.getParent();
				}
				String s = node.toString();
				direc result = DIRECTORY;
				/*searchDirect(DIRECTORY, s, result);
				direc d = result;
				System.out.println(d.name);
				*/
				List<String> list = new ArrayList<String>();
				DefaultMutableTreeNode temp = node;
				list.add(node.toString());
				while((DefaultMutableTreeNode)temp.getParent() != null) {
					list.add(((DefaultMutableTreeNode)temp.getParent()).toString());
					temp = (DefaultMutableTreeNode)temp.getParent();
				}
				direc d = DIRECTORY;
				//System.out.println(d.d.get(0).name);
				//System.out.println(d.d.get(0).d.get(0).name);
				//System.out.println(d.d.get(0).d.get(0).d.get(0).name);
				//System.out.println(d.d.get(0).d.get(0).d.get(0).d.get(0).name);
				
				
				for(int i = list.size()-2; i >= 0; i--) {
					//System.out.println(list.get(i));
					for(int ii = 0; ii < d.d.size(); ii++) {
						//System.out.println(d.d.get(ii).name);
						if(d.d.get(ii).name.equals(list.get(i)) == true) {
							d = d.d.get(ii);
							break;
						}
					}
				}
				
				
				readRule(d, s1, true);
				readRule(d, s2, false);
				DefaultMutableTreeNode f = null;
				for(int i = 0; i < d.have.size(); i++) {
					if(node.getFirstLeaf().toString().equals("Allows all files") == true) {
						node.remove(node.getFirstLeaf());
						model.reload(node);
					}
					//System.out.println(node);
					if(d.have.get(i).isAdd != true) {
						f = new DefaultMutableTreeNode(d.have.get(i));
						node.add(f);
						d.have.get(i).isAdd = true;
					}
				}
				for(int i = 0; i < d.nothave.size(); i++) {
					if(node.getFirstLeaf().toString().equals("Allows all files") == true) {
						node.remove(node.getFirstLeaf());
						model.reload(node);
					}
					if(d.nothave.get(i).isAdd != true) {
						f = new DefaultMutableTreeNode("Not Allowed: " + d.nothave.get(i));
						node.add(f);
						d.nothave.get(i).isAdd = true;
					}
				}
				model.reload(node);
				boolean said = false;
				if(have.size() != 0) {
					process.setText(process.getText() + "Rules set!\n");
					said = true;
					process.setText(process.getText() + "Zip must contain: ");
					for(int i = 0; i < have.size(); i++) {
						if(i == have.size() - 1) {
							process.setText(process.getText() + have.get(i).fileext);
							break;
						}
						process.setText(process.getText() + have.get(i).fileext  + ", ");
					}
					process.setText(process.getText() + "\n");
				}
				if(nothave.size() != 0) {
					if(said == false) {
						process.setText(process.getText() + "Rules set!\n");
					}
					process.setText(process.getText() + "Zip must not contain: ");
					for(int i = 0; i < nothave.size(); i++) {
						if(i == nothave.size() - 1) {
							process.setText(process.getText() + nothave.get(i).fileext);
							break;
						}
						process.setText(process.getText() + nothave.get(i).fileext  + ", ");
					}
					process.setText(process.getText() + "\n");
				}
				must_t.setText("");
				mustnot_t.setText("");
				directories.setText("");
			}
		});
		
		String str = folderName;
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				root.removeAllChildren();
		        DefaultMutableTreeNode f = null;
		        f = new DefaultMutableTreeNode("Allows all files");
		        root.add(f);
		        model.reload();
				DIRECTORY = new direc(str);
				must_t.setText("");
				mustnot_t.setText("");
				directories.setText("");
			}
		});
		
		
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//otherGroup other = new otherGroup();
				
				try {
					getFiles G = new getFiles(Z);
					String newDirect = G.folder.getName();
					int pos = newDirect.lastIndexOf(".");
					if (pos > 0) {
					    newDirect = newDirect.substring(0, pos);
					}
					/*
					for(int i = 0; i < have.size(); i++) {
						G.other.rules.add(have.get(i));
					}
					for(int i = 0; i < nothave.size(); i++) {
						G.other.rulesN.add(nothave.get(i));
					}*/
					//G.setAll();
					//System.out.println(DIRECTORY.name);
					G.checkRules(G.RealDirectory, DIRECTORY);
					for(int i = 0; i < G.console.size(); i++) {
						process.setText(process.getText() + G.console.get(i) + "\n");
					}
					
					
					if(G.istrue == false) {
						// use thread to update GUI while in progress
						process.setText(process.getText() + "Sending email, please wait...\n");
						SwingWorker<List<String>, Object> worker = new SwingWorker<List<String>, Object>() {
				        @Override
				        protected List<String> doInBackground() throws Exception {                
				            return G.console; // call a REST API
				        }
				        @Override
				        protected void done() {
				            try {
				            	
				        		Z.sendEmail(Z.writeEmail(get()));
				        		process.setText(process.getText() + "Email has been sent successfully!\n");
				            } catch (Exception e) {
				                //ignore
				            }
				        }
				    };      
				    worker.execute();
					}
					
					
					else {
						process.setText(process.getText() + "Nothing wrong with the rules!\n");
						//System.out.println("You are good! Nothing wrong with the rules!\n");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		JPanel pane1 = new JPanel(new GridBagLayout());
		JPanel pane2 = new JPanel(new GridBagLayout());
		JPanel pane3 = new JPanel(new GridBagLayout());
		JPanel pane4 = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			//natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		pane1.add(title, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		pane2.add(stuID, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		pane2.add(stuName, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		pane2.add(stuEmail, c);
		
		/*c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		pane1.add(directory);
		*/
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		
		c.ipady = 20;      //make this component tall
		c.gridx = 0;
		c.gridy = 1;
		pane1.add(pane2, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		pane1.add(directory, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.ipady = 100;
		pane1.add(treeView, c);
		
		JPanel temppane = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		temppane.add(setD, c);
		c.gridx = 1;
		temppane.add(removeD, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 400;
		c.ipady = 10;
		pane3.add(directories, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 10;
		pane3.add(temppane, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 10;
		pane3.add(musthave, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 10;
		pane3.add(must_t, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 10;
		pane3.add(mustnot, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = 10;
		pane3.add(mustnot_t, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.ipady = 40;      //make this component tall
		c.gridx = 0;
		c.gridy = 4;
		pane1.add(pane3, c);
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 10;
		pane4.add(setR, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		pane4.add(reset, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		pane4.add(ok, c);
		//c.gridwidth = 3;
		//c.ipady = 40;      //make this component tall
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.ipady = 10;
		pane1.add(pane4, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		c.ipady = 100;
		pane1.add(sp, c);
		
		
		getContentPane().add(pane1);
		setSize(800,600);
		setResizable(false);
		setLocationRelativeTo(null); 
		setVisible(true);
	}
	
	
	List<file> have = new ArrayList<file>();
	List<file> nothave = new ArrayList<file>();
	
	public void readRule(direc d, String s, boolean isHave) {
		if(s.equals("") == false) {
			String[] sub = s.split(";");
			if(isHave == true) {
				for(int i = 0; i < sub.length; i++) {
					if(sub[i].contains(".")) {
						String[] temp = sub[i].split(Pattern.quote("."));
						file f = new file(temp[0], temp[1]);
						boolean ishave = false;
						for(int ii = 0; ii < d.have.size(); ii++) {
							if(d.have.get(ii).toString().equals(sub[i]) == true) {
								ishave = true;
							}
						}
						if(ishave == false) {
							d.have.add(f);
						}
					}
					else {
						file f = new file("*", sub[i]);
						boolean ishave = false;
						for(int ii = 0; ii < d.have.size(); ii++) {
							if(d.have.get(ii).fileext.equals(sub[i]) == true && 
									d.have.get(ii).filename.equals("")) {
								ishave = true;
							}
						}
						if(ishave == false) {
							d.have.add(f);
						}
					}
				}
			}
			else {
				for(int i = 0; i < sub.length; i++) {
					if(sub[i].contains(".")) {
						String[] temp = sub[i].split(Pattern.quote("."));
						file f = new file(temp[0], temp[1]);
						boolean ishave = false;
						for(int ii = 0; ii < d.nothave.size(); ii++) {
							if(d.nothave.get(ii).toString().equals(sub[i]) == true) {
								ishave = true;
							}
						}
						if(ishave == false) {
							d.nothave.add(f);
						}
					}
					else {
						file f = new file("*", sub[i]);
						boolean ishave = false;
						for(int ii = 0; ii < d.nothave.size(); ii++) {
							if(d.nothave.get(ii).fileext.equals(sub[i]) == true && 
									d.nothave.get(ii).filename.equals("")) {
								ishave = true;
							}
						}
						if(ishave == false) {
							d.nothave.add(f);
						}
					}
				}
			}
		}
	}
}

