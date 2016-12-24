package net.wynsolutions.bsc.addons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
		this.setHandler(hand);
		this.setDescription(desc);
	}
	
	public void onEnable(){

	}

	public void onLoad()  {
		
	}

	public void onDisable(){

	}

	public AddonDescription getDescription() {
		return description;
	}

	public void setDescription(AddonDescription description) {
		this.description = description;
	}

	public Logger getLogger() {
		return logger;
	}

    public final InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
    
    public final File getDataFolder(){
    	return new File(BSCPluginLoader.instance.getDataFolder().getPath() + File.separatorChar + "addons" + File.separatorChar + description.getName());
    }
    
    public void setupConfig(){
    	if(configFile == null){
			configFile = new File(getDataFolder(), "config.yml");

	        if (!configFile.exists()) {
	        	try {
					configFile.createNewFile();
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

	public void setHandler(AddonHandler handler) {
		this.handler = handler;
	}
	
}
