package net.wynsolutions.bsen.events.tasks;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;
import net.wynsolutions.bsen.debug.Debug;

public class MemoryTask implements Runnable{

	private BSENAddon plug;

	public MemoryTask(BSENAddon par1) {
		this.plug = par1;
	}

	@Override public void run() {
		this.plug.getMemoryMonitor().updateMemoryStats();
		if(this.plug.getMemoryMonitor().getMemFree() <= this.plug.getMemThreshold()){
			String command = "message";
			if(this.plug.isMemGroup()){
				command = "gmessage";
			}
			BSC.sendMessage(command, this.plug.getMemTo(), this.plug.getMemSubject().replaceAll("<server>", BSC.getServerName()),
					this.plug.getMemMessage().replaceAll("<server>", BSC.getServerName()));
		}

		Debug.info("Free memory: " + this.plug.getMemoryMonitor().getMemFreePercent());
	}

}
