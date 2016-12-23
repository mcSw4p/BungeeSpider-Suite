package net.wynsolutions.bss.server;

import java.util.concurrent.TimeUnit;

import net.wynsolutions.bss.BSSLaunch;
import net.wynsolutions.bss.config.ServerPropertiesConfig;
import net.wynsolutions.bss.email.Email;

public class InactiveServerNotify {

	private String serverName;
	
	public InactiveServerNotify(String par1) {
		
		this.serverName = par1;
		
		Scheduler.scheduleTask(new Runnable(){

			@Override public void run() {	
				if(BSSLaunch.serverDead(serverName)){	
					
					System.out.println("Server " + serverName + " has become inactive. May require attention.");
					//Notify Recipients
					new Email(ServerPropertiesConfig.getConfig().getString("serverUnresponsive.subject"), 
							ServerPropertiesConfig.getConfig().getString("serverUnresponsive.message").replace("<server>", serverName),
							ServerPropertiesConfig.getConfig().getString("email")).send();
					BSSLaunch.deadServers.add(serverName);
				}	
			}	
			
		}, BSSLaunch.instance.getServerTimeout()/3, TimeUnit.SECONDS);	
	}
	
}
