package net.wynsolutions.bsc.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.wynsolutions.bsc.BSCPluginLoader;
import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bsc.addons.AddonDescription;
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
public class SchedulerAPI {
	
	private BSCPluginLoader plug;
	
	private HashMap<String, Integer> tasks = new HashMap<String, Integer>();
	
	public SchedulerAPI(BSCPluginLoader par1) {
		plug = par1;
	}
	
	/**
	 * Run a task from the main thread.
	 * @param run
	 */
	public void scheduleSyncTask(Addon add, Runnable run){
		int id = plug.getServer().getScheduler().scheduleSyncDelayedTask(plug, run);
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding scheduled delayed task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a delayed task from the main thread.
	 * @param run
	 * @param delay
	 */
	public void scheduleSyncTask(Addon add, Runnable run, long delay){
		int id = plug.getServer().getScheduler().scheduleSyncDelayedTask(plug, run, delay);
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding scheduled delayed(" + (delay/20) +"sec) task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a task Asynchronously.
	 * @param run
	 */
	public void scheduleAsyncTask(Addon add, Runnable run){
		int id = plug.getServer().getScheduler().runTaskLaterAsynchronously(plug, run, 0L).getTaskId();
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding Async scheduled delayed task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a delayed task Asynchronously.
	 * @param run
	 * @param delay
	 */
	public void scheduleAsyncTask(Addon add, Runnable run, long delay){
		int id = plug.getServer().getScheduler().runTaskLaterAsynchronously(plug, run, delay).getTaskId();
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding Async scheduled delayed(" + (delay/20) + "sec) task for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task from the main thread.
	 * @param run
	 * @param reDelay
	 */
	public void scheduleSyncRepeatingTask(Addon add, Runnable run, long reDelay){
		int id = plug.getServer().getScheduler().scheduleSyncRepeatingTask(plug, run, 0L, reDelay);
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding scheduled repeating task every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task with a delay from the main thread.
	 * @param run
	 * @param delay
	 * @param reDelay
	 */
	public void scheduleSyncRepeatingTask(Addon add, Runnable run, long delay, long reDelay){
		int id = plug.getServer().getScheduler().scheduleSyncRepeatingTask(plug, run, delay, reDelay);
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding scheduled repeating task(" + (delay/20) + "sec) every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task Asynchronously.
	 * @param run
	 * @param reDelay
	 */
	public void scheduleAsyncRepeatingTask(Addon add, Runnable run, long reDelay){
		int id = plug.getServer().getScheduler().runTaskTimer(plug, run, 0L, reDelay).getTaskId();
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding Async scheduled repeating task every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Run a repeating task with a delay Asynchronously.
	 * @param run
	 * @param delay
	 * @param reDelay
	 */
	public void scheduleAsyncRepeatingTask(Addon add, Runnable run, long delay, long reDelay){	
		int id = plug.getServer().getScheduler().runTaskTimer(plug, run, delay, reDelay).getTaskId();
		tasks.put(add.getDescription().getName() + id, id);
		Debug.info("Adding Async scheduled repeating task(" + (delay/20) + "sec) every " + (reDelay/20) + "sec. for addon \"" + add.getDescription().getName() + "\".");
	}
	
	/**
	 * Cancel all tasks related to a Addon.
	 * @param add
	 */
	public void cancelAllTasksForAddon(String addStr){
		
		AddonDescription desc = null;
		
		List<String> ints = new ArrayList<String>();
		
		for(String s : tasks.keySet()){
			if(desc == null){
			for(AddonDescription ad : plug.getAddonHandler().getAddonDescriptions().values()){
				if(ad.getName().equalsIgnoreCase(addStr)){
					desc = ad;
				}
			}
			}
			if(s.startsWith(desc.getName())){
				int id = tasks.get(s);
				ints.add(s);
				Debug.info("Removing task #" + id + " for addon \"" + addStr + "\".");
				plug.getServer().getScheduler().cancelTask(tasks.get(s));

			}
		}
		
		for(String i : ints){
			tasks.remove(i);
		}
		
	}

}
