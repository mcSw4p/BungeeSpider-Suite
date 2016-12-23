package net.wynsolutions.bss;

import net.wynsolutions.bss.commands.BSSCommands;

public class BSSPluginLoader extends BSSPlugin{

	private BSSLaunch func_001;
	
	@Override public void onEnable() {
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
