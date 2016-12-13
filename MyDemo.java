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
                
                String dirpath = openDia.getDirectory();//获取打开文件路径并保存到字符串中。
                String fileName = openDia.getFile();//获取打开文件名称并保存到字符串中
                
                if (dirpath == null || fileName == null)//判断路径和文件是否为空
                    return;
                else
                    display.setText(null);//文件不为空，清空原来文件内容。
                file = new File(dirpath, fileName);//创建新的路径和名称

                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(file));//尝试从文件中读东西
                    String line = null;//变量字符串初始化为空
                    while ((line = bufr.readLine()) != null) {
                	display.append(line + "\r\n");//显示每一行内容
                    }
                    bufr.close();//关闭文件
                } catch (FileNotFoundException e1) {
                    // 抛出文件路径找不到异常
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // 抛出IO异常
                    e1.printStackTrace();
                }

            }

        });
        
        // next event listen;
        nextItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    nextDia.setVisible(true);//显示保存文件对话框
                    String dirpath = nextDia.getDirectory();//获取保存文件路径并保存到字符串中。
                    String fileName = nextDia.getFile();////获取打保存文件名称并保存到字符串中
                    
                    if (dirpath == null || fileName == null)//判断路径和文件是否为空
                        return;//空操作
                    else
                        file=new File(dirpath,fileName);//文件不为空，新建一个路径和名称
                }
                    try {
                        BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
                        
                        String text = display.getText();//获取文本内容
                        bufw.write(text);//将获取文本内容写入到字符输出流
                        
                        bufw.close();//关闭文件
                    } catch (IOException e1) {
                        //抛出IO异常
                        e1.printStackTrace();
                    }
                

            }

        });
        
        // 退出菜单项监听
        closeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        
        // 窗体关闭监听
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