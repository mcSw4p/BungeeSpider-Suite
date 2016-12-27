package net.wynsolutions.bsen.events.tasks;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;
import net.wynsolutions.bsen.debug.Debug;

public class DiskUsageTask implements Runnable{

	private BSENAddon plug;

	public DiskUsageTask(BSENAddon par1) {
		this.plug = par1;
	}

	@Override public void run() {
		this.plug.getDiskUsageMonitor().updateDiskUsageStats();
		if(this.plug.getDiskUsageMonitor().getDiskFree() <= this.plug.getDiskUsageThreshold()){
			String command = "message";
			if(this.plug.isDiskUsageGroup()){
				command = "gmessage";
			}
			BSC.sendMessage(command, this.plug.getDiskUsageTo(), this.plug.getDiskUsageSubject().replaceAll("<server>", BSC.getServerName()),
					this.plug.getDiskUsageMessage().replaceAll("<server>", BSC.getServerName()));
		}

		Debug.info("Free Disk space: " + this.plug.getDiskUsageMonitor().getDiskFreePerecnt());
	}

}
