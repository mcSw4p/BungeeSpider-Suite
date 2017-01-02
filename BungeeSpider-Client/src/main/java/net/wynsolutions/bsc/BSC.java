package net.wynsolutions.bsc;

import java.util.HashMap;

import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bsc.addons.AddonDescription;
import net.wynsolutions.bsc.addons.AddonHandler;
import net.wynsolutions.bsc.config.ShortcutConfig;
import net.wynsolutions.bsc.debug.Debug;
import net.wynsolutions.bsc.shortcuts.Shortcut;

public class BSC {

	private static BSCPluginLoader plug;
	
	private static HashMap<String, Integer> tasks = new HashMap<String, Integer>();
	
	public BSC(BSCPluginLoader par1) {
		plug = par1;
	}
	
	/**
	 * Sends @param to the server as commands to be handled by the input handlers.
	 * @param str
	 */
	public static void sendMessage(String... str){
		plug.sendMessage(str);
	}
	
	/**
	 * @return The BungeeSpider Server ip as set in the BungeeSpider-Client settings file.
	 */
	public static String getServerIp(){
		return plug.getServerIP();
	}
	
	/**
	 * @return The BungeeSpider Server port as set in the BungeeSpider-Client settings file.
	 */
	public static int getServerPort(){
		return plug.getServerPort();
	}
	
	/**
	 * @return The Current player count on the server.
	 */
	public static int getCurrentPlayerCount(){
		return plug.getServer().getOnlinePlayers().size();
	}
	
	/**
	 * @return The Max player count on the server.
	 */
	public static int getMaxPlayers(){
		return plug.getServer().getMaxPlayers();
	}
	
	/**
	 * @return The server name as set in the BungeeSpider-Client settings file.
	 */
	public static String getServerName(){
		return plug.getServerName();
	}
	
	/**
	 * @return The server timeout as set in the BungeeSpider-Client settings file.
	 */
	public static int getServerTimeout(){
		return plug.getServerTimeout();
	}
	
	/**
	 * @return The Addon's handler for handling the Addon's on the BungeeSpider-Client.
	 */
	public static AddonHandler getHandler(){
		return plug.getAddonHandler();
	}
	
	/*
	 * Schedule Methods 
	 */
	
	/**
	 * Run a task from the main thread.
	 * @param run
	 */
	public static void scheduleSyncTask(Addon add, Runnable run){
		tasks.put(add.getDescription().getName(), plug.getServer().getScheduler().scheduleSyncDelayedTask(plug, run));
		Debug.info("Adding scheduled delayed task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a delayed task from the main thread.
	 * @param run
	 * @param delay
	 */
	public static void scheduleSyncTask(Addon add, Runnable run, long delay){
		tasks.put(add.getDescription().getName(),plug.getServer().getScheduler().scheduleSyncDelayedTask(plug, run, delay));
		Debug.info("Adding scheduled delayed(" + (delay/20) +"sec) task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a task Asynchronously.
	 * @param run
	 */
	public static void scheduleAsyncTask(Addon add, Runnable run){
		tasks.put(add.getDescription().getName(),plug.getServer().getScheduler().runTaskLaterAsynchronously(plug, run, 0L).getTaskId());
		Debug.info("Adding Async scheduled delayed task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a delayed task Asynchronously.
	 * @param run
	 * @param delay
	 */
	public static void scheduleAsyncTask(Addon add, Runnable run, long delay){
		tasks.put(add.getDescription().getName(),plug.getServer().getScheduler().runTaskLaterAsynchronously(plug, run, delay).getTaskId());
		Debug.info("Adding Async scheduled delayed(" + (delay/20) + "sec) task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task from the main thread.
	 * @param run
	 * @param reDelay
	 */
	public static void scheduleSyncRepeatingTask(Addon add, Runnable run, long reDelay){
		tasks.put(add.getDescription().getName(), plug.getServer().getScheduler().scheduleSyncRepeatingTask(plug, run, 0L, reDelay));
		Debug.info("Adding scheduled repeating task every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task with a delay from the main thread.
	 * @param run
	 * @param delay
	 * @param reDelay
	 */
	public static void scheduleSyncRepeatingTask(Addon add, Runnable run, long delay, long reDelay){
		tasks.put(add.getDescription().getName(), plug.getServer().getScheduler().scheduleSyncRepeatingTask(plug, run, delay, reDelay));
		Debug.info("Adding scheduled repeating task(" + (delay/20) + "sec) every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task Asynchronously.
	 * @param run
	 * @param reDelay
	 */
	public static void scheduleAsyncRepeatingTask(Addon add, Runnable run, long reDelay){
		tasks.put(add.getDescription().getName(), plug.getServer().getScheduler().runTaskTimer(plug, run, 0L, reDelay).getTaskId());
		Debug.info("Adding Async scheduled repeating task every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task with a delay Asynchronously.
	 * @param run
	 * @param delay
	 * @param reDelay
	 */
	public static void scheduleAsyncRepeatingTask(Addon add, Runnable run, long delay, long reDelay){
		tasks.put(add.getDescription().getName(), plug.getServer().getScheduler().runTaskTimer(plug, run, delay, reDelay).getTaskId());
		Debug.info("Adding Async scheduled repeating task(" + (delay/20) + "sec) every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Cancel all tasks related to a Addon.
	 * @param add
	 */
	public static void cancelAllTaskForAddon(String addStr){
		
		AddonDescription desc = null;
		
		for(String s : tasks.keySet()){
			if(desc == null){
			for(AddonDescription ad : plug.getAddonHandler().getAddonDescriptions().values()){
				if(ad.getName().equalsIgnoreCase(addStr)){
					desc = ad;
				}
			}
			}
			if(s.equalsIgnoreCase(desc.getName())){
				plug.getServer().getScheduler().cancelTask(tasks.get(s));
				tasks.remove(s);
			}
		}
	}
	
	/*
	 * End of Schedule Methods
	 */
	
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
	 * @param par
	 */
	public static void addShortCut(Shortcut par){
		if(!ShortcutConfig.getShortcutsNames().contains(par.getName()))
			ShortcutConfig.addShortcut(par);
	}
	
}
