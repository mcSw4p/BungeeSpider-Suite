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
public class DiskUsageTask implements Runnable{

	// Addon instance
	private BSENAddon plug;

	public DiskUsageTask(BSENAddon par1) {
		this.plug = par1; // Initialize addon instance
	}

	/**
	 * Runnable task
	 */
	@Override public void run() {
		this.plug.getDiskUsageMonitor().updateDiskUsageStats(); // Update Statistics
		if(this.plug.getDiskUsageMonitor().getDiskFree() <= this.plug.getDiskUsageThreshold()){ // Is Disk usage greater than the threshold?
			String command = "message"; // Initialize command String
			if(this.plug.isDiskUsageGroup()){ // Is the To String a group?
				command = "gmessage"; // Set command String to group message
			}
			BSC.sendMessage(command, this.plug.getDiskUsageTo(), this.plug.getDiskUsageSubject().replaceAll("<server>", BSC.getServerName()),
					this.plug.getDiskUsageMessage().replaceAll("<server>", BSC.getServerName())); // Send socket message to server
		}

		Debug.info("Free Disk space: " + this.plug.getDiskUsageMonitor().getDiskFreePerecnt()); // Debug to console
	}

}
