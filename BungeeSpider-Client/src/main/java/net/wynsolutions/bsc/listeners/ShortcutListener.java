package net.wynsolutions.bsc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.BSCPluginLoader;
import net.wynsolutions.bsc.debug.Debug;
import net.wynsolutions.bsc.shortcuts.Shortcut;

public class ShortcutListener implements Listener{
	
	private BSCPluginLoader plug;
	 
	public ShortcutListener(BSCPluginLoader par) {
		this.plug = par;
	}

	@EventHandler public void onChatPreProcess(AsyncPlayerChatEvent event){
		
		if(event.getMessage().startsWith("-")){
			event.setCancelled(true);
			String[] msgSplit = event.getMessage().split(" ");
			if(this.plug.getShortcutConfig().getShortcut(msgSplit[0].replace("-", "")) != null){
				Shortcut cut = this.plug.getShortcutConfig().getShortcut(msgSplit[0].replace("-", ""));
				String command = cut.getCommand();
				Debug.info("Handling shotcut command " + event.getMessage());
				if(command.contains("[arg0]")){
					String[] split = command.split(" ");
					int c = 0, i = 0;
					for(String s : split){
						Debug.info("Running through command message split " + s);
						if(s.startsWith("[arg")){
							command = command.replace("[arg" + i + "]", msgSplit[c].toString());
							Debug.info("Changing [arg" + i + "] to " + msgSplit[c].toString());
							i++;
						}
						c++;
					}
				}
				

				Debug.info("Handling shotcut command " + command);
				if(event.getPlayer().hasPermission(cut.getPermission()))
					event.getPlayer().performCommand(command);
				else
					event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to do that!");
			}else{
				event.getPlayer().sendMessage(ChatColor.RED + "Incorrect shortcut name.");
			}
		}
		
	}
	
}
