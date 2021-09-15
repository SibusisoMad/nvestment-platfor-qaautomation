// <copyright file="SendMail.java" company="Atom Consulting Services Ltd">
// Copyright (c) Atom Consulting Services Ltd. All rights reserved.
// </copyright>
package src.com.pack.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.IOUtils;



public class SendMail {
	public static int dropboxerrorcount=0;

	/**
	 * Email Configuration with outlook 365. Just keep to SMTP port to 587 for 365. SMTP detail need to be configured according to requirement
	 * the method details
	 */
public static void execute( String reportFileName1, String reportFileName,  String EmailPassword, String EmailUser, String ExeMode,int email_failed,int email_pass) throws Exception {
	final String username = EmailUser;
	final String password = EmailPassword;
	
	//String username1= "postmaster@sandbox0f3bb7411bdf4f44a978777a81f2460c.mailgun.org";
	//String password1="8c686adc3227d700812991bdf82cf5ec-07bc7b05-74f2df82";
	Properties props = new Properties();
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.host", "outlook.office365.com");
	props.put("mail.smtp.port", "587");
	
	//props.put("mail.smtp.starttls.enable", "true");
	//props.put("mail.smtp.auth", "true");
	//props.put("mail.smtp.host", "smtp.mailgun.org");
	//props.put("mail.smtp.port", "587");
   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
     protected PasswordAuthentication getPasswordAuthentication() {
 //  	 System.out.println(username+""+password);
      return new PasswordAuthentication(username, password);   }  });
  
    
   try {
	   System.out.println("Sending...");
	   MimeMessage message = new MimeMessage(session);
	   message.setFrom(new InternetAddress("rajeev@atomcto.com"));

	   message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ricky@atomcto.com"));
	   //	message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("rajeev@atomcto.com"));  
	   String EmailSubject= "Report: "+ email_failed+ "-Failed/"+email_pass+"-Passed" ;
	   message.setSubject(EmailSubject);
	   Thread.sleep(2000);
	   String basePath = new File("").getAbsolutePath();
	   String path = new File("test-output\\").getAbsolutePath();
	   String file = path;
	   String fileName = reportFileName1;
	   String finalpath =file + fileName;
	   StringWriter writer = new StringWriter();
	   Multipart multipart = new MimeMultipart();
	   BodyPart htmlBodyPart = new MimeBodyPart();
	   IOUtils.copy(new FileInputStream(new File(finalpath)), writer);
	   htmlBodyPart.setContent(writer.toString(), "text/html");
	   multipart.addBodyPart(htmlBodyPart);
	   String filename = reportFileName;
 	   addAttachment(multipart, filename);
	   Thread.sleep(1000);
       message.setContent(multipart); 
       Transport.send(message);
	   System.out.println("Email Sent.....");
   		} catch (MessagingException e) {
   			throw new RuntimeException(e);
   		}
   		
   	 
 }
/**
 * Code for report attachment in email
 */
	private static void addAttachment(Multipart multipart, String filename) throws Exception
	{
		String basePath1 = new File("").getAbsolutePath();
		String path1 = new File("test-output\\").getAbsolutePath();
		String file1 = path1;
	    DataSource source = new FileDataSource(file1+filename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(filename);
	    multipart.addBodyPart(messageBodyPart);
	}
  
}
