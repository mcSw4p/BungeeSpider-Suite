package net.wynsolutions.bsc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bsc.debug.Debug;

public class AddonsCommand extends Command{

	public AddonsCommand() {
		super("addon");
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
