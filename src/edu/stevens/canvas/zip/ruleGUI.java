package edu.stevens.canvas.zip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.tree.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;




public class ruleGUI extends JFrame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ZipFile Z;
	public List<StudentInfo> info = new ArrayList<StudentInfo>();
	private JLabel stuID, stuName, stuEmail, hwName, title, musthave, mustnot, directory, image, ddd;
	private JTextField must_t, mustnot_t, directories;
	private JTextArea process;
	private JButton ok, reset, setR, setD, removeD, save, load, next, prev, checkAll;
	private JScrollPane sp, treeView;
	private JTree tree;
	private String direct = "Current directory: /";
	public ImageIcon iconLogo = new ImageIcon("stevens.gif");
	public direc DIRECTORY;
	boolean isSend = false;
	public List<String> output = new ArrayList<String>();
	DefaultTreeModel model = new DefaultTreeModel(null);
	public int total = 0;
	public int now = 0;
	public boolean[] isDecom;
	public String fileRoot;
	
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode f = null;
        f = new DefaultMutableTreeNode("Allows all files");
        top.add(f);
    }
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
    DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

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
    
    private static Element createTree(Document doc, TreeModel model, DefaultMutableTreeNode node) {
        Element el = doc.createElement(node.toString());
        for(int i = 0; i < model.getChildCount(node); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) model.getChild(node, i);
            el.appendChild(createTree(doc, model, child));
        }
        return el;
    }
    
    public void checkAndSend(int I) {
    	try {
			if(I != 0) {
				process.setText(process.getText() + "Unziping " + info.get(I).hw + "...\n");
				Z = new ZipFile(info.get(I).stuID, info.get(I).name, info.get(I).email, info.get(I).hw, fileRoot);
				process.setText(process.getText() + "Done!\n");
			}
			getFiles G = new getFiles(Z);
			G.checkRules(G.RealDirectory, DIRECTORY);
			for(int i = 0; i < G.console.size(); i++) {
				process.setText(process.getText() + G.console.get(i) + "\n");
			}
			if(G.istrue == false) {
				// use thread to update GUI while in progress
				process.setText(process.getText() + "Sending email to " + info.get(I).name + ", please wait...\n");
				SwingWorker<List<String>, Object> worker = new SwingWorker<List<String>, Object>() {
			    @Override
			    protected List<String> doInBackground() throws Exception {
			        return G.console; // call a REST API
			    }
			    @Override
			    protected void done() {
			        try {
			        	int ii = I;
			        	Z.sendEmail(Z.writeEmail(get()));
			            //Z.sendEmail(Z.writeEmail(G.console));
				        process.setText(process.getText() + "Email has been sent successfully!\n");
				        ii ++;
				        if(ii < total) {
				        	checkAndSend(ii);
				        }
			        } catch (Exception e) {
			            //ignore
			        }
			    }
			};      
			worker.execute();
		}
		else {
			process.setText(process.getText() + "Nothing wrong with the rules!\n");
			int ii = I;
			ii++;
			if(ii < total) {
				checkAndSend(ii);
			}
		}
	} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public void visit(Node child,DefaultMutableTreeNode parent){
        short type = child.getNodeType();
        if(type == Node.ELEMENT_NODE){
            Element e = (Element)child;
            DefaultMutableTreeNode node = new DefaultMutableTreeNode
                                        (e.getTagName());
            parent.add(node);

            if(e.hasChildNodes()){
                NodeList list = e.getChildNodes();
                for(int i=0;i<list.getLength();i++){
                    visit(list.item(i),node);
                }
            }

        }else if(type == Node.TEXT_NODE){
            Text t = (Text)child;
            String textContent = t.getTextContent();
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(
                    textContent);
            parent.add(node);
        }
    }
    
    protected static void parseTreeNode(DefaultMutableTreeNode treeNode, Document doc) {

        String value = treeNode.getUserObject().toString();
        Element rootElement = doc.createElement("Library");
        doc.appendChild(rootElement);

        Enumeration kiddies = treeNode.children();
        while (kiddies.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) kiddies.nextElement();
            parseTreeNode(child, rootElement);
        }

}
    
    private static void parseTreeNode(DefaultMutableTreeNode treeNode, Element doc) {
    	String value = treeNode.toString();
    	Element parentElement = null;
    	parentElement = doc.getOwnerDocument().createElement("catagory");

        //BookCatagory book = (BookCatagory) value;
        // Apply properties to root element...
        Attr attrName = doc.getOwnerDocument().createAttribute("name");
        attrName.setNodeValue(value);
        parentElement.getAttributes().setNamedItem(attrName);
        doc.appendChild(parentElement);
        Enumeration kiddies = treeNode.children();
        while (kiddies.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) kiddies.nextElement();
            parseTreeNode(child, parentElement);
        }

    }
    
	public ruleGUI(String s) throws Exception {
		super(s);
		process = new JTextArea();
		process.setEditable(false);
		musthave = new JLabel("Must have: ");
		mustnot = new JLabel("Must not have: ");
		directory = new JLabel(direct, SwingConstants.CENTER);
		must_t = new JTextField();
		mustnot_t = new JTextField();
		directories = new JTextField();
		setD = new JButton("add new directory");
		removeD = new JButton("remove directory");
		save = new JButton("save rules");
		load = new JButton("load rules");
		next = new JButton("NEXT ☞");
		prev = new JButton("☜ PREVIOUS");
		image = new JLabel();
		//image.setIcon(iconLogo);
		checkAll = new JButton("Check All");
		fileRoot = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/Final Project/ZIPRULE/src/Download";
		try {
			FileInputStream fis = new FileInputStream(fileRoot + "/studentInfo.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			info = (ArrayList<StudentInfo>) ois.readObject();
			total = info.size();
			ois.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		title = new JLabel("ZIP FILE RULES", SwingConstants.CENTER);
		stuID = new JLabel("\t\tStudent CWID: \t\t" + info.get(0).stuID, SwingConstants.LEFT);
		stuName = new JLabel("\t\tStudent Name: \t\t" + info.get(0).name, SwingConstants.LEFT);
		stuEmail = new JLabel("\t\tStudent Email: \t\t" + info.get(0).email, SwingConstants.LEFT);
		hwName = new JLabel("\tHomework: " + "example homework", SwingConstants.CENTER);	// edit later...
		ddd = new JLabel("Directory", SwingConstants.CENTER);
		process.setText(process.getText() + "Unziping " + info.get(0).hw + "...\n");
		Z = new ZipFile(info.get(0).stuID, info.get(0).name, info.get(0).email, info.get(0).hw, fileRoot);
		process.setText(process.getText() + "Done!\n");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		String folderName = Z.hw.getName();	// get the folder name
		int pos = folderName.lastIndexOf(".");
		if (pos > 0) {
		    folderName = folderName.substring(0, pos);
		}
		
		DIRECTORY = new direc(folderName);
		
		DefaultMutableTreeNode top =
		        new DefaultMutableTreeNode("Root Directory");
		rootNode = top;
		treeModel = new DefaultTreeModel(rootNode);
		createNodes(rootNode);
		tree = new JTree(rootNode);
		model = (DefaultTreeModel) tree.getModel();
		sp = new JScrollPane(process);
		sp.setBounds(10,60,780,400);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		treeView = new JScrollPane(tree);
		treeView.setBounds(10,60,780,500);
		treeView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ok = new JButton("CHECK");
		reset = new JButton("RESET");
		setR = new JButton("SET");
		
		checkAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				checkAndSend(i);
			}
		});
		
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(now < total) {
					now ++;
					try {
						Z = new ZipFile(info.get(now).stuID, info.get(now).name, info.get(now).email, info.get(now).hw, fileRoot);
						//System.out.println(Z.stuName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					stuName.setText("Student Name: " + info.get(now).name);
					stuID.setText("Student CWID: " + info.get(now).stuID);
					stuEmail.setText("Student Email: " + info.get(now).email);
				}
				else {
					process.setText(process.getText() + "This is the last student!\n\n");
				}
			}
		});
		
		prev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(now > 0) {
					now --;
					try {
						Z = new ZipFile(info.get(now).stuID, info.get(now).name, info.get(now).email, info.get(now).hw, fileRoot);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					stuName.setText("Student Name: " + info.get(now).name);
					stuID.setText("Student CWID: " + info.get(now).stuID);
					stuEmail.setText("Student Email: " + info.get(now).email);
				}
				else {
					process.setText(process.getText() + "This is the first student!\n\n");
				}
			}
		});
		
		setD.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String D = directories.getText();
				if(D.contains("/")) {
					
					String[] sb = D.split("/");
					int length = sb.length;
					DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		                       tree.getLastSelectedPathComponent();
					if(node.isLeaf() == false) {
					}
					else {
						node = (DefaultMutableTreeNode) node.getParent();
					}
					DefaultMutableTreeNode child = (DefaultMutableTreeNode)
							node.getFirstChild();
					List<String> list = new ArrayList<String>();
					for(int i = 0; i < length; i++) {
						if(sb[i].equals("")) {
							i++;
						}
						boolean isExist = false;
						//System.out.println(child);
						if(child.toString().equals(sb[i]) == true) {
							//process.setText(process.getText() + "Directory already exist!\n");
							isExist = true;
						}
						while(child.getNextSibling() != null) {
							child = (DefaultMutableTreeNode)
									child.getNextSibling();
							//System.out.println(child);
							if(child.toString().equals(sb[i]) == true) {
								//process.setText(process.getText() + "Directory already exist!\n");
								isExist = true;
							}
						}
						if(isExist == false) {
							DefaultMutableTreeNode category = null;
							DefaultMutableTreeNode f = null;
					        category = new DefaultMutableTreeNode(sb[i]);
							node.add(category);
							f = new DefaultMutableTreeNode("Allows all files");
					        category.add(f);
					        //addObject(category);
					        //DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
					        model.reload(node);
						}
						for(int ii = 0; ii < node.getChildCount(); ii++) {
							if(node.getChildAt(ii).toString().equals(sb[i])) {
								node = (DefaultMutableTreeNode) node.getChildAt(ii);
								child = (DefaultMutableTreeNode) node.getFirstChild();
								break;
							}
						}
						
					}
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
				else {
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
				}
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
				must_t.setText("");
				mustnot_t.setText("");
				directories.setText("");
			}
		});
		
		
		
		
		
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
				List<String> list = new ArrayList<String>();
				DefaultMutableTreeNode temp = node;
				list.add(node.toString());
				while((DefaultMutableTreeNode)temp.getParent() != null) {
					list.add(((DefaultMutableTreeNode)temp.getParent()).toString());
					temp = (DefaultMutableTreeNode)temp.getParent();
				}
				direc d = DIRECTORY;
				
				
				for(int i = list.size()-2; i >= 0; i--) {
					for(int ii = 0; ii < d.d.size(); ii++) {
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
				rootNode.removeAllChildren();
		        DefaultMutableTreeNode f = null;
		        f = new DefaultMutableTreeNode("Allows all files");
		        rootNode.add(f);
		        model.reload();
				DIRECTORY = new direc(str);
				must_t.setText("");
				mustnot_t.setText("");
				directories.setText("");
			}
		});
		
		
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					getFiles G = new getFiles(Z);
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
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// in progress...
		save.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser();
	    		FileNameExtensionFilter filter = new FileNameExtensionFilter("Serial File", "ser", "xml");
	    		chooser.setFileFilter(filter);
	            chooser.setCurrentDirectory(new File("."));
	            chooser.setMultiSelectionEnabled(false);
	            chooser.setApproveButtonText("SAVE");
	            String fname = null;
	            int result=chooser.showSaveDialog(null);
	            if(result==JFileChooser.APPROVE_OPTION){
	            	File f = chooser.getSelectedFile();
	            	fname = chooser.getName(f); 
	            	try {
	            		
	            		FileOutputStream fos = new FileOutputStream(fname + ".ser");
	            		ObjectOutputStream oos = new ObjectOutputStream(fos);
	            		oos.writeObject(DIRECTORY);
	            		oos.close();
	            		
	            		/*
	            		FileOutputStream fos2;
	            		if(fname.contains(".xml") == true) {
	            			fos2 = new FileOutputStream(fname);
	            		}
	            		else {
	            			fos2 = new FileOutputStream(fname + ".xml");
	            		}
	            		XMLEncoder xe = new XMLEncoder(fos2);
	            		//xe.writeObject(DIRECTORY);
	            		xe.writeObject(rootNode);
	            		xe.close();*/
	            		
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
			}
		});
		
		load.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	    		JFileChooser chooser=new JFileChooser();
	    		FileNameExtensionFilter filter = new FileNameExtensionFilter("Serial File", "ser", "xml");
	    		chooser.setFileFilter(filter);
	            chooser.setCurrentDirectory(new File("."));
	            chooser.setMultiSelectionEnabled(false);
	            int result=chooser.showOpenDialog(null);
	            String fileName = "";
	            if(result==JFileChooser.APPROVE_OPTION)
	            {          
	                File _file=chooser.getSelectedFile(); 
	                fileName = _file.getName();
	            }
	            try {
	            	FileInputStream fis = new FileInputStream(fileName);
	            	ObjectInputStream ois = new ObjectInputStream(fis);
	            	DIRECTORY = (direc) ois.readObject();
	            	ois.close();
	            	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root Directory");
	            	direcToJTree dj = new direcToJTree(DIRECTORY, root);
	            	dj.convert(DIRECTORY, root);
	            	rootNode = root;
	            	model.setRoot(rootNode);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            
	        }
	    });
		
		JPanel pane1 = new JPanel(new GridBagLayout());
		JPanel pane2 = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		pane1.add(title,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 4;
		c.ipadx = 150;
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("stevens.gif").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
		image.setIcon(imageIcon);
		pane1.add(image,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = 15;
		c.gridwidth = 2;
		pane1.add(stuName,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		c.ipady = 15;
		c.gridwidth = 2;
		pane1.add(stuID,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.ipady = 15;
		c.gridwidth = 2;
		pane1.add(stuEmail,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 4;
		c.ipady = 15;
		pane1.add(prev,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 4;
		c.ipady = 15;
		pane1.add(next,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.gridheight = 7;
		c.ipadx = 120;
		pane1.add(treeView,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 2;
		c.ipady = 15;
		pane1.add(ddd,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 2;
		c.ipady = 15;
		pane1.add(directories,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 7;
		c.ipady = 15;
		pane1.add(setD,c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 7;
		c.ipady = 15;
		pane1.add(removeD, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 8;
		c.ipady = 15;
		pane1.add(musthave, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 9;
		c.ipady = 15;
		pane1.add(mustnot, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 8;
		c.ipady = 15;
		pane1.add(must_t, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 9;
		c.ipady = 15;
		pane1.add(mustnot_t, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 10;
		c.ipady = 15;
		pane1.add(setR, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 10;
		c.ipady = 15;
		pane1.add(reset, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 11;
		c.ipady = 15;
		pane1.add(ok, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 11;
		c.ipady = 15;
		pane1.add(checkAll, c);
		c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 12;
		c.ipadx = 30;
		c.ipady = 15;
		c.gridwidth = 2;
		pane1.add(save, c);
		c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 12;
		c.ipadx = 30;
		c.ipady = 15;
		c.gridwidth = 2;
		pane1.add(load, c);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 13;
		c.ipady = 200;
		c.gridwidth = 4;
		pane1.add(sp, c);
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		pane1.add(directory);
		*/
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 3;
		/*
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
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.ipady = 100;
		pane1.add(checkAll, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 7;
		c.ipady = 100;
		pane1.add(prev, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 7;
		c.ipady = 100;
		pane1.add(next, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 8;
		c.ipady = 100;
		pane1.add(save, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 8;
		c.ipady = 100;
		pane1.add(load, c);*/
		
		getContentPane().add(pane1);
		setSize(600,750);
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

