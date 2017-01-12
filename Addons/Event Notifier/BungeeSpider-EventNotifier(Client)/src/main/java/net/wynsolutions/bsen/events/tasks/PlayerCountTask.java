package net.wynsolutions.bsen.events.tasks;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;
import net.wynsolutions.bsen.debug.Debug;
/**
 * Copyright (C) 2017  Sw4p
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Sw4p
 *
 */
public class PlayerCountTask implements Runnable{

	// Addon instance
	private BSENAddon plug;

	public PlayerCountTask(BSENAddon par1) {
		this.plug = par1; // Initialize addon instance
	}

	/**
	 * Runnable task
	 */
	@Override public void run() {
		this.plug.getPlayerCountMonitor().updatePlayerCountStats(); // Update Statistics
		if(this.plug.getPlayerCountMonitor().getCurrentPlayerCount() >= (BSC.getMaxPlayers() - this.plug.getPlayerCountThreshold())){ // Is Player count greater than the Threshold?
			String command = "message"; // Initialize command String
			if(this.plug.isPlayerCountEnabled()){ // Is the To String a group?
				command = "gmessage"; // Set command String to group message
			}
			BSC.sendMessage(command, this.plug.getPlayerCountTo(), this.plug.getPlayerCountSubject().replaceAll("<server>", BSC.getServerName()),
					this.plug.getPlayerCountMessage().replaceAll("<server>", BSC.getServerName())); // Send socket message to sever 
		}
		Debug.info("Player Count: " + this.plug.getPlayerCountMonitor().getCurrentPlayerCount() + "/" + BSC.getMaxPlayers()); // Debug to console
	}

}
