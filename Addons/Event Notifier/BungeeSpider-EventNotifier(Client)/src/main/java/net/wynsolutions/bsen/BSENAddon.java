package net.wynsolutions.bsen;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bsen.events.MonitorDiskUsage;
import net.wynsolutions.bsen.events.MonitorMemory;
import net.wynsolutions.bsen.events.MonitorPlayerCount;
import net.wynsolutions.bsen.events.MonitorTPS;
import net.wynsolutions.bsen.events.tasks.DiskUsageTask;
import net.wynsolutions.bsen.events.tasks.MemoryTask;
import net.wynsolutions.bsen.events.tasks.PlayerCountTask;
import net.wynsolutions.bsen.events.tasks.TPSTask;

public class BSENAddon extends Addon{

	/*
	 * Computers do not do what you want. They simply do what you tell them.
	 * ----
	 * Having problems getting a class to do what you want? Write one that does.
	 */
	
	private MonitorTPS tpsMonitor;
	private MonitorMemory memoryMonitor;
	private MonitorPlayerCount playerCountMonitor;
	private MonitorDiskUsage diskUsageMonitor;
	
	private static boolean debug;
	
	// Settings
	private int tpsInterval, memInterval, playerCountInterval, diskUsageInterval, playerCountThreshold, diskUsageThreshold;
	private boolean tpsEnabled, memEnabled, playerCountEnabled, diskUsageEnabled;
	private double tpsThreshold, memThreshold;
	
	//Recipients
	private boolean tpsGroup, memGroup, playerCountGroup, diskUsageGroup;
	private String tpsTo, memTo, playerCountTo, diskUsageTo;
	
	//Message Settings
	private String tpsSubject, tpsMessage, memSubject, memMessage, playerCountSubject, playerCountMessage, diskUsageSubject, diskUsageMessage;
	
	@Override public void onEnable() {
		
		getLogger().info("[BSEN] Enabling BungeeSpider-EventNotifier");
		
		this.createConfig();
		this.initMonitors();
		
		super.onEnable();
	}
	
	@Override public void onDisable() {
		
		getLogger().info("[BSEN] Disabling BungeeSpider-EventNotifier");
		
		BSC.cancelAllTaskForAddon(this);
		super.onDisable();
	}
	
	private void createConfig(){
		this.setupConfig(this.getResourceAsStream("config.yml"));
		this.tpsInterval = (this.getConfig().getInt("tps.update-interval") * 20);
		this.memInterval = (this.getConfig().getInt("memory.update-interval") * 20);
		this.playerCountInterval = (this.getConfig().getInt("playercount.update-interval") * 20);
		this.diskUsageInterval = (this.getConfig().getInt("diskusage.update-interval") * 20);
		
		this.tpsEnabled = this.getConfig().getBoolean("tps.enabled");
		this.memEnabled = this.getConfig().getBoolean("memory.enabled");
		this.playerCountEnabled = this.getConfig().getBoolean("playercount.enabled");
		this.diskUsageEnabled = this.getConfig().getBoolean("diskusage.enabled");
		
		this.tpsThreshold = this.getConfig().getDouble("tps.threshold");
		this.memThreshold = this.getConfig().getDouble("memory.threshold");
		this.playerCountThreshold = this.getConfig().getInt("playercount.threshold");
		this.diskUsageThreshold = this.getConfig().getInt("diskusage.threshold");
		
		this.tpsGroup = this.getConfig().getBoolean("tps.to.group");
		this.memGroup = this.getConfig().getBoolean("memory.to.group");
		this.playerCountGroup = this.getConfig().getBoolean("playercount.to.group");
		this.diskUsageGroup = this.getConfig().getBoolean("diskusage.to.group");
		
		this.tpsTo = this.getConfig().getString("tps.to.to");
		this.memTo = this.getConfig().getString("memory.to.to");
		this.playerCountTo = this.getConfig().getString("playercount.to.to");
		this.diskUsageTo = this.getConfig().getString("diskusage.to.to");
		
		this.tpsSubject = this.getConfig().getString("tps.msg.subject");
		this.memSubject = this.getConfig().getString("memory.msg.subject");
		this.playerCountSubject = this.getConfig().getString("playercount.msg.subject");
		this.diskUsageSubject = this.getConfig().getString("diskusage.msg.subject");
		
		this.tpsMessage = this.getConfig().getString("tps.msg.message");
		this.memMessage = this.getConfig().getString("memory.msg.message");
		this.playerCountMessage = this.getConfig().getString("playercount.msg.message");
		this.diskUsageMessage = this.getConfig().getString("diskusage.msg.message");
		
		debug = this.getConfig().getBoolean("debug");
	}

	private void initMonitors(){
		if(this.tpsEnabled)
			this.tpsMonitor = new MonitorTPS(this);
		if(this.memEnabled)
			this.memoryMonitor = new MonitorMemory();
		if(this.playerCountEnabled)
			this.playerCountMonitor = new MonitorPlayerCount();
		if(this.diskUsageEnabled)
			this.diskUsageMonitor = new MonitorDiskUsage();
		this.loadTasks();
	}
	
	private void loadTasks(){
		if(this.tpsEnabled)
			BSC.scheduleSyncRepeatingTask(this, new TPSTask(this), 2, this.tpsInterval);
		if(this.memEnabled)
			BSC.scheduleSyncRepeatingTask(this, new MemoryTask(this), 2, this.memInterval);
		if(this.playerCountEnabled)
			BSC.scheduleSyncRepeatingTask(this, new PlayerCountTask(this), 2, this.playerCountInterval);
		if(this.diskUsageEnabled)
			BSC.scheduleSyncRepeatingTask(this, new DiskUsageTask(this), 2, this.diskUsageInterval);
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
