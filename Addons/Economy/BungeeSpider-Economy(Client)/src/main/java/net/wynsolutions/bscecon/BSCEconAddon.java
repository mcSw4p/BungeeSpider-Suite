package net.wynsolutions.bscecon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bsc.addons.Addon;

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
public class BSCEconAddon extends Addon{

	@Override public void onEnable() {
		this.getLogger().info("[BSCEcon] Starting BungeeSpider-Economy(Client)");
		this.getHandler().registerCommand("econ", new EconomyCommand());
		super.onEnable();
	}
	
	@Override public void onDisable() {
		this.getLogger().info("[BSCEcon] Disabling BungeeSpider-Economy(Client)");
		super.onDisable();
	}
	
	public void getBalance(final CommandSender arg0, final UUID uid){
		new Thread(new Runnable(){

			public void run() {
				String response = null;
				try{
					Socket s = new Socket(BSC.getServerIp(), BSC.getServerPort());

					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

					s.setSoTimeout(15*1000);

					out.println("econbal");
					out.println(uid.toString());

					response = in.readLine();
					
					if(response == null){
						//Server must be unreachable
						getLogger().warning("--------------");
						getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
						getLogger().warning("--------------");
					}
					in.close();
					out.close();
					s.close();
				}catch(Exception ex){
					getLogger().warning("--------------");
					getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
					getLogger().warning("--------------");
				}
				arg0.sendMessage( ChatColor.GREEN + arg0.getName() + "\'s current balance is: " + ChatColor.RED + response);
			}
			
		}).start();
		
	}
	
	public void addBalance(final CommandSender arg0, final UUID uid, final double amt, final String[] arg2){
		new Thread(new Runnable(){

			public void run() {
				try{
					Socket s = new Socket(BSC.getServerIp(), BSC.getServerPort());

					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

					s.setSoTimeout(15*1000);

					out.println("econbaladd");
					out.println(uid.toString());
					out.println(String.valueOf(amt));

					String response = in.readLine();
					
					if(response == null){
						//Server must be unreachable
						getLogger().warning("--------------");
						getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
						getLogger().warning("--------------");
					}
					in.close();
					out.close();
					s.close();
				}catch(Exception ex){
					getLogger().warning("--------------");
					getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
					getLogger().warning("--------------");
				}
				arg0.sendMessage(ChatColor.GREEN + "Added " + arg2[2] + " to " + arg2[1] + "\'s account.");	
			}	
		}).start();	
	}
	
	public void removeBalance(final CommandSender arg0, final UUID uid, final double amt, final String[] arg2){
		
		new Thread(new Runnable(){

			public void run() {
				try{
					Socket s = new Socket(BSC.getServerIp(), BSC.getServerPort());

					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

					s.setSoTimeout(15*1000);

					out.println("econbalsub");
					out.println(uid.toString());
					out.println(String.valueOf(amt));

					String response = in.readLine();
					
					if(response == null){
						//Server must be unreachable
						getLogger().warning("--------------");
						getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
						getLogger().warning("--------------");
					}
					in.close();
					out.close();
					s.close();
				}catch(Exception ex){
					getLogger().warning("--------------");
					getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
					getLogger().warning("--------------");
				}
				arg0.sendMessage(ChatColor.GREEN + "Removed " + arg2[2] + " to " + arg2[1] + "\'s account.");	
			}
			
		}).start();
	}
	
	private class EconomyCommand extends Command{

		protected EconomyCommand() {
			super("econ");
		}

		@Override public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
			
			if(arg1.contentEquals("econ")){
				
				if(arg2.length > 0){
					
					if(arg2.length >= 1){
						if(arg2[0].equalsIgnoreCase("add")){
							if(arg2.length == 3){
								
								if(!arg0.hasPermission("bscecon.add")){
									arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!");
									return false;
								}
								
								Player p;
								double b;
								if((p = getHandler().getPlayerByName(arg2[1])) != null){
									try{ 
										b = Double.parseDouble(arg2[2]);
									}catch(NumberFormatException e){
										arg0.sendMessage(ChatColor.RED + "\"" + arg2[2] + "\" is not a number!");
										return false;
									}
									addBalance(arg0, p.getUniqueId(), b, arg2);
								}else{
									arg0.sendMessage(ChatColor.RED + "\"" + arg2[1] + "\" is not a Player!");
									return false;
								}
							}else{
								arg0.sendMessage(ChatColor.RED + "Incorrect usage! /econ add [playername] [amt]");
								return false;
							}
						}else if(arg2[0].equalsIgnoreCase("remove")){
							if(arg2.length == 3){
								
								if(!arg0.hasPermission("bscecon.remove")){
									arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!");
									return false;
								}
								
								Player p;
								double b;
								if((p = getHandler().getPlayerByName(arg2[1])) != null){
									try{
										b = Double.parseDouble(arg2[2]);
									}catch(NumberFormatException e){
										arg0.sendMessage(ChatColor.RED + "\"" + arg2[2] + "\" is not a number!");
										return false;
									}
									removeBalance(arg0, p.getUniqueId(), b, arg2);
								}else{
									arg0.sendMessage(ChatColor.RED + "\"" + arg2[1] + "\" is not a Player!");
									return false;
								}
							}else{
								arg0.sendMessage(ChatColor.RED + "Incorrect usage! /econ remove [playername] [amt]");
								return false;
							}
						}else if(arg2[0].equalsIgnoreCase("help")){
							
							arg0.sendMessage(ChatColor.GREEN + "Economy Commands:");
							arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ add [playername] [amount] - " + ChatColor.AQUA + "Adds the amount to the players balance.");
							arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ remove [playername] [amount] - " + ChatColor.AQUA + "Removes the amount from the players balance.");
							arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ [playername] - " + ChatColor.AQUA + "Views a players balance.");
							arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ - " + ChatColor.AQUA + "View your balance.");
							return true;
							
						}else{
							Player p;
							if((p = getHandler().getPlayerByName(arg2[0])) != null){
								
								if(!arg0.hasPermission("bscecon.view")){
									arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!");
									return false;
								}
								
								getBalance(arg0, p.getUniqueId());
								return true;
							}else{
								// Show menu
								arg0.sendMessage(ChatColor.GREEN + "Economy Commands:");
								arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ add [playername] [amount] - " + ChatColor.AQUA + "Adds the amount to the players balance.");
								arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ remove [playername] [amount] - " + ChatColor.AQUA + "Removes the amount from the players balance.");
								arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ [playername] - " + ChatColor.AQUA + "Views a players balance.");
								arg0.sendMessage(ChatColor.AQUA + "- " + ChatColor.GREEN + "/econ - " + ChatColor.AQUA + "View your balance.");
								return false;
							}
						}
					}
				}else{
					if(arg0.hasPermission("bscecon.balance")){
						Preconditions.checkArgument(arg0 instanceof Player, "Error, Cannot get balance if your not a player.");
						Player p = (Player) arg0;
						getBalance(arg0, p.getUniqueId());
						return true;
					}else{
						arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!");
						return false;
					}
				}
			}
			return false;
		}
		
	}
	
}
