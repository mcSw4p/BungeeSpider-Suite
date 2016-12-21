package net.wynsolutions.bss.server;

import java.util.concurrent.TimeUnit;

import net.wynsolutions.bss.BSSPluginLoader;
import net.wynsolutions.bss.email.Email;

public class InactiveServerNotify {

	private String serverName;
	
	public InactiveServerNotify(String par1) {
		
		this.serverName = par1;
		
		BSSPluginLoader.instance.getProxy().getScheduler().schedule(BSSPluginLoader.instance, new Runnable(){

			@Override public void run() {	
				if(BSSPluginLoader.serverDead(serverName)){	
					
					BSSPluginLoader.instance.getProxy().getLogger().info("Server " + serverName + " has become inactive. May require attention.");
					//Notify Recipients
					new Email(BSSPluginLoader.configuration.getString("serverUnresponsive.subject"), 
							BSSPluginLoader.configuration.getString("serverUnresponsive.message").replace("<server>", serverName),
							BSSPluginLoader.configuration.getString("email")).send();
					BSSPluginLoader.deadServers.add(serverName);
				}	
			}	
			
		}, BSSPluginLoader.instance.getServerTimeout()/3, TimeUnit.SECONDS);	
	}
	
}
