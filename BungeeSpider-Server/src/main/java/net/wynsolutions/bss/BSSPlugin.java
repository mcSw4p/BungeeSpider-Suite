package net.wynsolutions.bss;

import java.util.logging.Logger;

import net.md_5.bungee.api.plugin.Plugin;

public class BSSPlugin extends Plugin{

	/*
	 * This plugin's purpose is to simply collect all servers and organize them into active and inactive servers. 
	 * Account for servers that may be unreachable.
	 * 
	 * Have servers send connections to the bungee server and if the bungee server cannot see the server then it should try to talk to it before
	 * declaring it inactive.
	 * 
	 */
	
	private Logger LOGGER;
	
	@Override public void onDisable() {
		this.getProxy().getLogger().info("[BSS] BungeeSpider Server is disabling.");
		super.onDisable();
	}
	
	@Override public void onEnable() {
		this.LOGGER = this.getProxy().getLogger();
		this.getProxy().getLogger().info("[BSS] BungeeSpider Server is enabling.");
		super.onEnable();
	}
	
	@Override public void onLoad() {
		this.getProxy().getLogger().info("[BSS] BungeeSpider Server is loading...");
		super.onLoad();
	}
	
	@Override public Logger getLogger() {
		return LOGGER;
	}
	
}
