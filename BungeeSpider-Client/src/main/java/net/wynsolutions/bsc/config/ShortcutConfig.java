package net.wynsolutions.bsc.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
