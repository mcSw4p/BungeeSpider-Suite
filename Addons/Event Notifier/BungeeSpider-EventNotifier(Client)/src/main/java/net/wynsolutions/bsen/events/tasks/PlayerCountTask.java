package net.wynsolutions.bsen.events.tasks;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;
import net.wynsolutions.bsen.debug.Debug;

public class PlayerCountTask implements Runnable{

	private BSENAddon plug;

	public PlayerCountTask(BSENAddon par1) {
		this.plug = par1;
	}

	@Override public void run() {
		this.plug.getPlayerCountMonitor().updatePlayerCountStats();
		if(this.plug.getPlayerCountMonitor().getCurrentPlayerCount() >= this.plug.getPlayerCountThreshold()){
			//Send Message to recipients
			String command = "message";
			if(this.plug.isPlayerCountEnabled()){
				command = "gmessage";
			}
			BSC.sendMessage(command, this.plug.getPlayerCountTo(), this.plug.getPlayerCountSubject().replaceAll("<server>", BSC.getServerName()),
					this.plug.getPlayerCountMessage().replaceAll("<server>", BSC.getServerName()));
		}
		Debug.info("Player Count: " + this.plug.getPlayerCountMonitor().getCurrentPlayerCount() + "/" + BSC.getMaxPlayers());
	}

}
