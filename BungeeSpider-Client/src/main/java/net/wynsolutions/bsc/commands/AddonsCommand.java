package net.wynsolutions.bsc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bsc.addons.AddonDescription;
import net.wynsolutions.bsc.api.BSC;
import net.wynsolutions.bsc.api.debug.Debug;
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
public class AddonsCommand extends Command{

	public AddonsCommand() {
		super("addon");
		BSC.addShortCut(BSC.createShortCut("adr", "bsc.shortcut.ads", "addon reload"));
		BSC.addShortCut(BSC.createShortCut("adrs", "bsc.shortcut.ads", "addon reload [arg0]"));
		BSC.addShortCut(BSC.createShortCut("adl", "bsc.shortcut.ads", "addon list"));
		
	}

	@Override public boolean execute(CommandSender sender, String label, String[] args) {
		if(label.equalsIgnoreCase("addon")){

			if(args.length > 0){

				if(args[0].equalsIgnoreCase("reload")){
					
					if(args.length < 2){
						// Not a specific addon
						if(sender.hasPermission("bsc.cmd.addons.reload")){
							sender.sendMessage(ChatColor.AQUA + "Reloading addons.");
							BSC.getHandler().reloadAddons();
							sender.sendMessage(ChatColor.GREEN + "Reload successful.");
							return true;
						}else{
							sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");
							return false;
						}
					}else{
						if(sender.hasPermission("bsc.cmd.addons.reload.single")){
							Debug.info("Trying to reload a single plugin.");
							String name = "";
							int c = 0;
							for(String s : args){
								if(c > 0 && c != args.length)
									name += s + " ";
								c++;
							}
							name = name.trim();
							Debug.info("Reloading addon \"" + name + "\".");
							Addon a = BSC.getHandler().getAddon(name);
							if(a == null)
								sender.sendMessage(ChatColor.RED + "There is no addon registered with that name!");
							else{
								sender.sendMessage(ChatColor.AQUA + "Reloading addon " + name + ".");
								BSC.getHandler().reloadAddon(BSC.getHandler().getAddon(name));
								sender.sendMessage(ChatColor.GREEN + "Reload successful.");
							}
							
							return true;
						}else{
							sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");
							return false;
						}
					}
					
				}else if(args[0].equalsIgnoreCase("list")){  
					
					if(sender.hasPermission("bsc.cmd.addons.list")){
						String str = ChatColor.AQUA + "Addons(" + BSC.getHandler().getAddonDescriptions().size() + "): " + ChatColor.GREEN;
						int i = 1;
						for(String a : BSC.getHandler().getAddonDescriptions().keySet()){
							if(i == BSC.getHandler().getAddonDescriptions().size()){
								str += a + ChatColor.GRAY + ".";
							}else{
								str += a + ChatColor.GRAY + ", " + ChatColor.GREEN;
							}
							i++;
						}
						sender.sendMessage(str);
					}

					
				}else{
					sender.sendMessage(ChatColor.RED + "Unreconized command!");
					sender.sendMessage(ChatColor.GOLD + "BungeeSpider-Client Addon Commands:");
					sender.sendMessage(ChatColor.GREEN + "- /addon reload <addon name> = Update this server to the main server.");
					return false;
				}


			}else{
				//Menu
				sender.sendMessage(ChatColor.GOLD + "BungeeSpider-Client Addon Commands:");
				sender.sendMessage(ChatColor.GREEN + "- /addon reload <addon name> = Update this server to the main server.");	
				return true;
			}

		}
		return false;
	}

}
