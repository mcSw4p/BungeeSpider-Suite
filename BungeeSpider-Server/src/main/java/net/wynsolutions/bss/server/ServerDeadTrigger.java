package net.wynsolutions.bss.server;

import java.util.concurrent.TimeUnit;

import net.wynsolutions.bss.BSSPluginLoader;

public class ServerDeadTrigger {

	private String serverName;
	
	public boolean cancel = false, dead = false;
	
	public ServerDeadTrigger(String par1) {
		this.serverName = par1;
		
		BSSPluginLoader.instance.getProxy().getScheduler().schedule(BSSPluginLoader.instance, new Runnable(){

			@Override public void run() {
				if(!cancel){
					BSSPluginLoader.triggerInactiveServer(serverName);
					new InactiveServerNotify(serverName);
					dead = true;
				}
			}
			
		}, BSSPluginLoader.instance.getServerTimeout(), TimeUnit.SECONDS);
		
	}
	
	public void cancel(){
		cancel = true;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public String getServerName(){
		return serverName;
	}
	
}
