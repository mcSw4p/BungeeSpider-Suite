package net.wynsolutions.bsc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.BSCPluginLoader;
import net.wynsolutions.bsc.api.debug.Debug;
import net.wynsolutions.bsc.shortcuts.Shortcut;
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
