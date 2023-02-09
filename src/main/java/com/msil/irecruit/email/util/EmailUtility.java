package com.msil.irecruit.email.util;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {
	
	static boolean flagForError=false;
	
	public static final boolean sendMail(final String to, final String from, final String cc, final String bcc,
			final String sub, final String mes, String smtp) {
		boolean flag=false;
		System.out.println("calllllllllllll.......................");
		ExecutorService executorService = Executors.newCachedThreadPool(); 
		  executorService.execute(new Runnable() {
		  @Override 
		  public void run() {
			  	try {
			  		flagForError= sendMailService(to, from, cc, bcc, sub, mes, smtp);
			  	} catch (NoSuchProviderException e) {
			  		e.printStackTrace();
			  	} 
		   	} //run
		  }); //execute()
		  executorService.shutdown();
		flag=flagForError;
		
		return flag;
	}
	
	private static final boolean sendMailService(final String to, final String from, final String cc, final String bcc,
			final String sub, final String mes, String host) throws NoSuchProviderException {
		
		host = "in-mum-smtp1-m181.icewarpcloud.in";
		boolean flag = false;
		final String SMTP_USERNAME = "icewarp-admin@smtp-marutisuzuki.icewarpcloud.in";
		final String SMTP_PASSWORD = "rwt3Ajmw&";
		final Properties props = System.getProperties();

		// Setting up mail server
		  props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.smtp.port", "587");
		  props.put("mail.smtp.host", host); 

		  props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		  
		  System.out.println("user..."+SMTP_USERNAME+"..smt.."+SMTP_PASSWORD+"..host.."+host);
		final Session session1 = Session.getDefaultInstance(props);   
		try {
			
			// MimeMessage object.
			MimeMessage message = new MimeMessage(session1);

			// Set From Field: adding senders email to from field.
			message.setFrom(new InternetAddress(SMTP_USERNAME));
		 
			// Set To Field: adding recipient's email to from field.
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress("yogendra.yadav@armezosolutions.com"));
	
			InternetAddress[] parseTo = InternetAddress.parse(to , true);
			if(cc != null && cc.length()>0) {
				message.setRecipient(Message.RecipientType.CC,new InternetAddress(cc));
			}
			if(parseTo.length>0) {
			   ///msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setRecipients(javax.mail.Message.RecipientType.TO,  parseTo);
				message.setFrom(new InternetAddress("connect@irecruit.org.in","iRecruit"));
			}
			
			 
			
			// Set Subject: subject of the email
			message.setSubject(sub);

			// set body of the email.
			message.setContent(mes,"text/html");
			Transport transport = session1.getTransport("smtp");
			// Send email.
			transport.connect(host, SMTP_USERNAME, SMTP_PASSWORD);
		 

			// Send the email.
			transport.sendMessage(message, message.getAllRecipients());
			 
			System.out.println("Mail successfully sent");
			
//
//			session1.setDebug(false);
//			final MimeMessage message = new MimeMessage(session1);
//			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
//			msg.addHeader("format", "flowed");
//			msg.addHeader("Content-Transfer-Encoding", "8bit");
//
//			final String BODY = String.join(System.getProperty("line.separator"), mes);
//
//			message.setFrom(new InternetAddress(SMTP_USERNAME));
//			//msg.setFrom((Address) new InternetAddress("support@armezosolutions.com"));
//			msg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(to, false));
//			if(cc.length()>0)
//			msg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(cc, false));
//			if(bcc.length()>0)
//			msg.setRecipients(Message.RecipientType.BCC, (Address[]) InternetAddress.parse(bcc, false));
//
//			msg.setSubject(sub,"UTF-8");
//
//			msg.setContent(BODY, "text/html; charset=UTF-8");
//
//			msg.setSentDate(new Date());
//			final Transport transport = session1.getTransport();
//			transport.connect(smtp, SMTP_USERNAME, SMTP_PASSWORD);
//			System.out.println(".......Connected.....");
//			transport.sendMessage((Message) msg, msg.getAllRecipients());
//			System.out.println("..........Mail Sent...........");
//			flag = true;
		} catch (Exception e) {
			System.out.println("....error....email........."+e);
		}
		return flag;
	}

	public static final boolean validateMail(final String estr) {
		final String str = estr;
		final String regExpr = "^[\\w\\.-]{1,}\\@([\\da-zA-Z-]{1,}\\.){1,}[\\da-zA-Z-]+$";
		final Pattern pat = Pattern.compile(regExpr, 2);
		final Matcher matcher = pat.matcher(str);
		return matcher.find();
	}
}
