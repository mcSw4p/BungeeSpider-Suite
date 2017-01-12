package net.wynsolutions.bsen;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bsen.commands.BSENCommand;
import net.wynsolutions.bsen.events.MonitorDiskUsage;
import net.wynsolutions.bsen.events.MonitorMemory;
import net.wynsolutions.bsen.events.MonitorPlayerCount;
import net.wynsolutions.bsen.events.MonitorTPS;
import net.wynsolutions.bsen.events.tasks.DiskUsageTask;
import net.wynsolutions.bsen.events.tasks.MemoryTask;
import net.wynsolutions.bsen.events.tasks.PlayerCountTask;
import net.wynsolutions.bsen.events.tasks.TPSTask;
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
public class BSENAddon extends Addon{

	/*
	 * Computers do not do what you want. They simply do what you tell them.
	 * ----
	 * Having problems getting a class to do what you want? Write one that does.
	 */
	
	// Monitors
	private MonitorTPS tpsMonitor;
	private MonitorMemory memoryMonitor;
	private MonitorPlayerCount playerCountMonitor;
	private MonitorDiskUsage diskUsageMonitor;
	
	// Debug?
	private static boolean debug;
	
	// Settings
	private int tpsInterval, memInterval, playerCountInterval, diskUsageInterval, playerCountThreshold, diskUsageThreshold;
	private boolean tpsEnabled, memEnabled, playerCountEnabled, diskUsageEnabled;
	private double tpsThreshold, memThreshold;
	
	// Recipients
	private boolean tpsGroup, memGroup, playerCountGroup, diskUsageGroup;
	private String tpsTo, memTo, playerCountTo, diskUsageTo;
	
	// Message Settings
	private String tpsSubject, tpsMessage, memSubject, memMessage, playerCountSubject, playerCountMessage, diskUsageSubject, diskUsageMessage;
	
	@Override public void onEnable() {
		
		getLogger().info("[BSEN] Enabling BungeeSpider-EventNotifier"); // Notify Console that BSEN is enabling
		
		this.createConfig(); // Create /Load Configuration
		this.initMonitors(); // Initialize Monitors
		
		this.getHandler().registerCommand("bsen", new BSENCommand(this)); // Register BSEN Command
		
		super.onEnable(); // Call parent method
	}
	
	@Override public void onDisable() {
		
		getLogger().info("[BSEN] Disabling BungeeSpider-EventNotifier"); // Notify Console that BSEN is disabling
		
		BSC.cancelAllTaskForAddon("BungeeSpider-EventNotifier(Client)"); // Cancel all the active tasks for this addon
		
		super.onDisable(); // Call parent method
	}
	
	private void createConfig(){
		this.setupConfig(this.getResourceAsStream("config.yml")); // Setup Configuration Files
		this.tpsInterval = (this.getConfig().getInt("tps.update-interval") * 20); // Load TPS Interval
		this.memInterval = (this.getConfig().getInt("memory.update-interval") * 20); // Load Memory Interval
		this.playerCountInterval = (this.getConfig().getInt("playercount.update-interval") * 20); // Load Player count Interval
		this.diskUsageInterval = (this.getConfig().getInt("diskusage.update-interval") * 20); // Load Disk usage Interval
		
		this.tpsEnabled = this.getConfig().getBoolean("tps.enabled"); // Load TPS Enabled
		this.memEnabled = this.getConfig().getBoolean("memory.enabled"); // Load Memory Enabled
		this.playerCountEnabled = this.getConfig().getBoolean("playercount.enabled"); // Load Player count Enabled
		this.diskUsageEnabled = this.getConfig().getBoolean("diskusage.enabled"); // Load Disk usage Enabled
		
		this.tpsThreshold = this.getConfig().getDouble("tps.threshold"); // Load TPS Threshold
		this.memThreshold = this.getConfig().getDouble("memory.threshold"); // Load Memory Threshold
		this.playerCountThreshold = this.getConfig().getInt("playercount.threshold"); // Load Player count Threshold
		this.diskUsageThreshold = this.getConfig().getInt("diskusage.threshold"); // Load Disk usage Threshold
		
		this.tpsGroup = this.getConfig().getBoolean("tps.to.group"); // Load TPS Group Enabled
		this.memGroup = this.getConfig().getBoolean("memory.to.group"); // Load Memory Group Enabled
		this.playerCountGroup = this.getConfig().getBoolean("playercount.to.group"); // Load Player count Group Enabled
		this.diskUsageGroup = this.getConfig().getBoolean("diskusage.to.group"); // Load Disk usage Group Enabled
		
		this.tpsTo = this.getConfig().getString("tps.to.to"); // Load TPS To address
		this.memTo = this.getConfig().getString("memory.to.to"); // Load Memory To address
		this.playerCountTo = this.getConfig().getString("playercount.to.to"); // Load Player count To address
		this.diskUsageTo = this.getConfig().getString("diskusage.to.to"); // Load Disk usage To address
		
		this.tpsSubject = this.getConfig().getString("tps.msg.subject"); // Load TPS Message subject
		this.memSubject = this.getConfig().getString("memory.msg.subject"); // Load Memory Message Subject
		this.playerCountSubject = this.getConfig().getString("playercount.msg.subject"); // Load Player count Message subject
		this.diskUsageSubject = this.getConfig().getString("diskusage.msg.subject"); // Load Disk usage Message subject
		
		this.tpsMessage = this.getConfig().getString("tps.msg.message"); // Load TPS Message
		this.memMessage = this.getConfig().getString("memory.msg.message"); // Load Memory Message
		this.playerCountMessage = this.getConfig().getString("playercount.msg.message"); // Load Memory Message
		this.diskUsageMessage = this.getConfig().getString("diskusage.msg.message"); // Load Disk usage Message
		
		debug = this.getConfig().getBoolean("debug"); // Load Debug Enabled
	}

	private void initMonitors(){
		if(this.tpsEnabled) // Is TPS Enabled?
			this.tpsMonitor = new MonitorTPS(this); // Initialize TPS Monitor
		if(this.memEnabled) // Is Memory Enabled?
			this.memoryMonitor = new MonitorMemory(); // Initialize Memory Monitor
		if(this.playerCountEnabled) // Is Player count Enabled?
			this.playerCountMonitor = new MonitorPlayerCount(); // Initialize Player count Monitor
		if(this.diskUsageEnabled) // Is Disk usage Enabled?
			this.diskUsageMonitor = new MonitorDiskUsage(); // Initialize Disk usage Monitor.
		this.loadTasks(); // Load all tasks for checks
	}
	
	private void loadTasks(){
		if(this.tpsEnabled) // Is TPS Enabled?
			BSC.scheduleSyncRepeatingTask(this, new TPSTask(this), 2, this.tpsInterval); // Schedule repeating task for TPS Checks
		if(this.memEnabled) // Is Memory EnableD?
			BSC.scheduleSyncRepeatingTask(this, new MemoryTask(this), 2, this.memInterval); // Schedule repeating task for Memory Checks
		if(this.playerCountEnabled) // Is Player count Enabled?
			BSC.scheduleSyncRepeatingTask(this, new PlayerCountTask(this), 2, this.playerCountInterval); // Schedule repeating task for Player count Checks
		if(this.diskUsageEnabled) // Is Disk usage Enabled?
			BSC.scheduleSyncRepeatingTask(this, new DiskUsageTask(this), 2, this.diskUsageInterval); // Schedule repeating task for Disk usage Checks
	}

	/**
	 * @return the tpsMonitor
	 */
	public MonitorTPS getTpsMonitor() {
		return tpsMonitor;
	}
	
	/**
	 * @return the memoryMonitor
	 */
	public MonitorMemory getMemoryMonitor() {
		return memoryMonitor;
	}

	/**
	 * @return the playerCountMonitor
	 */
	public MonitorPlayerCount getPlayerCountMonitor() {
		return playerCountMonitor;
	}

	/**
	 * @return the playerCountThreshold
	 */
	public int getPlayerCountThreshold() {
		return playerCountThreshold;
	}

	/**
	 * @return the tpsThreshold
	 */
	public double getTpsThreshold() {
		return tpsThreshold;
	}

	/**
	 * @return the memThreshold
	 */
	public double getMemThreshold() {
		return memThreshold;
	}

	/**
	 * @return the tpsInterval
	 */
	public int getTpsInterval() {
		return tpsInterval;
	}

	/**
	 * @return the memInterval
	 */
	public int getMemInterval() {
		return memInterval;
	}

	/**
	 * @return the playerCountInterval
	 */
	public int getPlayerCountInterval() {
		return playerCountInterval;
	}

	/**
	 * @return the tpsEnabled
	 */
	public boolean isTpsEnabled() {
		return tpsEnabled;
	}

	/**
	 * @return the memEnabled
	 */
	public boolean isMemEnabled() {
		return memEnabled;
	}

	/**
	 * @return the playerCountEnabled
	 */
	public boolean isPlayerCountEnabled() {
		return playerCountEnabled;
	}

	/**
	 * @return the tpsGroup
	 */
	public boolean isTpsGroup() {
		return tpsGroup;
	}

	/**
	 * @return the memGroup
	 */
	public boolean isMemGroup() {
		return memGroup;
	}

	/**
	 * @return the playerCountGroup
	 */
	public boolean isPlayerCountGroup() {
		return playerCountGroup;
	}

	/**
	 * @return the tpsTo
	 */
	public String getTpsTo() {
		return tpsTo;
	}

	/**
	 * @return the memTo
	 */
	public String getMemTo() {
		return memTo;
	}

	/**
	 * @return the playerCountTo
	 */
	public String getPlayerCountTo() {
		return playerCountTo;
	}

	/**
	 * @return the tpsSubject
	 */
	public String getTpsSubject() {
		return tpsSubject;
	}

	/**
	 * @return the tpsMessage
	 */
	public String getTpsMessage() {
		return tpsMessage;
	}

	/**
	 * @return the memSubject
	 */
	public String getMemSubject() {
		return memSubject;
	}

	/**
	 * @return the memMessage
	 */
	public String getMemMessage() {
		return memMessage;
	}

	/**
	 * @return the playerCountSubject
	 */
	public String getPlayerCountSubject() {
		return playerCountSubject;
	}

	/**
	 * @return the playerCountMessage
	 */
	public String getPlayerCountMessage() {
		return playerCountMessage;
	}

	/**
	 * @return the diskUsageMonitor
	 */
	public MonitorDiskUsage getDiskUsageMonitor() {
		return diskUsageMonitor;
	}

	/**
	 * @return the diskUsageInterval
	 */
	public int getDiskUsageInterval() {
		return diskUsageInterval;
	}

	/**
	 * @return the diskUsageThreshold
	 */
	public int getDiskUsageThreshold() {
		return diskUsageThreshold;
	}

	/**
	 * @return the diskUsageEnabled
	 */
	public boolean isDiskUsageEnabled() {
		return diskUsageEnabled;
	}

	/**
	 * @return the diskUsageGroup
	 */
	public boolean isDiskUsageGroup() {
		return diskUsageGroup;
	}

	/**
	 * @return the diskUsageTo
	 */
	public String getDiskUsageTo() {
		return diskUsageTo;
	}

	/**
	 * @return the diskUsageSubject
	 */
	public String getDiskUsageSubject() {
		return diskUsageSubject;
	}

	/**
	 * @return the diskUsageMessage
	 */
	public String getDiskUsageMessage() {
		return diskUsageMessage;
	}

	/**
	 * @return the debug
	 */
	public static boolean isDebug() {
		return debug;
	}
	
}
