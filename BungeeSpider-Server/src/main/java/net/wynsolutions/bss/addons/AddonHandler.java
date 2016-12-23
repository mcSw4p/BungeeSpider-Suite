package net.wynsolutions.bss.addons;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.base.Preconditions;

import net.md_5.bungee.api.plugin.PluginClassloader;
import net.wynsolutions.bss.BSSLaunch;
import net.wynsolutions.bss.config.Configuration;
import net.wynsolutions.bss.config.ConfigurationProvider;
import net.wynsolutions.bss.config.YamlConfiguration;

public class AddonHandler {

	private BSSLaunch plugin;
	private File addonsDir;
	
	private HashMap<String, AddonDescription> addonDescriptions = new HashMap<String, AddonDescription>();
	private List<Addon> addons = new ArrayList<Addon>();
	
	public AddonHandler(BSSLaunch plug) {
		this.plugin = plug;
		
		this.addonsDir = new File(BSSLaunch.getDataFolder().getPath() + File.separatorChar + "addons");
		
        this.scanForAddons(addonsDir);
        this.enableAddons();
	}
	
	public void disableAddons(){
		for(AddonDescription description : addonDescriptions.values()){
			
			URLClassLoader loader;
			try {
				loader = new PluginClassloader( new URL[]
						{
								description.getFile().toURI().toURL()
						} );
				Class<?> main = loader.loadClass( description.getMain() );
				Addon clazz = (Addon) main.getDeclaredConstructor().newInstance();

				clazz.init(description);
				clazz.onDisable();
				
				addons.add(clazz);
				System.out.println("Disabling addon \"" + clazz.getDescription().getName() + "\".");
			} catch (MalformedURLException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void enableAddons(){
		for(AddonDescription description : addonDescriptions.values()){
			
			URLClassLoader loader;
			try {
				loader = new PluginClassloader( new URL[]
						{
								description.getFile().toURI().toURL()
						} );
				Class<?> main = loader.loadClass( description.getMain() );
				Addon clazz = (Addon) main.getDeclaredConstructor().newInstance();

				clazz.init(description);
				clazz.onEnable();
				
				addons.add(clazz);
				System.out.println("Enabling addon \"" + clazz.getDescription().getName() + "\".");
			} catch (MalformedURLException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void loadAddons(){
		for(AddonDescription description : addonDescriptions.values()){
			
			URLClassLoader loader;
			try {
				loader = new PluginClassloader( new URL[]
						{
								description.getFile().toURI().toURL()
						} );
				Class<?> main = loader.loadClass( description.getMain() );
				Addon clazz = (Addon) main.getDeclaredConstructor().newInstance();

				clazz.init(description);
				clazz.onLoad();
				
				addons.add(clazz);
				System.out.println("Starting addon \"" + clazz.getDescription().getName() + "\".");
			} catch (MalformedURLException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
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
                        System.out.println("[BSS] Found a valid addon with name \"" + desc.getName() + "\" v" + desc.getVersion() + " by " + desc.getAuthor() + ".");
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
	
	private AddonDescription loadAddonDescriptionFile(Configuration con, AddonDescription aDesc){
		
		if(con.contains("name")){
			aDesc.setName(con.getString("name"));
		}
		
		if(con.contains("main")){
			aDesc.setMain(con.getString("main"));
		}
		
		if(con.contains("version")){
			aDesc.setVersion(con.getString("version"));
		}
		
		if(con.contains("author")){
			aDesc.setAuthor(con.getString("author"));
		}	
		
		if(con.contains("description")){
			aDesc.setDescription(con.getString("description"));
		}
		
		
		return aDesc;
	}
	
	
}
