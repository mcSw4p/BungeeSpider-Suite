package net.wynsolutions.bsc.api;

import net.wynsolutions.bsc.BSCPluginLoader;
import net.wynsolutions.bsc.addons.AddonHandler;
import net.wynsolutions.bsc.config.ShortcutConfig;
import net.wynsolutions.bsc.database.DatabaseManager;
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
public class BSC {

	private static BSCPluginLoader plug;
	
	private static SchedulerAPI scheduler;
	private static ServerAPI serverProperties;
	private static BSCServerAPI serverBSCProperties;
	
	public BSC(BSCPluginLoader par1) {
		plug = par1;
		scheduler = new SchedulerAPI(par1);
		serverProperties = new ServerAPI(par1);
		serverBSCProperties = new BSCServerAPI(par1);
	}
	
	/**
	 * This is a scheduling class to keep track of tasks and allow you to cancel them
	 * by the addon for ease of use. 
	 * 
	 * @return The Scheduler API 
	 */
	public static SchedulerAPI getScheduler(){
		return scheduler;
	}
	
	/**
	 * This is a Database Management class to manage different types of databases and 
	 * condense them down to one API.
	 * 
	 * @return The Database API
	 */
	public static DatabaseManager getDatabase(){
		return plug.getDbManager();
	}
	
	/**
	 * This is a Addon Management class to manage addons on the BungeeSpider-Client.
	 * 
	 * @return The Addon API
	 */
	public static AddonHandler getHandler(){
		return plug.getAddonHandler();
	}
	
	/**
	 * This is a class to retrieve properties about the spigot/bukkit server currently 
	 * running on.
	 * @return The Server Properties
	 */
	public static ServerAPI getServerProperties(){
		return serverProperties;
	}
	
	/**
	 * This is a class to retrieve properties about the BSC server currently 
	 * running on.
	 * @return The BungeSpider-Client server Properties
	 */
	public static BSCServerAPI getBSCServerProperties(){
		return serverBSCProperties;
	}
	
	/**
	 * Sends @param to the server as commands to be handled by the input handlers.
	 * @param str
	 */
	public static void sendMessage(String... str){
		plug.sendMessage(str);
	}
	
	/**
	 * @param name
	 * @param permission
	 * @param command
	 * @return Creates a new shortcut instance that can be added to the list.
	 */
	public static Shortcut createShortCut(String name, String permission, String command){
		return new Shortcut(name, permission, command);
	}
	
	/**
	 * Adds a shortcut to the BungeeSpider-Client shortcuts list.
	 * 
	 * Will only add the Shortcut if one does not exist with the same name. You can call this
	 * in your onEnable() method.
	 * @param par
	 */
	public static void addShortCut(Shortcut par){
		if(!ShortcutConfig.getShortcutsNames().contains(par.getName()))
			ShortcutConfig.addShortcut(par);
	}
	
}
