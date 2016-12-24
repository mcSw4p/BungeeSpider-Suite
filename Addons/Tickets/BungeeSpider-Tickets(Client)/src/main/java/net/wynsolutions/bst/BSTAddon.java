package net.wynsolutions.bst;

import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bsc.addons.AddonHandler;
import net.wynsolutions.bst.command.TicketCommand;

public class BSTAddon extends Addon{

	private static int tempId;
	private static BSTAddon instance;
	
	@Override public void onDisable() {
		getLogger().info("Disabling BungeeSpider-Tickets(Client)");
		super.onDisable();
	}
	
	@Override public void onEnable() {
		instance = this;
		getLogger().info("Enabling BungeeSpider-Tickets(Client)");
		
		getHandler().registerCommand("ticket", new TicketCommand(getHandler()));
		this.setupConfig(getResourceAsStream("config.yml"));
		tempId = this.getConfig().getInt("temp-id");
		
		super.onEnable();
	}
	
	public static int getNewId(){
		tempId++;
		instance.getConfig().set("temp-id", tempId);
		instance.saveConfig();
		return tempId;
	}
	
	public static AddonHandler getAddonHandler(){
		return instance.getHandler();
	}
	
}
