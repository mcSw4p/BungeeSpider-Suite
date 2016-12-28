package net.wynsolutions.bsen.events;

import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bsc.BSC;
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
public class MonitorPlayerCount {

	// Settings
	private int pastLenth = 3, currentPlayerCount = 0;
	private List<Integer> pastPlayerCounts = new ArrayList<Integer>();
	
	/**
	 * Add value to Player count history.
	 * @param par1
	 */
	public void addPlayerCountToPast(Integer par1){
		if(this.pastPlayerCounts.size() >= this.pastLenth){ // Is Player count History size longer than pastLength
			this.pastPlayerCounts.remove(this.pastLenth-1); // Remove Player count from Player count history
		}
		this.pastPlayerCounts.add(par1); // Add Player count to history
	}
	
	/**
	 * Update Player count statistics.
	 */
	public void updatePlayerCountStats(){
		this.currentPlayerCount = BSC.getCurrentPlayerCount(); // Load Player count
		this.addPlayerCountToPast(this.currentPlayerCount); // Add Player count to history
	}

	/**
	 * @return the pastLenth
	 */
	public int getPastLenth() {
		return pastLenth;
	}

	/**
	 * @param pastLenth the pastLenth to set
	 */
	public void setPastLenth(int pastLenth) {
		this.pastLenth = pastLenth;
	}

	/**
	 * @return the currentPlayerCount
	 */
	public int getCurrentPlayerCount() {
		return currentPlayerCount;
	}

	/**
	 * @return the pastPlayerCounts
	 */
	public List<Integer> getPastPlayerCounts() {
		return pastPlayerCounts;
	}
	
}
