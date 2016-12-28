package net.wynsolutions.bsen.events;

import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;
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
public class MonitorTPS {
	
	// Settings
	private int tpsRecordLength = 3;
	private List<Double> pastTPS = new ArrayList<Double>();
	private Lag lagTask;
	private BSENAddon plug;

	public MonitorTPS(BSENAddon par1) {
		this.plug = par1; // Load Plug
		this.lagTask = new Lag(); // Initialize Lag repeating task
		BSC.scheduleSyncRepeatingTask(plug, this.lagTask, 100L, 1L); // Schedule Lag task
		
	}
	
	/**
	 * 
	 * @return Returns the current tps of the server.
	 */
	public double getTPS(){
		return Double.parseDouble(String.format("%.2f", this.lagTask.getTPS()));
	}
	
	/**
	 * Update TPS statistics.
	 */
	public void updateTPS(){
		if(this.pastTPS.size() >= this.tpsRecordLength){
			this.pastTPS.remove(this.tpsRecordLength-1);
		}
		this.pastTPS.add(this.getTPS());
	}
	
	/**
	 * Compares System times to calculate server lag.
	 */
	private class Lag implements Runnable {
	  public int TICK_COUNT= 0;
	  public long[] TICKS= new long[600];
	 
	  /**
	   * @return Returns the current tps on the server.
	   */
	  public double getTPS(){
	    return getTPS(100);
	  }
	 
	  /**
	   * 
	   * @param ticks
	   * @return Returns the current tps on the server.
	   */
	  public double getTPS(int ticks) {
	    if (TICK_COUNT< ticks) { // If ticks is greater than tick count
	      return 20.0D; // Return 20.0
	    }
	    int target = (TICK_COUNT- 1 - ticks) % TICKS.length; // Calculate average tick count 
	    long elapsed = System.currentTimeMillis() - TICKS[target]; // calculate elapsed time
	 
	    return ticks / (elapsed / 1000.0D); // Return method
	  }
	 
	  /**
	   * Runnable task
	   */
	  public void run(){
	    TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();
	 
	    TICK_COUNT+= 1;
	  }
	}


	/**
	 * @param tpsRecordLength the tpsRecordLength to set
	 */
	public void setTpsRecordLength(int tpsRecordLength) {
		this.tpsRecordLength = tpsRecordLength;
	}

}
