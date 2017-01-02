package net.wynsolutions.bss;

import java.util.logging.Logger;

import net.wynsolutions.bss.commands.BSSCommands;

public class BSSPluginLoader extends BSSPlugin{

	private BSSLaunch func_001;
	public static Logger logger;
	
	@Override public void onEnable() {
		logger = this.getProxy().getLogger();
		this.func_001 = new BSSLaunch();
		this.func_001.onEnable();
		this.getProxy().getPluginManager().registerCommand(this, new BSSCommands());
		super.onEnable();
	}
	
	@Override public void onDisable() {
		this.func_001.onDisable();
		super.onDisable();
	}
	
}
