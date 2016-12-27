package net.wynsolutions.bsen.events;

import java.util.ArrayList;
import java.util.List;

public class MonitorMemory {

	private double memUsed, memMax, memFree, memFreePercent;
	private int pastLenth = 3;
	private List<Double> pastMemory = new ArrayList<Double>();
	
	public void addMemoryToPast(Double par1){
		if(this.pastMemory.size() >= this.pastLenth){
			this.pastMemory.remove(this.pastLenth-1);
		}
		this.pastMemory.add(par1);
	}
	
	public void updateMemoryStats(){
		
		Runtime r = Runtime.getRuntime();
		this.memMax = (r.maxMemory() / 1048576D);
		this.memUsed = ((r.totalMemory() - r.freeMemory()) / 1048576D);
		this.memFree = (this.memMax - this.memUsed);
		this.memFreePercent = Double.parseDouble(String.format("%.2f", ((100D / this.memMax) * this.memFree)));
		this.addMemoryToPast(this.memFreePercent);
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
