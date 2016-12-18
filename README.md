#Zip File Rules
Group members: 
Peiying Cao, Shenwei Chen, Zhaolun Song

1. Project Statement
Canvas has major problems.  While we may not notice as a student, it is incredible difficult to get the kind of behavior as teachers.  Lots of features will be implemented in a large project, with each team contributing their part towards a complete, integrated improvement of Canvas. And our group is focus on zip file part, trying to do some optimization based on present canvas features.

2. Objectives
We will make a function to specify the zip file rule, decompress the zip file which could be got from the Download Group. And according the specified the rules, Email Students automatically if they get the rules wrong, letting them re-enter the homework. After unzipped all the homework, output them in a file which could be available to other group.

3. Components
3.1 Classes
There are 9 classes in our project: decompress, direc, Email, file, getFiles, PassStudent, ruleGUI, StudentInfo, ZipFile. 
Here are the brief overview of each class, the core codes will be explained in next section.
3.1.1 decompress
This class works for decompression of the zip files.
3.1.2 direc
This class is a representation of the directory, including directory name, the sub-directories, the must-have files, the must-not-have files. Can be serialized to save and load the rules.
3.1.3 Email
This class works for writing email contents and sending email.
3.1.4 file
This class is a representation of the file, including file name, file extension, and some boolean values to represent the file’s status. Can be serialized to save and load the rules.
3.1.5 getFiles
This class works mainly on File IO, to get the folder and files after decompression and further process.
3.1.6 PassStudents
This class is a representation of the student information, including student name, id, email address and the homework path of the student. We can combine the homework folder with the student in this class. Can be serialized and used for Automatic Homework.
3.1.7 ruleGUI
This class presents a GUI to specify the rules, including many JLabels, JButtons, JTextAreas, etc.
3.1.8 StudentInfo
This class is a representation of the student information, ncluding student name, id, email address and the zip path of the student. We can combine the zip file with the student in this class. Can be serialized and the infomation from Download can be stored.
3.1.9 ZipFile
The main class to run the codes.

3.2 GUI introduction
We designed a GUI to realize the main functions of our projects.
The student information is displayed on the screen, and the user can change the student by clicking “NEXT” or “PREVIOUS” button.
The GUI has a visualization window for the rules, that means, the user can see the rule as folder icons and file icons.
Type the directory and add to the rule, also can remove directory from rule.
Type the specific rules and set the rules, also reset is available.
Check the student on the screen for his homework, and can also check all students together.
Save and Load rules by clicking “Save Rules” and “Load Rules” buttons.
The console window shows the progresses of what the user have done.

3.3 Decompression
We use ZipInputStream to get the zip file path, use BufferedInputStream to get the input zip file. For there may be a problem, if one zip file include another zip file, we use ZipEntry to judge if it’s a folder, in order to traverse all of the zip files.

3.4 Rules specify
3.4.1 Data structure we use
We write two Objects called direc and file to store all the directories and files under it. List<file> have stores the files that the rules say that must have, List<file> nothave stores the files that are not allowed. The two boolean values in class file is the status of the file, which will be explained later.

3.4.4 Set directory
We can read the String from the JTextField in the GUI. It allows user to input single directory or directory after “/”, e.g. src/edu/stevens/canvas. When input the single directory, we just add a node under the parent node we get. When input a bunch of directories, check whether each directory node is existed, and add when it’s not existed, one by one, from the start to the end.

3.4.5 Remove directory
Remove the node that is clicked in JTree. If it’s not a parent node, do nothing.

3.4.6 Set rules in directory
3.4.6.1 Files that are must have
We can read the String from the JTextField in the GUI. Multiple files are separated by “;”. The file name can have both name and extension, e.g. “main.java”, or just name but no extension, e.g. “readme.*”, or just extension, e.g. “*.class”, or missing both, e.g. “*.*”, stand for all types of files.
3.4.6.2 What are must not have
Read the String in the same way as above.

3.4.7 How to read the rule
Split the String by “;”, for each substring, specify it as a file Object, and check if it’s in the list in the direc, if not, add into the list. 

3.4.8 How to specify rule into file
Set rule is always inconvenient, especially for those extremely weird rules. We can serialize the direc Object and write into .ser file and read them. In this way we achieve a save-load method.
When the direc Object is loaded, use the same method mentioned above to update the JTree. 

3.4.9 How to check the file
In order not to write another Object, we just use the direc to represent the real directory also. However, in real directory there’s no must-not-have file, so we just leave the list as null and add all the files in List<file> have, and all the directories in List<direc> d. 
In order to avoid the contradiction of must have and must not have, i.e. must have *.class, and must not have *.class, we always check the must have first. If the file is passed the must-have check, we set the isCheck value in the file Object into true, so even if the must-not-have rule is contradictory, we can ignore it.
And we generate a String stating what’s wrong and add the String into a list called List<String> Console. This list stores all the wrong files statement, which will be used to be displayed in GUI and to send email.

3.5 Send email
3.5.1 How to send email
We use JavaMail API to send email. To send an email by JavaMail, we need to import javax.mail.jar in Eclipse, create a Properties Object, set email server host, protocol, port number into Properties. 
We create a gmail account named “mycanvasmanager@gmail.com” as our sending account. Then we create a Session Object, with sender’s email account information. At last, create a Message Object, set receivers’ email address, use Transport to send the email.
For the content of the email, we write a method that can return a String, which including the beginning and ending part of the content, and can add all of the statement in List<String> Console.
We use SimpleDateFormat to generate the current time and add to the content.

3.5.2 Send email in GUI
We want to send email after the button click, which means all the functions are in the JButton’s ActionListener. However, the SetText() method of JTextArea will be update only after all the functions completed, which will take a long time, for sending each email will cost about 10 seconds. So we use thread in Java Swing, which is called SwingWorker to avoid this, then the JTextArea can be updated when sending email, in other words, user can see the progress of sending email.
