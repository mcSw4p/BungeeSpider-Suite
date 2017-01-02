package net.wynsolutions.bsc;

import org.bukkit.plugin.java.JavaPlugin;

public class BSCPlugin extends JavaPlugin{
	
	@Override public void onDisable() {
		this.getServer().getLogger().info("[BSC] BungeeSpider Client is disabling.");
		super.onDisable();
	}
	
	@Override public void onEnable() {
		this.getServer().getLogger().info("[BSC] BungeeSpider Client is enabling.");
		super.onEnable();
	}
	
	@Override public void onLoad() {
		this.getServer().getLogger().info("[BSC] BungeeSpider Client is loading...");
		super.onLoad();
	}
	
}
