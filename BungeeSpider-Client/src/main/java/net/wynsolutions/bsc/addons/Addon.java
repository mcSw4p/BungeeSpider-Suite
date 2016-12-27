package net.wynsolutions.bsc.addons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Logger;

import net.wynsolutions.bsc.BSCPluginLoader;
import net.wynsolutions.bsc.config.Configuration;
import net.wynsolutions.bsc.config.ConfigurationProvider;
import net.wynsolutions.bsc.config.YamlConfiguration;

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
	
	public void onEnable(){}

	public void onLoad()  {}

	public void onDisable(){}

	public AddonDescription getDescription() {
		return description;
	}

	public void setDescription(AddonDescription description) {
		this.description = description;
	}

	public Logger getLogger() {
		return logger;
	}

	@SuppressWarnings("resource")
	public InputStream getResourceAsStream(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException("Filename cannot be null");
		}

		try {
			URL url = new URLClassLoader (new URL[]{description.getFile().toURI().toURL()}).getResource(filename);

			if (url == null) {
				return null;
			}

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

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	public void saveConfig(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AddonHandler getHandler() {
		return handler;
	}
	
}
