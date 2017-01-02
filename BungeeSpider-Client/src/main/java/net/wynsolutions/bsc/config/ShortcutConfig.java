package net.wynsolutions.bsc.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bsc.shortcuts.Shortcut;

public class ShortcutConfig {
	private static File configFile;
	private static Configuration configuration;
	
	private static List<String> shortcutsNames = new ArrayList<String>();
	private static List<Shortcut> shortcuts = new ArrayList<Shortcut>();
	
	public ShortcutConfig(String dFolder) {
		
		configFile = new File(dFolder, "shortcuts.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/shortcuts.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
			
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String s : configuration.getKeys()){
			if(!shortcutsNames.contains(s)){
				shortcutsNames.add(s);
				shortcuts.add(new Shortcut(s, configuration.getString(s+".permission"), configuration.getString(s+".command")));
			}
		}
		
	}
	
	public void saveConfig(){
		
		for(Shortcut s : shortcuts){
			configuration.set(s.getName()+".permission", s.getPermission());
			configuration.set(s.getName()+".command", s.getCommand());
		}
		
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InputStream getResourceAsStream(String par1){
		return  getClass().getClassLoader().getResourceAsStream(par1);
	}

	/**
	 * @return the shortcutsNames
	 */
	public static List<String> getShortcutsNames() {
		return shortcutsNames;
	}

	/**
	 * @return the shortcuts
	 */
	public List<Shortcut> getShortcuts() {
		return shortcuts;
	}
	
	/**
	 * @param name
	 * @return a shortcut with the name in @param
	 */
	public Shortcut getShortcut(String name){
		for(Shortcut s : shortcuts){
			if(s.getName().equalsIgnoreCase(name)){
				return s;
			}
		}
		return null;
	}
	
	public static void addShortcut(Shortcut par){
		shortcuts.add(par);
		shortcutsNames.add(par.getName());
	}
}
