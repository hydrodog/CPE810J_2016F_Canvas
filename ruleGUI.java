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


public class ruleGUI extends JFrame {
	private JLabel stuID, stuName, stuEmail, hwName, title, musthave, mustnot, directory;
	private JTextField must_t, mustnot_t, directories;
	private JTextArea process;
	private JButton ok, reset, setR, setD, back;
	private JTextArea t;
	private JScrollPane sp;
	private JList dir;
	private JTree tree;
	private String direct = "Current directory: /";
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode f = null;
        f = new DefaultMutableTreeNode(new file
                ("",
                ""));
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

    
	public ruleGUI(String s) throws Exception {
		super(s);
		ZipFile Z = new ZipFile();
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
		
		
		dir = new JList();
		
		String folderName = Z.hw.getName();	// get the folder name
		int pos = folderName.lastIndexOf(".");
		if (pos > 0) {
		    folderName = folderName.substring(0, pos);
		}
		DefaultMutableTreeNode top =
		        new DefaultMutableTreeNode(folderName);
		rootNode = top;
		treeModel = new DefaultTreeModel(rootNode);
		createNodes(rootNode);
		tree = new JTree(rootNode);
		setD.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String D = directories.getText();
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                       tree.getLastSelectedPathComponent();
				//System.out.println(node);
				//if(node.isLeaf() == false) {
					DefaultMutableTreeNode category = null;
					DefaultMutableTreeNode f = null;
			        category = new DefaultMutableTreeNode(D);
					node.add(category);
					f = new DefaultMutableTreeNode(new file
			                ("",
			                ""));
			        category.add(f);
			        addObject(category);
			        
				//}
			}
		});
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("root");
		process = new JTextArea();
		process.setEditable(false);
		sp = new JScrollPane(process);
		sp.setBounds(10,60,780,500);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ok = new JButton("OK");
		reset = new JButton("RESET");
		setR = new JButton("SET");
		
		setR.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// ...
				String s1 = must_t.getText();
				String s2 = mustnot_t.getText();
				readRule(s1, true);
				readRule(s2, false);
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
			}
		});
		
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(have.size() != 0 || nothave.size() != 0)
					process.setText(process.getText() + "Reset the rules!\n");
				must_t.setText("");
				mustnot_t.setText("");
				have.clear();
				nothave.clear();
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
					for(int i = 0; i < have.size(); i++) {
						G.other.rules.add(have.get(i));
					}
					for(int i = 0; i < nothave.size(); i++) {
						G.other.rulesN.add(nothave.get(i));
					}
					G.setAll();
					G.checkRules();
					if(G.isTrue == false) {
						//Z.sendEmail(Z.writeEmail(G.wrongFiles));
					}
					else {
						System.out.println("You are good! Nothing wrong with the rules!\n");
					}
					System.out.println("Compiling the file...");
					if(Z.runFile(Z.hw) == false) {
						Z.warnHw();
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
		pane1.add(tree, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 400;
		c.ipady = 10;
		pane3.add(directories, c);
		c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 10;
		pane3.add(setD, c);
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
		setLocationRelativeTo(null); 
		setVisible(true);
	}
	
	
	List<file> have = new ArrayList<file>();
	List<file> nothave = new ArrayList<file>();
	
	public void readRule(String s, boolean isHave) {
		if(s.equals("") == false) {
			String[] sub = s.split(";");
			if(isHave == true) {
				for(int i = 0; i < sub.length; i++) {
					if(sub[i].contains(".")) {
						String[] temp = sub[i].split(Pattern.quote("."));
						file f = new file(temp[0], temp[1]);
						have.add(f);
					}
					else {
						file f = new file("*", sub[i]);
						have.add(f);
					}
				}
			}
			else {
				for(int i = 0; i < sub.length; i++) {
					if(sub[i].contains(".")) {
						String[] temp = sub[i].split(Pattern.quote("."));
						file f = new file(temp[0], temp[1]);
						nothave.add(f);
					}
					else {
						file f = new file("*", sub[i]);
						nothave.add(f);
					}
				}
			}
		}
		
		
		
	}
	
	
	
}

