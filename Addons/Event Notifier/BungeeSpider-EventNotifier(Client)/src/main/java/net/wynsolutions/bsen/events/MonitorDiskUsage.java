package net.wynsolutions.bsen.events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
public class MonitorDiskUsage {

	// Settings
	private long diskMax;
	private double diskUsed, diskFree, diskFreePerecnt;
	private int pastLenth = 3;
	private List<Double> pastDiskUsage = new ArrayList<Double>();
	
	/**
	 * Add a value to Disk usage history.
	 * @param par1
	 */
	public void addDiskUsageToPast(Double par1){
		if(this.pastDiskUsage.size() >= this.pastLenth){ // Is the pastDiskUsage length longer than pastLength?
			this.pastDiskUsage.remove(this.pastLenth-1); // Remove last Disk Usage statistic
		}
		this.pastDiskUsage.add(par1); // Add @param to Disk usage history.
	}
	
	/**
	 * Update the Disk usage statistics.
	 */
	public void updateDiskUsageStats(){
		
		File diskPart = new File("/"); // Create a new temp file in root folder
		this.diskMax = diskPart.getTotalSpace() / (1024 *1024 *1024); // Load Total Disk space
		this.diskUsed = ((this.diskMax - diskPart.getFreeSpace()) / (1024 *1024 *1024)); // Load Total Disk used
		this.diskFree = (diskPart.getFreeSpace() / (1024 *1024 *1024)); // Load Total Disk free space
		this.diskFreePerecnt = Double.parseDouble(String.format("%.2f", ((100D / this.diskMax) * this.diskFree))); // Calculate Total Disk space in percent
		this.addDiskUsageToPast(this.diskFreePerecnt); // Add Disk usage to Disk usage history
	}
	
	/**
	 * @return the diskMax
	 */
	public long getDiskMax() {
		return diskMax;
	}

	/**
	 * @return the diskUsed
	 */
	public double getDiskUsed() {
		return diskUsed;
	}

	/**
	 * @return the diskFree
	 */
	public double getDiskFree() {
		return diskFree;
	}

	/**
	 * @return the diskFreePerecnt
	 */
	public double getDiskFreePerecnt() {
		return diskFreePerecnt;
	}

	/**
	 * @return the pastLenth
	 */
	public int getPastLenth() {
		return pastLenth;
	}

	/**
	 * @return the pastDiskUsage
	 */
	public List<Double> getPastDiskUsage() {
		return pastDiskUsage;
	}
	
}
