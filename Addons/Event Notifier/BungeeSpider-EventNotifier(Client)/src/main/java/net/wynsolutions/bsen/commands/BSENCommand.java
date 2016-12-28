package net.wynsolutions.bsen.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsen.BSENAddon;
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
public class BSENCommand extends Command{

	private BSENAddon add; // Addon Instance
	
	public BSENCommand(BSENAddon par1) {
		super("bsen"); // Call parent method
		this.add = par1; // Initialize Addon instance
	}
	
	@Override
	public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
		
		if(arg2.equalsIgnoreCase("bsen")){ // Is the command equal to "BSEN"?
			if(arg3.length > 0){ // Is the argument length greater than 0?
				
				if(arg3[0].equalsIgnoreCase("memory") || arg3[0].equalsIgnoreCase("ram") || arg3[0].equalsIgnoreCase("mem")
						|| arg3[0].equalsIgnoreCase("r") || arg3[0].equalsIgnoreCase("m")){ // Does Argument 1 equal a RAM alias?
					
					if(arg0.hasPermission("bsen.check.ram")){ // Does the sender have the correct permissions?
						
						if(arg3.length == 1){ // Is the Argument length equal to 1?
							String ramUsage = ChatColor.AQUA + "" + this.add.getMemoryMonitor().getMemFreePercent() + ""; // Load Free RAM usage
							if(this.add.getMemoryMonitor().getMemFreePercent() >= this.add.getMemThreshold()) // Is free ram over Threshold?
								ramUsage = ChatColor.RED + "" + this.add.getMemoryMonitor().getMemFreePercent() + ""; // Set Free RAM usage to Color RED
							arg0.sendMessage(ChatColor.GREEN + "Current RAM usage is " + ramUsage + ChatColor.GREEN + " free."); // Send message for Free RAM
							return true; // Return method
						}else if((arg3[1].equalsIgnoreCase("all") || arg3[1].equalsIgnoreCase("a")) && arg3.length == 2){ // Is the Argument length equal to 2 and Does argument 2 equal "all" or "a"
							
							arg0.sendMessage(ChatColor.GREEN + "Current RAM usage/settings:"); // Send Command Title message
							
							String ramUsage = ChatColor.AQUA + "" + this.add.getMemoryMonitor().getMemFreePercent() + ""; // Load Free RAM usage
							if(this.add.getMemoryMonitor().getMemFreePercent() >= this.add.getMemThreshold()) // Is free ram over theshold?
								ramUsage = ChatColor.RED + "" + this.add.getMemoryMonitor().getMemFreePercent() + ""; // Set Free RAM usage to Color RED
							arg0.sendMessage(ChatColor.GREEN + "Current RAM usage is " + ramUsage + ChatColor.GREEN + " free."); // Send message for Free RAM
							arg0.sendMessage(ChatColor.GREEN + "Maximum RAM is " + ChatColor.AQUA + this.add.getMemoryMonitor().getMemMax() + ChatColor.GREEN + "."); // Send message for Max RAM
							arg0.sendMessage(ChatColor.GREEN + "Used RAM is " + ChatColor.AQUA + this.add.getMemoryMonitor().getMemUsed() + ChatColor.GREEN + "."); // Send message for used RAM
							String pastMemory = "["; // Initialize pastMemory String
							for(Double d : this.add.getMemoryMonitor().getPastMemory()){ // Loop through past RAM usages
								pastMemory += d+", "; // Add to pastMemory String
							}
							pastMemory +="]"; // End pastMemory String
							arg0.sendMessage(ChatColor.GREEN + "Past RAM usage is " + ChatColor.AQUA + pastMemory + ChatColor.GREEN + "."); // Send message for Past Memory usage
							return true; // Return method
						}else{
							arg0.sendMessage(ChatColor.RED + "Incorrect usage. Usage: /bsen r <all/a>"); // Send Incorrect Usage message
							return false; // Return method
						}
					}else{
						arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!"); // Send Incorrect Permissions message
						return false; // Return method
					}
					
				}else if(arg3[0].equalsIgnoreCase("playercount") || arg3[0].equalsIgnoreCase("pc") || arg3[0].equalsIgnoreCase("p")){ // Does Argument 1 equal a Player count alias?
					
					if(arg0.hasPermission("bsen.check.pc")){ // Does the sender have the correct permissions?
						if(arg3.length == 1){ // Is the Argument length equal to 1?
							String playerCount = ChatColor.AQUA + "" + this.add.getPlayerCountMonitor().getCurrentPlayerCount() + ""; // Load Player count 
							if(this.add.getPlayerCountMonitor().getCurrentPlayerCount() <= this.add.getPlayerCountThreshold()) // Is Player count over the Threshold?
								playerCount = ChatColor.RED + "" + this.add.getPlayerCountMonitor().getCurrentPlayerCount() + ""; // Set Player count to RED
							arg0.sendMessage(ChatColor.GREEN + "Current player count is " + playerCount + ChatColor.GREEN + "."); // Send message for Player count
							return true; // Return method
						}else if((arg3[1].equalsIgnoreCase("all") || arg3[1].equalsIgnoreCase("a")) && arg3.length == 2){ // Is the Argument length equal to 2 and Does argument 2 equal "all" or "a"
							
							arg0.sendMessage(ChatColor.GREEN + "Current player count/ settings:"); // Send Command Title message
							
							String playerCount = ChatColor.AQUA + "" + this.add.getPlayerCountMonitor().getCurrentPlayerCount() + ""; // Load Player count 
							if(this.add.getPlayerCountMonitor().getCurrentPlayerCount() <= this.add.getPlayerCountThreshold()) // Is Player count over the threshold?
								playerCount = ChatColor.RED + "" + this.add.getPlayerCountMonitor().getCurrentPlayerCount() + ""; // Set Player count to RED
							arg0.sendMessage(ChatColor.GREEN + "Current player count is " + playerCount + ChatColor.GREEN + "."); // Send message for Player count
							arg0.sendMessage(ChatColor.GREEN + "Maximum player count is " + ChatColor.AQUA + BSC.getMaxPlayers() + ChatColor.GREEN + "."); // Send message for Max Player count
							String pastMemory = "["; // Initialize pastMemory String
							for(Integer d : this.add.getPlayerCountMonitor().getPastPlayerCounts()){ // Loop through past Player counts
								pastMemory += d+", "; // Add to pastMemory String
							}
							pastMemory +="]"; // End pastMemory String
							arg0.sendMessage(ChatColor.GREEN + "Past player count is " + ChatColor.AQUA + pastMemory + ChatColor.GREEN + "."); // Send message for Past Player counts
							return true; // Return method
						}else{
							arg0.sendMessage(ChatColor.RED + "Incorrect usage. Usage: /bsen p <all/a>"); // Send Incorrect Usage message
							return false; // Return method
						}
					}else{
						arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!"); // Send Incorrect Permissions message
						return false; // Return method
					}
					
				}else if(arg3[0].equalsIgnoreCase("diskusage") || arg3[0].equalsIgnoreCase("disk") || arg3[0].equalsIgnoreCase("d")){ // Does Argument 1 equal a Disk usage alias?
					
					if(arg0.hasPermission("bsen.check.disk")){ // Does the Sender have the correct permissions?
						if(arg3.length == 1){ // Is the Argument length equal to 1?
							String diskUsage = ChatColor.AQUA + "" + this.add.getDiskUsageMonitor().getDiskFreePerecnt() + ""; // Load Disk usage
							if(this.add.getDiskUsageMonitor().getDiskFreePerecnt() <= this.add.getDiskUsageThreshold()) // Is Disk usage over the Threshold
								diskUsage = ChatColor.RED + "" + this.add.getDiskUsageMonitor().getDiskFreePerecnt() + ""; // Set Disk usage to color RED
							arg0.sendMessage(ChatColor.GREEN + "Current Disk usage is " + diskUsage + ChatColor.GREEN + " free."); // Send message for Disk usage
							return true; // Return method
						}else if((arg3[1].equalsIgnoreCase("all") || arg3[1].equalsIgnoreCase("a")) && arg3.length == 2){ // Is the Argument length equal to 2 and Does argument 2 equal "all" or "a"
							
							arg0.sendMessage(ChatColor.GREEN + "Current Disk usage:"); // Send Command Title message
							
							String diskUsage = ChatColor.AQUA + "" + this.add.getDiskUsageMonitor().getDiskFreePerecnt() + ""; // Load Disk usage
							if(this.add.getDiskUsageMonitor().getDiskFreePerecnt() <= this.add.getDiskUsageThreshold()) // Is Disk usage over the threshold
								diskUsage = ChatColor.RED + "" + this.add.getDiskUsageMonitor().getDiskFreePerecnt() + ""; // Set Disk usage to color RED
							arg0.sendMessage(ChatColor.GREEN + "Current Disk usage is " + diskUsage + ChatColor.GREEN + " free."); // Send message for Disk usage
							arg0.sendMessage(ChatColor.GREEN + "Maximum Disk space is " + this.add.getDiskUsageMonitor().getDiskMax() + ChatColor.GREEN + "."); // Send message for Total Disk space
							String pastMemory = "["; // Initialize pastMemory String
							for(Double d : this.add.getDiskUsageMonitor().getPastDiskUsage()){ // Loop through past Disk usages
								pastMemory += d+", "; // Add too pastMemory String
							}
							pastMemory +="]"; // End pastmemory String
							arg0.sendMessage(ChatColor.GREEN + "Past Disk usage is " + ChatColor.AQUA + pastMemory + ChatColor.GREEN + "."); // Send message for Past Disk usages
							return true; // Return method
						}else{
							arg0.sendMessage(ChatColor.RED + "Incorrect usage. Usage: /bsen d <all/a>"); // Send Incorrect Usage message
							return false; // Return method
						}	
					}else{
						arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!"); // Send Incorrect Permissions message
						return false; // Return method
					}
					
				}else if(arg3[0].equalsIgnoreCase("tps") || arg3[0].equalsIgnoreCase("t")){ // Does Argument 1 equal a TPS alias? 
					
					if(arg0.hasPermission("bsen.check.tps")){ // Does the Sender have the correct permissions?
						if(arg3.length == 1){ // Is the Argument length equal 1?
							
							String tps = ChatColor.AQUA + "" + this.add.getTpsMonitor().getTPS() + ""; // Load TPS
							if(this.add.getTpsMonitor().getTPS() <= this.add.getTpsThreshold()) // Is TPS over the Threshold?
								tps = ChatColor.RED + "" + this.add.getTpsMonitor().getTPS() + ""; // Set TPS to color RED
							arg0.sendMessage(ChatColor.GREEN + "Current TPS is " + tps + ChatColor.GREEN + "."); // Send message for TPS
							return true; // Return method
						}else{
							arg0.sendMessage(ChatColor.RED + "Incorrect usage. Usage: /bsen t <all/a>"); // Send Incorrect Usage message
							return false; // Return method
						}	
					}else{
						arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!"); // Send Incorrect Permissions message
						return false; // Return method
					}
					
				}
				
			}else{
				//Show Menu
				
				if(arg0.hasPermission("bsen.check.menu")){ // Does the sender have the correct permission?
					arg0.sendMessage(ChatColor.GREEN + "Event Notifier Commands:"); // Send Command Title message
					if(arg0.hasPermission("bsen.check.ram")){ // Does the sender have the correct permission?
						arg0.sendMessage(ChatColor.AQUA + "[+]" + ChatColor.GOLD + "/bsen [memory/ram/mem/r/m] <all/a>" + ChatColor.AQUA +  " - " + ChatColor.BLUE + 
								"Displays the current RAM usage."); // Send Ram usage command message
					}
					if(arg0.hasPermission("bsen.check.pc")){ // Does the sender have the correct permission?
						arg0.sendMessage(ChatColor.AQUA + "[+]" + ChatColor.GOLD + "/bsen [playercount/pc/p] <all/a>" + ChatColor.AQUA +  " - " + ChatColor.BLUE + 
								"Displays the current player count."); // Send Player count command message
					}
					if(arg0.hasPermission("bsen.check.disk")){ // Does the sender have the correct permission?
						arg0.sendMessage(ChatColor.AQUA + "[+]" + ChatColor.GOLD + "/bsen [diskusage/disk/d] <all/a>" + ChatColor.AQUA +  " - " + ChatColor.BLUE + 
								"Displays the current Disk usage."); // Send Disk usage command message
					}
					if(arg0.hasPermission("bsen.check.tps")){ // Does the sender have the correct permission?
						arg0.sendMessage(ChatColor.AQUA + "[+]" + ChatColor.GOLD + "/bsen [tps/t]" + ChatColor.AQUA +  " - " + ChatColor.BLUE + 
								"Displays the current TPS."); // Send TPS command message
					}
					return true; // Return method
				}else{
					arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!"); // Send Incorrect Permissions message
					return false; // Return method
				}
			}
		}
		
		return false; // Return method
	}

}
