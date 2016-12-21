package net.wynsolutions.bss;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.wynsolutions.bss.commands.BSSCommands;
import net.wynsolutions.bss.email.Email;
import net.wynsolutions.bss.email.recipients.Recipient;
import net.wynsolutions.bss.server.BSServer;
import net.wynsolutions.bss.server.ServerDeadTrigger;

public class BSSPluginLoader extends BSSPlugin{
	
	private BSServer BSSERVER;
	
	public static HashMap<String, Boolean> serverStatus = new HashMap<String, Boolean>();
	private static List<ServerDeadTrigger> serverDeadTriggers = new ArrayList<ServerDeadTrigger>();
	public static List<String> deadServers = new ArrayList<String>();
	private static List<Recipient> recipients = new ArrayList<Recipient>();
	
	private int serverPort = 25566, serverTimeout = 35;
	private boolean logEmailToCon;
	
	public static BSSPluginLoader instance;
	private static File configFile;

	private static File recipFile;
	public static Configuration configuration, recipConfig;
	
	@Override public void onEnable() {
		
		instance = this;
		
		this.loadConfig();
		
		this.BSSERVER = new BSServer(this);
		this.BSSERVER.start();
		
		this.getProxy().getPluginManager().registerCommand(this, new BSSCommands());
		
		super.onEnable();
	}
	
	@Override public void onDisable() {
			saveConfig();
		super.onDisable();
	}
	
	private void loadConfig(){
		
		configFile = new File(getDataFolder(), "emailprops.yml");
		if (!getDataFolder().exists())
			getDataFolder().mkdir();

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/emailprops.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		configFile = new File(getDataFolder(), "serverprops.yml");
		if (!getDataFolder().exists())
			getDataFolder().mkdir();

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/serverprops.yml")) {
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
		
		this.serverPort = configuration.getInt("serverPort");
		this.serverTimeout = configuration.getInt("serverTimeout");
		this.logEmailToCon = configuration.getBoolean("logEmail");
		
		recipFile = new File(getDataFolder(), "recipients.yml");
		if (!getDataFolder().exists())
			getDataFolder().mkdir();

        if (!recipFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/recipients.yml")) {
                Files.copy(in, recipFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		try {
			recipConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(recipFile);
			
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(recipConfig, recipFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String s : recipConfig.getKeys()){
			for(String s1 : recipConfig.getStringList(s)){
				recipients.add(new Recipient(s, s1));
			}
		}
		
	}
	
	private static void saveConfig(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, List<String>> configRecip = new HashMap<String, List<String>>();
		
		for(Recipient r : recipients){
			List<String> temp = new ArrayList<String>();
			if(!configRecip.containsKey(r.getGroup())){
				for(Recipient r2 : recipients){
					if(r2.getGroup().equals(r.getGroup())){
						temp.add(r2.getEmail());
					}
				}
				temp.add(r.getEmail());
			}
			configRecip.put(r.getGroup(), temp);
		}
		
		for(String s : configRecip.keySet()){
			recipConfig.set(s, configRecip.get(s));
		}
		
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(recipConfig, recipFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getServerPort(){
		return serverPort;
	}
	
	public int getServerTimeout(){
		return serverTimeout;
	}
	
	public boolean getLogEmail(){
		return logEmailToCon;
	}
	
	public static boolean serverDead(String par1){
		
		for(ServerDeadTrigger s : serverDeadTriggers){
			if(s.getServerName().equals(par1)){
				return s.isDead();
			}
		}
		
		return false;
	}
	
	private static void cancelDeadTrigger(String par1){
		for(ServerDeadTrigger s : serverDeadTriggers){
			if(s.getServerName().equals(par1)){
				s.cancel();
			}
		}
	}
	
	
	public static void triggerActiveServer(String par1){
		
		if(deadServers.contains(par1)){
			new Email(configuration.getString("serverResponding.subject"), configuration.getString("serverResponding.message").replace("<server>", par1), configuration.getString("email")).send();
			deadServers.remove(par1);
		}
		
		if(serverStatus.containsKey(par1)){
			cancelDeadTrigger(par1);
			serverStatus.remove(par1);
			serverStatus.put(par1, true);
			serverDeadTriggers.add(new ServerDeadTrigger(par1));

		}else{
			serverStatus.put(par1, true);
			serverDeadTriggers.add(new ServerDeadTrigger(par1));
		}

	}
	
	public static void triggerInactiveServer(String par1){
		
		cancelDeadTrigger(par1);
		
		if(serverStatus.containsKey(par1)){
			if(serverStatus.get(par1) != false){
				serverStatus.remove(par1);
				serverStatus.put(par1, false);
			}
		}else{
			serverStatus.put(par1, false);
		}
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
