
Final codes are in the folder final_src, and the files outside show the process how we achieve our project.


----------------------------------------------------
Instructions: 
	After adding the shortcut, press "ctrl + O" to open a file, and the code will be displayed onto the TextArea;
	Press "run" button, the result will be displayed onto result display, and the score will generate simultaneously.
		if the code cannot be compiled, it will be graded 25;
		if it can compil but can't run, 50 will be graded;
		if it can run well, then it get 90 score;
		if its result is correct, please modify the score to 100;
	Press the "Save Grade" button, a "grade.txt" file will be generated into the directory of the code;
	Add comments and press "Save Comment", a comment file will be there.
	
Notice that, the "grade.txt" and "comment.txt" is in the same directory with the code file that you want to grade.

----------------------------------------------------

The goal of our project :

		Open assignment and display source code.

		Compile and run the code

		Allow the grader to enter comments and grade.
 
The components of our project :

There are Seven classes in our project: Gui, Code, CodeCpp, CodeJava, CodePy, Grade, Comment.
 
Gui : This class design the user interface. The components on the interface include:

Menu Bar: include a “Open” item to open a file, “Save” item to save file, “Quit” item to quit, and all of them have shortcut 

 	  for convenience.

Code display: a TextArea to display the original code of the program which the user choose.

Result display：a TextArea to display the result captured from terminal when compiling and running the code.

Button "run": is used after the user open a program, by clicking it, the result display will be activated and the default 

	      score will be displayed onto the grade area.

Grade Area: a TextField showing the default grade of the program and could be entered if the TA wants to modify the grade. 

"Save Grade" button is used to save the score to grade.txt.

Comment Area: a JTextArea allow the TA to input some comments and the "Save Comment" button to save it to comment.txt.
 
Code :
This is an abstract class to be extended, it provide an abstract method: compile().

The abstract method declares how to compile and run a program. There are three member variables in this class: “path” of 

String type, “fileName” of String type and “file” of File type. The path variable represents the program’s absolute path 

except it’s expanded-name. The “fileName” variable represents the program’s name. The “file” variable is used for program’s 

deposit.
 
CodeCpp & CodeJava :
This subclass inherits the abstract class Code. It is use for compiling and running a C++ or Java  program respectively.

It inherits abstract class’s three variables. String path and fileName is the directory and file’s name of the program that 

you want to grade. 

In its own compile() method, we use ProcessBuilder to create a terminal and set the current directory where the code to be 

graded is in as its working directory. The running result of the program is captured and displayed onto the result TextArea in 

GUI. 

We will set a default grade by capturing the exit value of terminal twice for compile and run respectively: if compile return 

1, which means the program fails to compile and grade will set to 25 by default; if compile return 0, while run return 1, 

which means it compile well but fails to run and the grade will set to 50 by default; if both return 0, then the grade will 

set to 100; The method “peelF” is used to get the prefix of file’s name, which means the string before '.', for instance, 

"Test.cpp" will return "Test".
 
CodePy:
CodePy Class is the same as the previous two classes, inheriting from Code class, except the default grade rule. 

Resulting from the python code does not need to compile it before running it, we decide skip the 25 part, that means  when 

capturing the exit value from terminal: 0 means this program run well and it’s grade set to 100 by default, while 1 means the 

program fails to run and it will set to 50 by default.  

Grade :
This class is to generate a grade.txt file into the directory where the code that you want to grade located in. 

There are two variables, “str” of String type represents the score and “path” of String type represents the directory of the 

code that you want to grade. We use BufferedWriter to write the grade into the grade.txt file.
 
Comment :
This class is to generate a comment.txt file into the directory where the code that you want to grade located in. 

There are two variables, “str” of String type represents the comment added into the file, and it comes from the TextArea in

the GUI part. Path of String type represents the directory of the code that you want to grade. We use BufferedWriter to write 

the comment text into the comment.txt file.
------------------------------------------------------
Steps to run the code :

1. click the file menu bar and select the open button :

2. choose a program file from the file manager.
 
3. display the source code in the “Code Display”.
 
4. click the “run” button and the it calls the CodeJava, CodeCpp, CodePy according to the program type. After running it will 

   set a default grade in “Grade Area”.

	If fail to compile, it will show the error location in “Result Area” and set a default grade.
 	
	If the user open an not qualified file it could not run.

5. the user could modify the grade in the “Grade Area” and add comment in the “Comment Area”. By the way, users can add 

   comment in the original code. Save the grade and comment by click the “Save Grade” button and “Save Comment” button.
 
   The grade.txt and comment.txt had been added to the original file folder.
 
6. save the original code if the user had added comment in the original Code and then click the open button to open another

   assignment.

END.


