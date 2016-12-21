package net.wynsolutions.bss.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.wynsolutions.bss.BSSPluginLoader;

public class BSSCommands extends Command{

	public BSSCommands() {
		super("bss");
	}

	@Override public void execute(CommandSender sender, String[] args) {
		
		if(args.length > 0){
			if(args[0].equalsIgnoreCase("list")){
				if(sender.hasPermission("bss.server.list") || !(sender instanceof ProxiedPlayer)){
					sender.sendMessage(new ComponentBuilder("Server List").color(ChatColor.GOLD).create());
					for(String s : BSSPluginLoader.serverStatus.keySet()){
						
						if(BSSPluginLoader.serverStatus.get(s))
							sender.sendMessage(new ComponentBuilder("- " + s + " : " + ChatColor.GOLD + BSSPluginLoader.serverStatus.get(s).toString()).color(ChatColor.GREEN).create());
						else
							sender.sendMessage(new ComponentBuilder("- " + s + " : " + ChatColor.GOLD + BSSPluginLoader.serverStatus.get(s).toString()).color(ChatColor.RED).create());
					}
				}else{
					//incorrect perms
					sender.sendMessage(new ComponentBuilder("You are not allowed to do that. Please contact the administrators.").color(ChatColor.RED).create());
				}
			}else if(args[0].equalsIgnoreCase("ping")){
				if(args.length >= 2){
					int i = 0;
					boolean flag = false;
					for(Byte b : args[1].getBytes()){
						if(i == 3 || i == 8){
							if(b != '.'){
								flag = true;
							}
						}
						
						i++;
						
					}
					
					if(flag){
						//Not ip
					}else{
						//Use ip
					}
					
					
				}else{
					sender.sendMessage(new ComponentBuilder("Correct usage - /bss ping [servername/ip].").color(ChatColor.RED).create());
				}
			}else if(args[0].equalsIgnoreCase("addip")){
				if(args.length == 2){
					
					//Add args[0] to ip list
					
				}else{
					sender.sendMessage(new ComponentBuilder("Correct usage - /bss addip [ip].").color(ChatColor.RED).create());
				}
			}
		}else{
			//Show menu of commands
			sender.sendMessage(new ComponentBuilder("BungeeSpider-Server Commands:").color(ChatColor.GOLD).create());
			sender.sendMessage(new ComponentBuilder("- /bss list = List all servers conneted and list statues.").color(ChatColor.GREEN).create());
			sender.sendMessage(new ComponentBuilder("- /bss addip [ip] = Add a trusted ip for servers.").color(ChatColor.GREEN).create());
			sender.sendMessage(new ComponentBuilder("- /bss ping [servername/ip] = Ping a single server to see if it\'s alive.").color(ChatColor.GREEN).create());
		}
		
	}

}
