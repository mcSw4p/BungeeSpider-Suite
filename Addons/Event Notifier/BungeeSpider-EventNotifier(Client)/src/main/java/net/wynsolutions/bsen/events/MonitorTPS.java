package net.wynsolutions.bsen.events;

import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;

public class MonitorTPS {
	
	private int tpsRecordLength = 3;
	private List<Double> pastTPS = new ArrayList<Double>();

	private Lag lagTask;

	private BSENAddon plug;

	public MonitorTPS(BSENAddon par1) {
	
		this.plug = par1;
		this.lagTask = new Lag();
		BSC.scheduleSyncRepeatingTask(plug, this.lagTask, 100L, 1L);
		
	}

	public double getTPS(){
		return Double.parseDouble(String.format("%.2f", this.lagTask.getTPS()));
	}
	
	public void updateTPS(){
		if(this.pastTPS.size() >= this.tpsRecordLength){
			this.pastTPS.remove(this.tpsRecordLength-1);
		}
		this.pastTPS.add(this.getTPS());
	}
	
	
	private class Lag implements Runnable {
	  public int TICK_COUNT= 0;
	  public long[] TICKS= new long[600];
	 
	  public double getTPS(){
	    return getTPS(100);
	  }
	 
	  public double getTPS(int ticks) {
	    if (TICK_COUNT< ticks) {
	      return 20.0D;
	    }
	    int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
	    long elapsed = System.currentTimeMillis() - TICKS[target];
	 
	    return ticks / (elapsed / 1000.0D);
	  }
	 
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
