package net.wynsolutions.bsc.addons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;

import net.wynsolutions.bsc.BSCPluginLoader;
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
public class Addon {
	
	private AddonDescription description;
	private Logger logger = Logger.getLogger(Addon.class.getName());
	private AddonHandler handler;
	
	private File configFile;
	private Configuration config;
	
	public void init(AddonHandler hand, AddonDescription desc){
		this.handler = hand;
		this.setDescription(desc);
	}

	@SuppressWarnings("resource")
	public InputStream getResourceAsStream(String filename) {
		
		Preconditions.checkNotNull(filename, "Cannot retrive file if file name is null!");
	
		try {
			URL url = new URLClassLoader (new URL[]{description.getFile().toURI().toURL()}).getResource(filename);

			Preconditions.checkNotNull(url, "Failed to retrive file. File does not exist [" + filename + "]");
			
			URLConnection connection = url.openConnection();
			connection.setUseCaches(false);
			return connection.getInputStream();
		} catch (IOException ex) {
			return null;
		}
	}

    public final File getDataFolder(){
    	return new File(BSCPluginLoader.instance.getDataFolder().getPath() + File.separatorChar + "addons" + File.separatorChar + description.getName());
    }
    
    public void setupConfig(InputStream in){
    	if(configFile == null){
    		
    		if(!getDataFolder().exists()){
    			getDataFolder().mkdir();
    		}
    		
			configFile = new File(getDataFolder().getAbsolutePath(), "config.yml");

	        if (!configFile.exists()) {
				  try {
					Files.copy(in, configFile.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
			try {
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
				
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	/**
	 * Called when the Addon is enabling.
	 */
	public void onEnable(){}

	/**
	 * Called when the Addon is loading.
	 */
	public void onLoad()  {}

	/**
	 * Called when the Addon is disabling.
	 */
	public void onDisable(){}
    
	/**
	 * @return Returns the Configuration.
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * Set the Configuration File.
	 * @param config
	 */
	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	/**
	 * Save the Configuration file.
	 */
	public void saveConfig(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the Addon Handler.
	 */
	public AddonHandler getHandler() {
		return handler;
	}
	
	/**
	 * @return Returns the Description File
	 */
	public AddonDescription getDescription() {
		return description;
	}

	/**
	 * Set the Description file.
	 * @param description
	 */
	public void setDescription(AddonDescription description) {
		this.description = description;
	}

	/**
	 * @return Returns the Addons Logger.
	 */
	public Logger getLogger() {
		return logger;
	}
}
