package com.isa.ISA.service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	private String to;
	private final String from="piginco@gmail.com";
	private final String host = "localhost";

	@Async
	public void regEmail(String to) throws InterruptedException {
		System.out.println("Sleeping now...");
		Thread.sleep(100);

		System.out.println("Sending email...");
		try{
			String host ="smtp.gmail.com" ;
	        String user = "piginco@gmail.com";
	        String pass = "pigincoNTM10";
	        String from = "piginco@gmail.com";
	        String subject = "Activate your account";
	        String messageText = "Please go to following link to activate your account: http://localhost:8096/api/register/" + to;
	        boolean sessionDebug = false;
	
	        Properties props = System.getProperties();
	
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.required", "true");
	
	        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        Session mailSession = Session.getDefaultInstance(props, null);
	        mailSession.setDebug(sessionDebug);
	        Message msg = new MimeMessage(mailSession);
	        msg.setFrom(new InternetAddress(from));
	        InternetAddress[] address = {new InternetAddress(to)};
	        msg.setRecipients(Message.RecipientType.TO, address);
	        msg.setSubject(subject); msg.setSentDate(new Date());
	        msg.setText(messageText);
	
	       Transport transport=mailSession.getTransport("smtp");
	       transport.connect(host, user, pass);
	       transport.sendMessage(msg, msg.getAllRecipients());
	       transport.close();
	       System.out.println("message send successfully");
	    }catch(Exception ex)
	    {
	        System.out.println(ex);
	    }

	}
	@Async
	public void inviteEmail(String to, String sub, String mess, Long rezID) throws InterruptedException {
		System.out.println("Sleeping now...");
		Thread.sleep(100);

		System.out.println("Sending email...");
		try {
			String host = "smtp.gmail.com";
			String user = "piginco@gmail.com";
			String pass = "pigincoNTM10";
			String from = "piginco@gmail.com";
			String subject = sub;
			String messageText = mess;
			boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");

			java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(messageText);

			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			System.out.println("message send successfully");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
