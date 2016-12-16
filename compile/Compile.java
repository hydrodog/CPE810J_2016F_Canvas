package com.company;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
// import java.awt.TextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;

public class Compile extends JFrame {
    protected TextArea rstDis, display;
    private JTextArea comArea;
    private JButton run, graSave, comSave;
    protected JTextField gra;
    private JLabel graLab, comLab, rstLab;
    private JPanel gradArea, comPanel, rstPanel, panel, disPanel, runPanel, pane3;
    private MenuBar bar;
    private Menu fileMenu;
    private MenuItem openItem, saveItem, closeItem, nextItem;
    private FileDialog openDia, saveDia, nextDia;
    private File file;
    private String dirpath, fileName;

    Compile() {

        rstDis = new TextArea();
        // run button
        run = new JButton("run");
        runPanel = new JPanel();
        runPanel.setLayout(new GridBagLayout());

        // grade area
        graLab = new JLabel("Grade");
        gra = new JTextField("", 3);
        graSave = new JButton("Save Grade");

        // comment area
        comLab = new JLabel("Comment ");
        comArea = new JTextArea("");
        comArea.setLineWrap(true);
        comSave = new JButton("Save Comment");

        // menu bar
        bar = new MenuBar(); //
        fileMenu = new Menu("file");// Initial menu
        // fileMenu.setShortcut(new MenuShortcut(KeyEvent.VK_F));
        openItem = new MenuItem("open(O)");// create open item
        openItem.setShortcut(new MenuShortcut(KeyEvent.VK_O));   //set shortcut for OpenItem
        saveItem = new MenuItem("save");// create save item
        saveItem.setShortcut(new MenuShortcut(KeyEvent.VK_S));
        closeItem = new MenuItem("quit");
        closeItem.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
        nextItem = new MenuItem("next");
        nextItem.setShortcut(new MenuShortcut(KeyEvent.VK_N));
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(nextItem);
        fileMenu.add(closeItem);
        bar.add(fileMenu);
        this.setMenuBar(bar);

        display = new TextArea();
        rstLab = new JLabel("Result");

        openDia = new FileDialog(this, "open", FileDialog.LOAD);
        saveDia = new FileDialog(this, "save", FileDialog.SAVE);
        nextDia = new FileDialog(this, "next", FileDialog.LOAD);

        this.setTitle("Automatic HomeWork");
        this.setBounds(300, 100, 700, 600);
        this.setLayout(new GridBagLayout());

        // Add components into frame
        GridBagConstraints c = new GridBagConstraints();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 8;
        c.gridheight = 7;
        this.add(display, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 9;
        c.gridy = 1;
        c.gridwidth = 1;
        this.add(run, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 9;
        c.gridy = 4;
        c.gridwidth = 1;
        this.add(graLab, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 8;
        c.gridy = 5;
        c.gridwidth = 2;
        this.add(gra, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 9;
        c.gridy = 6;
        c.gridwidth = 1;
        this.add(graSave, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 9;
        c.gridy = 7;
        c.gridwidth = 1;
        this.add(comLab, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 9;
        c.gridy = 8;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(comArea, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 9;
        c.gridy = 9;
        c.gridwidth = 1;
        this.add(comSave, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        this.add(rstLab, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(rstDis, c);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myEvent();
    }

    // Event method for components
    private void myEvent() {
        // action of openItem;
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                openDia.setVisible(true);

                dirpath = openDia.getDirectory();
                fileName = openDia.getFile();

                if (dirpath == null || fileName == null)
                    return;
                else
                    display.setText(null);
                file = new File(dirpath, fileName);

                // Clear the result Display
                if (!"".equals(rstDis.getText())) {
                    rstDis.setText(null);
                }


                // Display the code file into TextArea
                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = bufr.readLine()) != null) {
                        display.append(line + "\r\n");
                    }
                    bufr.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                // Run button action
                run.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // compile the code and grade in the dirtory the code belongs to
                        if(get(dirpath + fileName).equals(".java")) {
                            CompileJava java = new CompileJava();
                            java.compile(dirpath, fileName, file);
                        } else if(get(dirpath + fileName).equals(".cpp")) {
                            CompileCpp cpp = new CompileCpp();
                            cpp.compile(dirpath, fileName, file);
                        } else if(get(dirpath + fileName).equals(".py")) {
                            CompilePython python = new CompilePython();
                            python.compile(dirpath, fileName, file);
                        } else {
                            rstDis.setText("Wrong file");
                        }
                    }
                });
                // Button to save the grade into the certain path
                graSave.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String ss = gra.getText();
                        grade(ss, dirpath);
                    }
                });
                // Button to save comments into the certain path
                comSave.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String ss = comArea.getText();
                        comment(ss, dirpath);
                    }
                });
            }
        });
        // next Item listener
        nextItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                nextDia.setVisible(true);

                dirpath = nextDia.getDirectory();
                fileName = nextDia.getFile();

                if (dirpath == null || fileName == null)
                    return;
                else
                    display.setText(null);
                file = new File(dirpath, fileName);

                // Clear the result Display
                if (!"".equals(rstDis.getText())) {
                    rstDis.setText(null);
                }


                // Display the code file into TextArea
                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = bufr.readLine()) != null) {
                        display.append(line + "\r\n");
                    }
                    bufr.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                // Run button action
                run.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // compile the code and grade in the dirtory the code belongs to
                        compile(dirpath, fileName, file);
                    }
                });
                // Button to save the grade into the certain path
                graSave.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String ss = gra.getText();
                        grade(ss, dirpath);
                    }
                });
                // Button to save comments into the certain path
                comSave.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String ss = comArea.getText();
                        comment(ss, dirpath);
                    }
                });
            }
        });

        // save event listen
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    saveDia.setVisible(true);
                    String dirpath = saveDia.getDirectory();
                    String fileName = saveDia.getFile();

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
        // Close event listener
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

    public void compile(String path, String fileName, File file){};

    public void grade(String str, String path) {
        String name = "grade.txt";
        File file = new File(path, name);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String str1 = str;
            bw.write(str1);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void comment(String str, String path) {
        String name = "comment.txt";
        File file = new File(path, name);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String line = str;
            bw.write(str);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return line;
    }

    public String peel(String str) {
        if (str == null)
            return str;
        int len = str.length();
        int i = str.indexOf('.');
        String rst = str.substring(0, i);
        return rst;
    }

    public String get(String str) {
        if (str == null)
            return str;
        int len = str.length();
        int i = str.indexOf('.');
        String rst = str.substring(i, len);
        return rst;
    }



    public static void main(String[] args) throws IOException { //this is the test code, we could run it successfully.
        Compile test = new Compile();
    }
}
