package net.wynsolutions.bss.addons;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.base.Preconditions;

import net.wynsolutions.bss.BSSLaunch;
import net.wynsolutions.bss.config.Configuration;
import net.wynsolutions.bss.config.ConfigurationProvider;
import net.wynsolutions.bss.config.YamlConfiguration;
import net.wynsolutions.bss.debug.Debug;

public class AddonHandler {

	private File addonsDir;

	private HashMap<String, AddonDescription> addonDescriptions = new HashMap<String, AddonDescription>();
	private List<Addon> addons = new ArrayList<Addon>();

	public AddonHandler() {

		this.addonsDir = new File(BSSLaunch.getDataFolder().getPath() + File.separatorChar + "addons");

		this.scanForAddons(addonsDir);
	}

	public void reloadAddon(Addon par1){
		Addon instance = this.getPluginMainClass(par1.getDescription());	
		instance.onDisable();
		System.out.println("[BSS] Disabled addon \"" + par1.getDescription().getName() + "\".");
		instance.onLoad();
		System.out.println("[BSS] Starting addon \"" + par1.getDescription().getName() + "\".");
		instance.onEnable();
		System.out.println("[BSS] Enabled addon \"" + par1.getDescription().getName() + "\" v" + par1.getDescription().getVersion() + " by " + par1.getDescription().getAuthor() + ".");
		if(par1.getDescription().getDescription() != null && !par1.getDescription().getDescription().equals("")){
			System.out.println("[BSS] " + par1.getDescription().getName() + ": " + par1.getDescription().getDescription());
		}
	}

	public void reloadAddons(){
		this.disableAddons();
		this.loadAddons();
	}

	public void disableAddons(){
		Debug.info("[+]Disabling addons[+]");
		for(AddonDescription description : addonDescriptions.values()){
			Addon instance = this.getPluginMainClass(description);	
			instance.onDisable();
			System.out.println("[BSS] Disabled addon \"" + description.getName() + "\".");
			addons.remove(instance);
		}	
	}

	private void loadAddons(){
		Debug.info("[+]Starting addons[+]");
		for(AddonDescription description : addonDescriptions.values()){
			Addon instance = this.getPluginMainClass(description);
			instance.init(this, description);
			instance.onLoad();
			System.out.println("[BSS] Starting addon \"" + description.getName() + "\".");
			instance.onEnable();
			System.out.println("[BSS] Enabled addon \"" + description.getName() + "\" v" + description.getVersion() + " by " + description.getAuthor() + ".");
			if(description.getDescription() != null && !description.getDescription().equals("")){
				System.out.println("[BSS] " + description.getName() + ": " + description.getDescription());
			}
			addons.add(instance);
		}	
	}

	private void scanForAddons(File dir){

		/**
		 * @author SpigotMC devs
		 * @modified by Sw4p
		 */

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
						Debug.info("[BSS] Found a valid addon with name \"" + desc.getName() + "\" v" + desc.getVersion() + " by " + desc.getAuthor() + ".");
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

	public Addon getAddon(String name){
		for(Addon a : this.addons){
			if(a.getDescription().getName().equalsIgnoreCase(name)){
				return a;
			}
		}
		return null;
	}

	public int getServerPort(){
		return BSSLaunch.instance.getServerPort();
	}

}
