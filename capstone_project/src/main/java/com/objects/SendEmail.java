package com.objects;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SendEmail {
	
	public static void sendemail(String toEmail, String subject, String body, String filename) {
		final String fromEmail = "evelynpeh39@gmail.com"; //requires valid gmail id
		final String password = "pnix nntf tqdi qwsb"; // correct password for gmail id
		//final String toEmail = "pehtingyu@gmail.com"; // can be any email id 
		
		//System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		EmailUtil.sendAttachmentEmail(session, toEmail,subject, body, filename);
	}

}