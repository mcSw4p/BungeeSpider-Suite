package net.wynsolutions.bsen.events;

import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bsc.BSC;

public class MonitorPlayerCount {

	private int pastLenth = 3, currentPlayerCount = 0;
	private List<Integer> pastPlayerCounts = new ArrayList<Integer>();
	
	public void addPlayerCountToPast(Integer par1){
		if(this.pastPlayerCounts.size() >= this.pastLenth){
			this.pastPlayerCounts.remove(this.pastLenth-1);
		}
		this.pastPlayerCounts.add(par1);
	}
	
	public void updatePlayerCountStats(){
		
		this.currentPlayerCount = BSC.getCurrentPlayerCount();
		
		this.addPlayerCountToPast(this.currentPlayerCount);
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
