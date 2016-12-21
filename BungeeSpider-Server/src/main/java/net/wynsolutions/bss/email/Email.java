package net.wynsolutions.bss.email;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.wynsolutions.bss.BSSPluginLoader;

public class Email {

	private String host, user, password, port, subject, messageText, to;
	private Session session;
	private boolean tls, ssl;
	
	private File configFile;
	private Configuration configuration;
	
	public Email(String par1, String par2, String par3) {
		
		configFile = new File(BSSPluginLoader.instance.getDataFolder(), "emailprops.yml");
		if (!BSSPluginLoader.instance.getDataFolder().exists())
			BSSPluginLoader.instance.getDataFolder().mkdir();

        if (!configFile.exists()) {
            try (InputStream in = BSSPluginLoader.instance.getResourceAsStream("resources/emailprops.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
			
        	ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.subject = par1;
		this.messageText = par2;
		this.to = par3;
		
		//Load from config all vars
		user = configuration.getString("username");
		password = configuration.getString("password");
		host = configuration.getString("host-name");
		port = String.valueOf(configuration.getInt("host-port"));
		tls = configuration.getBoolean("tls");
		ssl = configuration.getBoolean("ssl");
		
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
		BSSPluginLoader.instance.getProxy().getScheduler().runAsync(BSSPluginLoader.instance, new Runnable(){
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
					BSSPluginLoader.instance.getProxy().getLogger().info("Sent message \"" + messageText + "\" with subject \"" + subject + "\" to \"" + to + "\".");
					
				}catch(MessagingException e){
					e.printStackTrace();
				}
			}	
		});
	}
	
}
