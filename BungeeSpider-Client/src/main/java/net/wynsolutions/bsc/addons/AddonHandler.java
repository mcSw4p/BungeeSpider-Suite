package net.wynsolutions.bsc.addons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.google.common.base.Preconditions;

import net.wynsolutions.bsc.BSCPluginLoader;
import net.wynsolutions.bsc.api.debug.Debug;
import net.wynsolutions.bsc.config.Configuration;
import net.wynsolutions.bsc.config.ConfigurationProvider;
import net.wynsolutions.bsc.config.YamlConfiguration;
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
public class AddonHandler {

	private BSCPluginLoader plugin;
	private File addonsDir;

	private HashMap<String, AddonDescription> addonDescriptions = new HashMap<String, AddonDescription>();
	private HashMap<String, URLClassLoader> addonClassLoaders = new HashMap<String, URLClassLoader>();
	private List<Addon> addons = new ArrayList<Addon>();

	public AddonHandler(BSCPluginLoader plug) {
		this.plugin = plug;

		this.addonsDir = new File(plugin.getDataFolder().getPath() + File.separatorChar + "addons");

		this.scanForAddons(addonsDir);
	}

	/**
	 * Reload a single addon in the BungeeSpider-Client addons folder
	 * @param Addon instance
	 */
	public void reloadAddon(Addon par1){
		
		// Disable active addon
		System.out.println("[BSC] Disabled addon \"" + par1.getDescription().getName() + "\".");
		par1.onDisable();
		addons.remove(par1);
		addonDescriptions.remove(par1.getDescription().getName());
		try {
			addonClassLoaders.get(par1.getDescription().getName()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		addonClassLoaders.remove(par1.getDescription().getName());
		
		// Load new jar 
		AddonDescription desc = null;
		File file = new File(par1.getDescription().getFile().getAbsolutePath());
		try (JarFile jar = new JarFile(file)) {
			JarEntry pdf = jar.getJarEntry("spider.yml");
			Preconditions.checkNotNull(pdf, "Addon must have a spider.yml");

			try (InputStream in = jar.getInputStream(pdf)){
				Configuration con = ConfigurationProvider.getProvider(YamlConfiguration.class).load(in);
				desc = new AddonDescription();
				this.loadAddonDescriptionFile(con, desc);
				desc.setFile(file);
				Debug.info("[BSC] Found a valid addon with name \"" + desc.getName() + "\" v" + desc.getVersion() + " by " + desc.getAuthor() + ".");
				this.addonDescriptions.put(desc.getName(), desc);
			}
		} catch (Exception ex){
			System.out.println("Could not load addon from file " + file);
			desc = par1.getDescription();
		}
		
		// Activate new instance addon
		
		Addon newInstance = this.getPluginMainClass(desc);	
		newInstance.init(this, desc);
		newInstance.onLoad();
		System.out.println("[BSC] Starting addon \"" +desc.getName() + "\".");
		newInstance.onEnable();
		System.out.println("[BSC] Enabled addon \"" + desc.getName() + "\" v" + desc.getVersion() + " by " + desc.getAuthor() + ".");
		if(desc.getDescription() != null && !desc.getDescription().equals("")){
			System.out.println("[BSC] " + desc.getName() + ": " + desc.getDescription());
		}
	}

	/**
	 * Reload all addons in the BungeeSpider-Client addons folder
	 */
	public void reloadAddons(){
		this.disableAddons();
		this.scanForAddons(this.addonsDir);
	}

	/**
	 * Disable all addons in the BungeeSpider-Client addons folder
	 */
	public void disableAddons(){
		System.out.println("[+]Disabling addons[+]");
		for(AddonDescription description : addonDescriptions.values()){
			Addon instance = this.getPluginMainClass(description);	
			instance.onDisable();
			System.out.println("[BSC] Disabled addon \"" + description.getName() + "\".");
			try {
				addonClassLoaders.get(description.getName()).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			addonClassLoaders.remove(description.getName());
		}	
		addonDescriptions = new HashMap<String, AddonDescription>();
		addons = new ArrayList<Addon>();
	}

	/**
	 * Load all addons in the BungeeSpider-Client addons folder
	 */
	private void loadAddons(){
		System.out.println("[+]Starting addons[+]");
		for(AddonDescription description : addonDescriptions.values()){
			Addon instance = this.getPluginMainClass(description);
			instance.init(this, description);
			instance.onLoad();
			System.out.println("[BSC] Starting addon \"" + description.getName() + "\".");
			instance.onEnable();
			System.out.println("[BSC] Enabled addon \"" + description.getName() + "\" v" + description.getVersion() + " by " + description.getAuthor() + ".");
			if(description.getDescription() != null && !description.getDescription().equals("")){
				System.out.println("[BSC] " + description.getName() + ": " + description.getDescription());
			}
			addons.add(instance);
		}	
	}

	/**
	 * Scan for addons in the BungeeSpider-Client addons folder and load their Description file
	 * @param dir
	 */
	private void scanForAddons(File dir){

		/**
		 * @author SpigotMC devs
		 * @modified by Sw4p
		 */

		Debug.info("Scanning for addons...");
		Preconditions.checkNotNull(dir, "Addons Directory is null");
		Preconditions.checkArgument(dir.isDirectory(), "Addons folder path is not a directory!");

		for (File file : dir.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".jar")) {
				try (JarFile jar = new JarFile(file)) {
					JarEntry pdf = jar.getJarEntry("spider.yml");
					Preconditions.checkNotNull(pdf, "Addon must have a spider.yml");

					try (InputStream in = jar.getInputStream(pdf)){
						Configuration con = ConfigurationProvider.getProvider(YamlConfiguration.class).load(in);
						AddonDescription desc = new AddonDescription();
						this.loadAddonDescriptionFile(con, desc);
						desc.setFile(file);
						Debug.info("[BSC] Found a valid addon with name \"" + desc.getName() + "\" v" + desc.getVersion() + " by " + desc.getAuthor() + ".");
						this.addonDescriptions.put(desc.getName(), desc);
					}
				} catch (Exception ex){
					System.out.println("Could not load addon from file " + file);
					ex.printStackTrace();
				}
			}
		}
		this.loadAddons();
	}

	/**
	 * Gets an Addon instance from a Description file.
	 * @param desc
	 * @return The Addon instance for a Description file
	 */
	private Addon getPluginMainClass(AddonDescription desc){
		URLClassLoader child;
		try {
			child = new URLClassLoader (new URL[]{desc.getFile().toURI().toURL()}, this.getClass().getClassLoader());
			Preconditions.checkNotNull(child, "Could not create class loader.");
			Preconditions.checkNotNull(desc.getMain(), "Addon\'s Main class path has returned null.");
			@SuppressWarnings("unchecked")
			Class<? extends Addon> classToLoad = (Class<? extends Addon>) Class.forName (desc.getMain(), true, child);
			Preconditions.checkNotNull(classToLoad, "Class from addon returned null after loading.");
			Addon addon = (Addon)classToLoad.newInstance();
			Preconditions.checkNotNull(addon, "Class was misshandled. Parsing to class \'Addon\' returned null.");
			this.addonClassLoaders.put(desc.getName(), child);
			return addon;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Load a Description file with all variables
	 * @param con
	 * @param aDesc
	 * @return The Description file with all variables loaded
	 */
	private AddonDescription loadAddonDescriptionFile(Configuration con, AddonDescription aDesc){

		Preconditions.checkArgument(con.contains("name"), "Could not load addon\'s name.");
		aDesc.setName(con.getString("name"));
		
		Preconditions.checkArgument(con.contains("main"), "Could not load addon\'s main class.");
		aDesc.setMain(con.getString("main"));
		
		Preconditions.checkArgument(con.contains("version"), "Could not load addon\'s version.");
		aDesc.setVersion(con.getString("version"));
		
		Preconditions.checkArgument(con.contains("author"), "Could not load addon\'s author.");
		aDesc.setAuthor(con.getString("author"));
		
		if(con.contains("description"))
			aDesc.setDescription(con.getString("description"));

		return aDesc;
	}
	
	/**
	 * Get an addon by name
	 * @param name
	 * @return The Addon instance
	 */
	public Addon getAddon(String name){
		for(Addon a : this.addons){
			if(a.getDescription().getName().equalsIgnoreCase(name)){
				return a;
			}
		}
		return null;
	}

	/**
	 * Register a command with Bukkit
	 * @param name
	 * @param executor
	 */
	public void registerCommand(String name, final Command executor){
		Field bukkitCommandMap;
		try {
			bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			commandMap.register(name, executor);
		} catch (NoSuchFieldException | SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Disable a single addon in the BungeeSpider-Client addons folder
	 * @param a
	 */
	public void disableAddon(Addon a){
		a.onDisable();
		addons.remove(a);
		try {
			addonClassLoaders.get(a.getDescription().getName()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		addonClassLoaders.remove(a.getDescription().getName());
	}
	
	/**
	 * Enable a single addon in the BungeeSider-Client addons folder
	 * @param desc
	 */
	public void enableAddon(AddonDescription desc){
		Addon add = this.getPluginMainClass(desc);
		add.init(this, desc);
		add.onLoad();
		add.onEnable();
		addons.add(add);
	}
	
	/**
	 * Check is a addon exists with a name
	 * @param name
	 * @return true/false
	 */
	public boolean addonExists(String name){
		
		for(Addon a : this.addons){
			if(a.getDescription().getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Register a listener with Bukkit
	 * @param list
	 */
	public void registerListener(Listener list){
		this.plugin.getMCServer().getPluginManager().registerEvents(list, this.plugin);
	}
	
	/**
	 * Unregister a listener from Bukkit
	 * @param list
	 */
	public void unregisterListener(Listener list){
		HandlerList.unregisterAll(list);
	}

	/**
	 * @param name
	 * @return The player with the name @param
	 */
	public Player getPlayerByName(String name){
		return Bukkit.getPlayer(name);
	}
	
	/**
	 * @param id
	 * @return The player with the UUID @param
	 */
	public Player getPlayerByUUID(UUID id){
		return Bukkit.getPlayer(id);
	}

	/**
	 * @return The Server ip address
	 */
	public String getServerIp(){
		return plugin.getServerIP();
	}

	/**
	 * @return The Server port number
	 */
	public int getServerPort(){
		return plugin.getServerPort();
	}
	
	/**
	 * Install a addon from a direct URL to the BungeeSpider-Client addons folder
	 * @param url
	 */
	public void installAddon(final String url){
		new Thread(new Runnable(){
			public void run() {
				AddonDownloader addl = new AddonDownloader(url);
				if(addl.startInstallation()){
					System.out.println("Finished installing addon.");
				}else{
					System.out.println("There was an error while installing the addon. Maybe the URL is not direct?");
				}		
			}	
		}).start();
	}

	/**
	 * @return the addonDescriptions
	 */
	public HashMap<String, AddonDescription> getAddonDescriptions() {
		return addonDescriptions;
	}

}
