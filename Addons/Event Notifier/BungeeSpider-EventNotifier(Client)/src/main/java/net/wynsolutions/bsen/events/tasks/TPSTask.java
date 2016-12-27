package net.wynsolutions.bsen.events.tasks;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;
import net.wynsolutions.bsen.debug.Debug;

public class TPSTask implements Runnable{

	private BSENAddon plug;
	
	public TPSTask(BSENAddon par1) {
		this.plug = par1;
	}
	
	@Override public void run() {
		this.plug.getTpsMonitor().updateTPS();
		if(this.plug.getTpsMonitor().getTPS() <= this.plug.getTpsThreshold()){
			String command = "message";
			if(this.plug.isTpsGroup()){
				command = "gmessage";
			}
			BSC.sendMessage(command, this.plug.getTpsTo(), this.plug.getTpsSubject().replaceAll("<server>", BSC.getServerName()),
					this.plug.getTpsMessage().replaceAll("<server>", BSC.getServerName()));
		}
		
		Debug.info("TPS: " + this.plug.getTpsMonitor().getTPS());
	}

}
