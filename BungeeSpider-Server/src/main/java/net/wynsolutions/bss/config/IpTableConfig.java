package net.wynsolutions.bss.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class IpTableConfig {

	private static File configFile;
	private static Configuration configuration;
	
	private static List<String> allowedIps = new ArrayList<String>(), blockedIps = new ArrayList<String>();
	private static boolean allowUnrecognizedClients;
	
	public IpTableConfig(String dFolder) {
		
		configFile = new File(dFolder, "ip_table.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/ip_table.yml")) {
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
		
		setAllowedIps(configuration.getStringList("allow"));
		setBlockedIps(configuration.getStringList("block"));
		setAllowUnrecognizedClients(configuration.getBoolean("unrecognized-ip-allow"));
		
	}
	
	public static void saveConfig(){
		configuration.set("allow", getAllowedIps());
		configuration.set("block", getBlockedIps());
		configuration.set("unrecognized-ip-allow", isAllowUnrecognizedClients());
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InputStream getResourceAsStream(String par1){
		return  getClass().getClassLoader().getResourceAsStream(par1);
	}

	public static boolean isAllowUnrecognizedClients() {
		return allowUnrecognizedClients;
	}

	public static void setAllowUnrecognizedClients(boolean allowUnrecognizedClients) {
		IpTableConfig.allowUnrecognizedClients = allowUnrecognizedClients;
	}

	public static List<String> getAllowedIps() {
		return allowedIps;
	}

	public static void setAllowedIps(List<String> allowedIps) {
		IpTableConfig.allowedIps = allowedIps;
	}

	public static List<String> getBlockedIps() {
		return blockedIps;
	}

	public static void setBlockedIps(List<String> blockedIps) {
		IpTableConfig.blockedIps = blockedIps;
	}
	
	
}
