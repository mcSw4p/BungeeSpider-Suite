package net.wynsolutions.bss.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.wynsolutions.bss.config.EmailPropertiesConfig;

public class Email {

	private String host, user, password, port, subject, messageText, to;
	private Session session;
	private boolean tls, ssl;

	public Email(String par1, String par2, String par3) {
		
		this.subject = par1;
		this.messageText = par2;
		this.to = par3;
		
		//Load from config all vars
		user = EmailPropertiesConfig.getEmailuser();
		password = EmailPropertiesConfig.getEmailpass();
		host = EmailPropertiesConfig.getHostname();
		port = String.valueOf(EmailPropertiesConfig.getHostport());
		tls = EmailPropertiesConfig.isTls();
		ssl = EmailPropertiesConfig.isSsl();
		
		Properties props = new Properties();
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", String.valueOf(tls).toLowerCase());
		props.put("mail.smtp.auth", "true");
		
		if(ssl){
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
		}
		
		session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(user, password);
			}
		});
		
	}
	
	public void send(){
		
		new Thread(new Runnable(){

			@Override public void run() {
				try{
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(user));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					if(subject != null && !subject.equalsIgnoreCase("null") && !subject.equalsIgnoreCase("none"))
						message.setSubject(subject);
					message.setSentDate(new Date());
					message.setText(messageText);
					
					//Send
					Transport.send(message);
					
				}catch(MessagingException e){
					e.printStackTrace();
				}
				
			}
			
		});
	}
	
}
