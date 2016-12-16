package edu.stevens.canvas.zip;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	/* Please change the hw to your own zip file */
	public File hw = new File(
			"/Users/Steveisno1/Documents/16-17Fall/EE810Java/" + "Final Project/ZIPRULE/src/sample files.zip");
	public int stuID = 0; // get student information
	public String stuName = null;
	public String stuEmail = null;
	/* Please change the path to your own file path */
	public String inPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/"
			+ "Final Project/ZIPRULE/src/sample files.zip";
	public String outPath = "/Users/Steveisno1/Documents/16-17Fall/EE810Java/" + "Final Project/ZIPRULE/src";
	
	public Email(int id, String nm, String em, String hw, String fileRoot) {
		/* These information are from other groups... */
		stuID = id;
		stuName = nm;
		stuEmail = em;
		inPath = fileRoot + "/" + hw + ".zip";
		outPath = fileRoot;
		this.hw = new File(inPath);
	}
	
	public String writeEmail(List<String> wrong) {
		String text;
		text = "Dear " + stuName + ":\n\nYou get the rules wrong for your homework.\n\n"
				+ "Here are the files that are not allowed:\n";
		for (int i = 0; i < wrong.size(); i++) {
			text = text + wrong.get(i) + "\n";
		}
		text = text + "\nPlease resubmit the homework!\nThank you!\n\nCanvas system\n";
		String date = new SimpleDateFormat("MM/dd/yyyy   HH:mm:ss").format(Calendar.getInstance().getTime());
		text = text + date;
		return text;
	}

	/* send email to student */
	public void sendEmail(String text) {
		Properties p = new Properties();
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.port", "587");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mycanvasmanager@gmail.com", "donttellothers");
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mycanvasmanager@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(stuEmail));
			message.setSubject("Wrong homework zip file");
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}