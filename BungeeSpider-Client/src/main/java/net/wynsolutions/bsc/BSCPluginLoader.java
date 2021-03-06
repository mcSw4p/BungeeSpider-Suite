package net.wynsolutions.bsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.addons.AddonDownloader;
import net.wynsolutions.bsc.addons.AddonHandler;
import net.wynsolutions.bsc.api.BSC;
import net.wynsolutions.bsc.api.debug.Debug;
import net.wynsolutions.bsc.commands.AddonsCommand;
import net.wynsolutions.bsc.commands.IDCommand;
import net.wynsolutions.bsc.config.ShortcutConfig;
import net.wynsolutions.bsc.database.DatabaseManager;
import net.wynsolutions.bsc.listeners.ShortcutListener;
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
public class BSCPluginLoader extends BSCPlugin{
	
	/*
	 * When finished with objects, set them to null so garbage collector know to get rid of them and allow more space for new 
	 * objects. This is not the most important thing in the world to do, from my testing it does just fine without it but 
	 * researching this further i find people tend to be split on if you should or shouldn't and i see no harm in doing it.
	 */

	private int serverPort, serverTimeout;
	private String serverIP, serverName;
	private static boolean debug = false;
	
	public static BSCPluginLoader instance;
	private AddonHandler addonHandler;
	private ShortcutConfig shortcutConfig;
	private DatabaseManager dbManager;
	
	@Override public void onEnable() { 

		instance = this;
		
		this.loadVars();

		new BSC(this);
		
		this.addonHandler = new AddonHandler(this); 
		
		this.getServer().getPluginManager().registerEvents(new ShortcutListener(this), this);
		BSC.getHandler().registerCommand("addon", new AddonsCommand());
		BSC.getHandler().registerCommand("id", new IDCommand());
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			public void run() {
				sendMessage("hello", serverName);
			}

		}, 0L, serverTimeout*20L);
		super.onEnable();
	}

	@Override public void onDisable() {
		this.sendMessage("shutdown", serverName);
		this.shortcutConfig.saveConfig();
		this.addonHandler.disableAddons();
		super.onDisable();
	}

	private void loadVars(){

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		//Default Config variables
		this.serverIP = this.getConfig().getString("server.ip");
		this.serverPort = this.getConfig().getInt("server.port");
		this.serverName = this.getConfig().getString("server.name");
		this.serverTimeout = this.getConfig().getInt("server.timeout");
		setDebug(this.getConfig().getBoolean("debug"));
		
		//Database Manager
		if(this.getConfig().getBoolean("database.enabled")){
			System.out.println("Starting connection to database with DatabaseManager. This could take a few seconds...");
			this.dbManager = new DatabaseManager(this.getConfig());
			Debug.info("Finished connection to database.");
		}else{
			Debug.info("Skipping database connection because it\'s disabled.");
		}

		
		this.shortcutConfig = new ShortcutConfig(getDataFolder().getPath());
		
		File f = new File(getDataFolder().getPath() + File.separatorChar + "addons");
		if(!f.exists()){
			f.mkdirs();
		}

	}

	@Override public boolean onCommand(final CommandSender sender, Command command, String label, final String[] args) {

		if(label.equalsIgnoreCase("bsc")){

			if(args.length > 0){

				if(args[0].equalsIgnoreCase("update")){
					if(sender.hasPermission("bsc.cmd.update")){
						sender.sendMessage(ChatColor.GOLD + "Sending update ping to server.");

						if(this.sendMessage("Fhello", serverName)){
							sender.sendMessage(ChatColor.GREEN + "Server was updated.");
						}else{
							sender.sendMessage(ChatColor.RED + "Could not connect to server!");
						}
						return true;
					}else{
						sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");
						return false;
					}
					

				}else if(args[0].equalsIgnoreCase("shortcuts") || args[0].equalsIgnoreCase("sc")){
					if(sender.hasPermission("bsc.cmd.shortcuts")){
						sender.sendMessage(ChatColor.GREEN + "List of shortcuts:");
						String str = "[";
						int i = 0;
						for(String s : ShortcutConfig.getShortcutsNames()){
							if(i == (ShortcutConfig.getShortcutsNames().size() - 1))
								str += ChatColor.GOLD + s + ChatColor.AQUA +  "]";
							else
								str += ChatColor.GOLD + s + ChatColor.AQUA +  "], [";
							i++;
						}
						sender.sendMessage(ChatColor.AQUA + str);
						return true;
					}else{
						sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");
						return false;
					}
				}else if(args[0].equalsIgnoreCase("install-addon") || args[0].equalsIgnoreCase("iadn")){
					
					if(sender.hasPermission("bsc.cmd.installaddon")){
						if(args.length == 2){
							// Just url
							if(args[1].startsWith("http://")){
								new Thread(new Runnable(){

									public void run() {
										AddonDownloader addl = new AddonDownloader(args[1]);
										if(addl.startInstallation()){
											sender.sendMessage(ChatColor.GREEN + "Finished installing addon.");
										}else{
											sender.sendMessage(ChatColor.RED + "There was an error while installing the addon. Maybe the URL is not direct?");
										}		
									}	
								}).start();
								
							}else{
								sender.sendMessage(ChatColor.RED + "Correct usage - /bss iadn [url].");
							}
						}else{
							sender.sendMessage(ChatColor.RED + "Correct usage - /bss iadn [url].");
						}

					}else{
						sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");
					}	
					
				}else if(args[0].equalsIgnoreCase("file-install-addon") || args[0].equalsIgnoreCase("fiadn")){
					
					if(sender.hasPermission("bsc.cmd.installaddon")){
						if(args.length == 2){
							final File f = new File(this.getDataFolder().getPath() + File.separatorChar + args[1]);
							
							if(!f.getParentFile().exists()){
								f.getParentFile().mkdir();
							}
							
							if(!args[1].endsWith(".bsadi")){
								sender.sendMessage(ChatColor.RED + "File \"" + f.getAbsolutePath() + "\" does not seem to be a install file.");
								return false;
							}
							
							if(f.exists()){
								
								new Thread(new Runnable(){

									public void run() {
										new AddonDownloader(f);
										sender.sendMessage(ChatColor.GREEN + "Finished installing addons.");	
									}	
								}).start();
								return true;
							}else{
								sender.sendMessage(ChatColor.RED + "File \"" + f.getAbsolutePath() + "\" does not exist!");
								return false;
							}
						}
					}
					
				}else{
					sender.sendMessage(ChatColor.RED + "Unreconized command!");
					sender.sendMessage(ChatColor.GOLD + "BungeeSpider-Client Commands:");
					sender.sendMessage(ChatColor.GREEN + "- /bsc [update] = Update this server to the main server.");	
					sender.sendMessage(ChatColor.GREEN + "- /bsc [shortcuts/sc] = List all shortcut names.");	
					sender.sendMessage(ChatColor.GREEN + "- /bsc [install-addon/iadn] [url]= List all shortcut names.");	
					return false;
				}


			}else{
				//Menu
				sender.sendMessage(ChatColor.GOLD + "BungeeSpider-Client Commands:");
				sender.sendMessage(ChatColor.GREEN + "- /bsc [update] = Update this server to the main server.");	
				sender.sendMessage(ChatColor.GREEN + "- /bsc [shortcuts/sc] = List all shortcut names.");	
				sender.sendMessage(ChatColor.GREEN + "- /bsc [install-addon/iadn] [url]= List all shortcut names.");	
				return true;
			}

		}

		return super.onCommand(sender, command, label, args);
	}

	public boolean sendMessage(String... str){
		try{
			Socket s = new Socket(serverIP, serverPort);

			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			s.setSoTimeout(15*1000);
			
			for(String s1 : str){
				out.println(s1);
			}

			String response = in.readLine();

			if(response == null){
				//Server must be unreachable
				this.getLogger().warning("--------------");
				this.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
				this.getLogger().warning("--------------");
			}

			s.close();
			return true;
		}catch(Exception ex){
			this.getLogger().warning("--------------");
			this.getLogger().warning("[BSC] Did not get response from the server! Check your settings or make sure the server is running.");
			this.getLogger().warning("--------------");
		}
		return false;
	}

	public String getServerIP(){
		return serverIP;
	}

	public int getServerPort(){
		return serverPort;
	}

	public String getServerName(){
		return serverName;
	}

	public int getServerTimeout(){
		return serverTimeout;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		BSCPluginLoader.debug = debug;
	}
	
	public Server getMCServer(){
		return this.getServer();
	}

	/**
	 * @return the addonHandler
	 */
	public AddonHandler getAddonHandler() {
		return addonHandler;
	}

	/**
	 * @return the shortcutConfig
	 */
	public ShortcutConfig getShortcutConfig() {
		return shortcutConfig;
	}

	/**
	 * @return the dbManager
	 */
	public DatabaseManager getDbManager() {
		return dbManager;
	}

}
