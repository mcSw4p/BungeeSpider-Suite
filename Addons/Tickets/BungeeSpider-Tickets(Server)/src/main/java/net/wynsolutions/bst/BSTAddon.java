package net.wynsolutions.bst;

import net.wynsolutions.bss.addons.Addon;
import net.wynsolutions.bss.server.event.EventHandler;
import net.wynsolutions.bst.listener.MessageListener;

public class BSTAddon extends Addon{

	private MessageListener msgListener;
	
	@Override public void onEnable() {
		getLogger().info("Enabling BungeeSpider-Tickets(Server)");
		
		this.msgListener = new MessageListener();
		EventHandler.addMessageEventListener(this.msgListener);
		
		this.setupConfig(getResourceAsStream("config.yml"));
		
		super.onEnable();
	}
	
	@Override public void onDisable() {
		getLogger().info("Disabling BungeeSpider-Tickets(Server)");
		
		EventHandler.removeMessageEventListener(this.msgListener);
		
		super.onDisable();
	}
	
}
