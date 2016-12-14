import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
// import javax.swing.RadioButton;
// import javax.swing.ButtonArea;

class MyDemo extends JFrame { 
	private MenuBar bar;       // Menu bar
	private TextArea display;  // 
	private Menu fileMenu;
	private MenuItem openItem, nextItem, closeItem;
	private FileDialog openDia, nextDia;
	private File file;

	MyDemo() {
		this.setTitle("Automatic HomeWork");
		this.setBounds(300, 100, 650, 600);

		bar = new MenuBar(); // 
		display = new TextArea();

		fileMenu = new Menu("file");// Initial menu

		openItem = new MenuItem("open");// create open item
		nextItem = new MenuItem("next");// create next item
		closeItem = new MenuItem("quit");// create close item

		fileMenu.add(openItem);
		fileMenu.add(nextItem);
		fileMenu.add(closeItem);

		bar.add(fileMenu);

		this.setMenuBar(bar);

		openDia = new FileDialog(this, "open", FileDialog.LOAD);
		nextDia = new FileDialog(this, "save", FileDialog.SAVE);

		this.add(display);
		myEvent();

		this.setVisible(true);
	}

	private void myEvent() {
        
        // open event listener
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                openDia.setVisible(true);
                
                String dirpath = openDia.getDirectory();
                String fileName = openDia.getFile();
                
                if (dirpath == null || fileName == null)
                    return;
                else
                    display.setText(null);
                file = new File(dirpath, fileName);

                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = bufr.readLine()) != null) {
                	display.append(line + "\r\n");
                    bufr.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        });
        
        // next event listen;
        nextItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    nextDia.setVisible(true);
                    String dirpath = nextDia.getDirectory();
                    String fileName = nextDia.getFile();
                    
                    if (dirpath == null || fileName == null)
                        return;
                    else
                        file=new File(dirpath,fileName);
                }
                    try {
                        BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
                        
                        String text = display.getText();
                        bufw.write(text);
                        
                        bufw.close();
                    } catch (IOException e1) {
                        
                        e1.printStackTrace();
                    }
                

            }

        });
        
        closeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

        });
    }

    public static void main(String[] args) {
        new MyMenuDemo();
    }

	// JPanel p1 = new JPanel();
	// JPanel p2 = new JPanel();
	// JPanel p3 = new JPanel();

	// JLabel l = new JLabel("Comment：");

	// JTextField f = new JTextField();
	// JButton b = new JButton("Save");

	// JLabel gradeL = new JLabel("Grade");
	// JRadioButton g_50, g_80, g_100;
	// ButtonGroup g_area;
	// JTextField g = new JTextField("", 2);
	// JButton gSave = new JButton("Save Grade");

// public TextArea() {
// 	this.setLayout(new BorderLayout());
// 	p1.setLayout(new GridLayout(3, 2));
// 	p1.add(l);
// 	p1.add(f);
// 	p1.add(b);

// 	p2.setLayout(new GridLayout(3,1));
// 	p2.add(gradeL);
// 	// g_area.add(g_50); g_area.add(g_80); g_area.add(g_100);
// 	// p3.add(g_50); p3.add(g_80);p3.add(g_100); 
// 	p3.add(g);
// 	p2.add(p3);
// 	p2.add(gSave);
// 	this.add(p1, BorderLayout.NORTH);
// 	// p2.add(g, BorderLayout.CENTER);
// 	this.add(p2, BorderLayout.CENTER);

// 	this.setSize(500, 500);
// 	this.setVisible(true);
// 	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// 	b.addActionListener(new ActionListener() {
// 		public void actionPerformed(ActionEvent e) {
// 			String str =f.getText();
// 			run(str, "comment.txt");
// 		}
// 	});

// 	gSave.addActionListener(new ActionListener() {
// 		public void actionPerformed(ActionEvent a) {
// 			String gra = g.getText();
// 			run(gra, "grade.txt");
// 		}
// 	});

// }

// public void run(String str, String path) {
// 	String file = path;//输出文件路径
//         try {
// 		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
// 		String str1 = str ;
// 		bw.write(str1);
// 		bw.newLine();
//    		bw.flush();
//    		bw.close();

// 	} catch (IOException e) {
// 		e.printStackTrace();
// 	}

// }
	


// public static void main (String[] args) {
// 	try {
// 		TextArea a = new TextArea();
// 	}
// 	catch (Exception e) {
// 		System.out.println("Error");
// 	}
// }

}