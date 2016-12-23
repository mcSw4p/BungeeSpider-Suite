package net.wynsolutions.bss.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ServerPropertiesConfig {

	private File configFile;
	private static Configuration configuration;
	
	private String dataFolder;
	private int serverPort = 25566, serverTimeout = 35;
	private boolean logEmailToCon;
	
	private static boolean debug = true;
	
	public ServerPropertiesConfig(String dFolder) {
		
		configFile = new File(dFolder, "serverprops.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/serverprops.yml")) {
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
		
		this.setServerPort(configuration.getInt("serverPort"));
		this.setServerTimeout(configuration.getInt("serverTimeout"));
		this.setLogEmailToCon(configuration.getBoolean("logEmail"));
		this.setDebug(configuration.getBoolean("debug"));
		
	}
	
	public void saveConfig(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InputStream getResourceAsStream(String par1){
		return  getClass().getClassLoader().getResourceAsStream(par1);
	}
	
	public String getDataFolder() {
		return dataFolder;
	}

	public void setDataFolder(String dataFolder) {
		this.dataFolder = dataFolder;
	}

	public int getServerTimeout() {
		return serverTimeout;
	}

	public void setServerTimeout(int serverTimeout) {
		this.serverTimeout = serverTimeout;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public boolean isLogEmailToCon() {
		return logEmailToCon;
	}

	public void setLogEmailToCon(boolean logEmailToCon) {
		this.logEmailToCon = logEmailToCon;
	}

	public static boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		ServerPropertiesConfig.debug = debug;
	}
	
	public static Configuration getConfig(){
		return configuration;
	}
	
}
