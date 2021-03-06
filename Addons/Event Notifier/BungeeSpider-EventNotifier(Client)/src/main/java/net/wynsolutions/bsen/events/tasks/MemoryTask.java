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
public class MemoryTask implements Runnable{

	// Addon instance
	private BSENAddon plug;

	public MemoryTask(BSENAddon par1) {
		this.plug = par1; // Initialize addon instance
	}

	/**
	 * Runnable task
	 */
	@Override public void run() {
		this.plug.getMemoryMonitor().updateMemoryStats(); // Update Statistics
		if(this.plug.getMemoryMonitor().getMemFree() <= this.plug.getMemThreshold()){ // Is Memory free greater than the threshold?
			String command = "message"; // Initialize command String
			if(this.plug.isMemGroup()){ // Is the To String a group?
				command = "gmessage"; // Set command String to group message
			}
			BSC.sendMessage(command, this.plug.getMemTo(), this.plug.getMemSubject().replaceAll("<server>", BSC.getServerName()),
					this.plug.getMemMessage().replaceAll("<server>", BSC.getServerName())); // Send socket message to sever 
		}
		Debug.info("Free memory: " + this.plug.getMemoryMonitor().getMemFreePercent()); // Debug to console
	}

}
