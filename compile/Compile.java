package com.company;

import java.awt.*;
import java.io.*;
import java.util.*;
// import java.awt.TextField;
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
import javax.swing.*;

public class Compile extends JFrame {
    protected TextArea rstDis, display;
    private JTextArea comArea;
    private JButton run, graSave, comSave;
    protected JTextField gra;
    private JLabel graLab, comLab, rstLab;
    private JPanel gradArea, comPanel, rstPanel, panel, disPanel, runPanel, pane3;
    private MenuBar bar;       // Menu bar
    private Menu fileMenu;
    private MenuItem openItem, nextItem, closeItem;
    private FileDialog openDia, nextDia;
    private File file;
    private String dirpath, fileName;

    Compile() {

        rstDis = new TextArea();
        // run button
        run = new JButton("run");
        runPanel = new JPanel();
        runPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // c.fill = GridBagConstraints.NONE;
        // c.gridwidth = 2;
        // c.gridheight = 2;
        // c.gridx = 1;
        // c.gridy = 1;
        // runPanel.add(run, c);


        // grade area
        graLab = new JLabel("Grade");
        gra = new JTextField("", 3);
        graSave = new JButton("Save Grade");
        // gradArea = new JPanel();
        // gradArea.setLayout(new GridBagLayout());
        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.gridwidth = 1;
        // c.weightx = 0;
        // c.gridx = 0;
        // c.gridy = 0;
        // gradArea.add(graLab, c);

        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.NONE;
        // c.gridwidth = 2;
        // c.gridx = 1;
        // c.gridy = 2;
        // gradArea.add(gra, c);

        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.NONE;
        // // c.weightx = 1.0;
        // c.gridwidth = 2;
        // c.gridx = 1;
        // c.gridy = 4;
        // gradArea.add(graSave, c);

        // comment area
        comLab = new JLabel("Comment ");
        comArea = new JTextArea("");
        comArea.setLineWrap(true);
        comSave = new JButton("Save Comment");
        // comPanel = new JPanel();
        // comPanel.setLayout(new GridBagLayout());
        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.weightx = 0.5;
        // c.gridwidth = 1;
        // c.gridx = 0;
        // c.gridy = 0;
        // comPanel.add(comLab, c);

        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.NONE;
        // c.gridwidth = 2;
        // // c.gridheight = 2;
        // c.gridx = 0;
        // c.gridy = 1;
        // comPanel.add(comArea, c);

        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.NONE;
        // c.gridwidth = 1;
        // c.gridx = 0;
        // c.gridy = 4;
        // comPanel.add(comSave, c);

        // pane3 = new JPanel();
        // pane3.setLayout(new GridLayout(3, 1));
        // pane3.add(runPanel);
        // pane3.add(gradArea);
        // pane3.add(comPanel);


        // menu bar
        bar = new MenuBar(); //
        fileMenu = new Menu("file");// Initial menu
        openItem = new MenuItem("open");// create open item
        nextItem = new MenuItem("next");// create next item
        closeItem = new MenuItem("quit");
        fileMenu.add(openItem);
        fileMenu.add(nextItem);
        fileMenu.add(closeItem);
        bar.add(fileMenu);
        this.setMenuBar(bar);

        display = new TextArea();
        rstLab = new JLabel("Result");
        // rstPanel = new JPanel();
        // rstPanel.setLayout(new GridBagLayout());
        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.gridwidth = 1;
        // // c.gridheight = 2;
        // c.gridx = 0;
        // c.gridy = 0;
        // rstPanel.add(rstLab, c);

        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.gridwidth = 2;
        // c.gridx = 0;
        // c.gridy = 2;
        // rstPanel.add(rstDis, c);

        // disPanel = new JPanel();
        // disPanel.setLayout(new BorderLayout());
        // disPanel.add(rstPanel, BorderLayout.SOUTH);
        // disPanel.add(display, BorderLayout.CENTER);

        openDia = new FileDialog(this, "open", FileDialog.LOAD);
        nextDia = new FileDialog(this, "save", FileDialog.SAVE);

        this.setTitle("Automatic HomeWork");
        this.setBounds(300, 100, 700, 600);
        this.setLayout(new GridBagLayout());

        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.NONE;
        // c.gridx = 0;
        // c.gridy = 0;
        // c.gridwidth = 8;
        // // c.gridheight = 7;
        // this.add(disPanel, c);

        // c = new GridBagConstraints();
        // c.fill = GridBagConstraints.NONE;
        // c.gridx = 8;
        // c.gridy = 0;
        // c.gridwidth = 2;
        // // c.gridheight = 7;
        // this.add(pane3, c);

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


    private void myEvent() {

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                openDia.setVisible(true);

                dirpath = openDia.getDirectory();
                fileName = openDia.getFile();
                // System.out.println(dirpath);


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
                    }
                    bufr.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                run.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
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

                graSave.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String ss = gra.getText();
                        grade(ss, dirpath);
                    }
                });

                comSave.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String ss = comArea.getText();
                        comment(ss, dirpath);
                    }
                });

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
                        file = new File(dirpath, fileName);
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
