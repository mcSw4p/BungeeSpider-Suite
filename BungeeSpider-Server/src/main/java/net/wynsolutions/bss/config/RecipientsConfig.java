package net.wynsolutions.bss.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bss.email.recipients.Recipient;

public class RecipientsConfig {

	private static File configFile;
	private static Configuration configuration;

	private static List<Recipient> recipients = new ArrayList<Recipient>();
	
	public RecipientsConfig(String dFolder) {
		
		configFile = new File(dFolder, "recipients.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/recipients.yml")) {
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
		
		for(String s : configuration.getKeys()){
			for(String s1 : configuration.getStringList(s)){
				recipients.add(new Recipient(s, s1));
			}
		}
		
		
	}
	
	public static void saveConfig(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InputStream getResourceAsStream(String par1){
		return  getClass().getClassLoader().getResourceAsStream(par1);
	}
	
	public static List<String> getRecipientGroup(String par1){
		List<String> rs = new ArrayList<String>();
		for(Recipient r : recipients){
			if(r.getGroup().equalsIgnoreCase(par1)){
				rs.add(r.getEmail());
			}
		}
		
		return rs;	
	}
	
	public static List<String> getRecipientsEmails(){
		List<String> rs = new ArrayList<String>();
		for(Recipient r : recipients)
			rs.add(r.getEmail());
		return rs;
	}
	
	public static List<Recipient> getRecipients(){
		return recipients;
	}
	
	public static void addRecipient(Recipient par1){
		recipients.add(par1);
		
		saveConfig();
		
	}
	
}
