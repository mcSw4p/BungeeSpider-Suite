package net.wynsolutions.bsen.events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MonitorDiskUsage {

	private long diskMax;
	private double diskUsed, diskFree, diskFreePerecnt;
	private int pastLenth = 3;
	private List<Double> pastDiskUsage = new ArrayList<Double>();
	
	public void addDiskUsageToPast(Double par1){
		if(this.pastDiskUsage.size() >= this.pastLenth){
			this.pastDiskUsage.remove(this.pastLenth-1);
		}
		this.pastDiskUsage.add(par1);
	}
	
	public void updateDiskUsageStats(){
		
		File diskPart = new File("/");
		this.diskMax = diskPart.getTotalSpace() / (1024 *1024 *1024);
		this.diskUsed = ((this.diskMax - diskPart.getFreeSpace()) / (1024 *1024 *1024));
		this.diskFree = (diskPart.getFreeSpace() / (1024 *1024 *1024));
		this.diskFreePerecnt = Double.parseDouble(String.format("%.2f", ((100D / this.diskMax) * this.diskFree)));
		this.addDiskUsageToPast(this.diskFreePerecnt);
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
