package net.wynsolutions.bsen.events;

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
public class MonitorMemory {

	// Settings
	private double memUsed, memMax, memFree, memFreePercent;
	private int pastLenth = 3;
	private List<Double> pastMemory = new ArrayList<Double>();
	
	/**
	 * Add a value to Memory history.
	 * @param par1
	 */
	public void addMemoryToPast(Double par1){
		if(this.pastMemory.size() >= this.pastLenth){ // Is pastMemory length longer than pastLength?
			this.pastMemory.remove(this.pastLenth-1); // Remove last Memory statistic
		}
		this.pastMemory.add(par1); // Add @param to Memory history.
	}
	
	/**
	 * Update Memory usage statistics.
	 */
	public void updateMemoryStats(){
		
		Runtime r = Runtime.getRuntime(); // Load Runtime
		this.memMax = (r.maxMemory() / 1048576D); // Load Max Memory
		this.memUsed = ((r.totalMemory() - r.freeMemory()) / 1048576D); // Load Memory Used
		this.memFree = (this.memMax - this.memUsed); // Load Memory Free
		this.memFreePercent = Double.parseDouble(String.format("%.2f", ((100D / this.memMax) * this.memFree))); // Calculate Memory Free percent
		this.addMemoryToPast(this.memFreePercent); // Add Memory Free to Memory history.
	}

	/**
	 * @return the memUsed
	 */
	public double getMemUsed() {
		return memUsed;
	}

	/**
	 * @return the memMax
	 */
	public double getMemMax() {
		return memMax;
	}

	/**
	 * @return the memFree
	 */
	public double getMemFree() {
		return memFree;
	}

	/**
	 * @return the memFreePercent
	 */
	public double getMemFreePercent() {
		return memFreePercent;
	}

	/**
	 * @return the pastLenth
	 */
	public int getPastLenth() {
		return pastLenth;
	}

	/**
	 * @return the pastMemory
	 */
	public List<Double> getPastMemory() {
		return pastMemory;
	}

	/**
	 * @param pastLenth the pastLenth to set
	 */
	public void setPastLenth(int pastLenth) {
		this.pastLenth = pastLenth;
	}
	
}
