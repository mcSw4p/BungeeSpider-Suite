package net.wynsolutions.bss;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.wynsolutions.bss.addons.AddonHandler;
import net.wynsolutions.bss.config.EmailPropertiesConfig;
import net.wynsolutions.bss.config.IpTableConfig;
import net.wynsolutions.bss.config.RecipientsConfig;
import net.wynsolutions.bss.config.ServerPropertiesConfig;
import net.wynsolutions.bss.email.Email;
import net.wynsolutions.bss.server.BSServer;
import net.wynsolutions.bss.server.ServerDeadTrigger;

public class BSSLaunch{
	/*
	 * Mock a java standalone app to help visualize the separate servers. Keep away from Spigot API as much as possible.
	 * Also allows easy porting.
	 * 
	 * --
	 * Computers do not do what you want. They simply do what you tell them.
	 * 
	 */
	private BSServer BSSERVER;

	public static HashMap<String, Boolean> serverStatus = new HashMap<String, Boolean>();
	private static List<ServerDeadTrigger> serverDeadTriggers = new ArrayList<ServerDeadTrigger>();
	public static List<String> deadServers = new ArrayList<String>();

	public static boolean allowUnrecognizedClients;

	public static BSSLaunch instance;

	private static File serverJar;

	public ServerPropertiesConfig serverProps;
	public EmailPropertiesConfig emailProps;
	public RecipientsConfig recipientsConfig;
	public IpTableConfig ipTableconfig;

	private AddonHandler addons;

	public void onEnable() {

		instance = this;
		serverJar = new File(System.getProperty("user.dir") + File.separatorChar + "plugins" + File.separatorChar + "BungeeSpider-Server");
		this.loadConfig();

		this.BSSERVER = new BSServer(this);
		this.BSSERVER.start();

		this.addons = new AddonHandler();

		new BSS(this);
	}

	public void onDisable() {
		saveConfig();
		this.addons.disableAddons();
	}

	private void loadConfig(){

		File addonFolder = new File(getDataFolder().getPath() +  File.separatorChar + "addons");
		if(!addonFolder.exists())
			addonFolder.mkdirs();

		this.serverProps = new ServerPropertiesConfig(getDataFolder().getPath());
		this.emailProps = new EmailPropertiesConfig(getDataFolder().getPath());
		this.recipientsConfig = new RecipientsConfig(getDataFolder().getPath());
		this.ipTableconfig = new IpTableConfig(getDataFolder().getPath());

	}

	private void saveConfig(){
		this.serverProps.saveConfig();
		this.emailProps.saveConfig();
		RecipientsConfig.saveConfig();
		IpTableConfig.saveConfig();
	}

	public int getServerPort(){
		return this.serverProps.getServerPort();
	}

	public int getServerTimeout(){
		return this.serverProps.getServerTimeout();
	}

	public boolean getLogEmail(){
		return this.serverProps.isLogEmailToCon();
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
			new Email(ServerPropertiesConfig.getConfig().getString("serverResponding.subject"),
					ServerPropertiesConfig.getConfig().getString("serverResponding.message").replace("<server>", par1),
					ServerPropertiesConfig.getConfig().getString("email")).send();
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

	public static File getDataFolder(){
		return serverJar;
	}
	
	public AddonHandler getAddonHandler(){
		return this.addons;
	}
}
