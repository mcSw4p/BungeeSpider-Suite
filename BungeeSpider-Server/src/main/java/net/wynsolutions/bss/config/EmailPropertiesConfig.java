package net.wynsolutions.bss.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class EmailPropertiesConfig {

	private File configFile;
	private Configuration configuration;
	
	private static String emailuser, emailpass, hostname;
	private static int hostport = 25566;
	private static boolean ssl, tls;
	
	public EmailPropertiesConfig(String dFolder) {
		
		configFile = new File(dFolder, "emailprops.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("resources/emailprops.yml")) {
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
		
		this.setEmailuser(configuration.getString("username"));
		this.setEmailpass(configuration.getString("password"));
		this.setHostname(configuration.getString("host-name"));
		this.setHostport(configuration.getInt("host-port"));
		this.setSsl(configuration.getBoolean("ssl"));
		this.setTls(configuration.getBoolean("tls"));
		
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

	public static String getEmailuser() {
		return emailuser;
	}

	public void setEmailuser(String emailuser) {
		EmailPropertiesConfig.emailuser = emailuser;
	}

	public static String getEmailpass() {
		return emailpass;
	}

	public void setEmailpass(String emailpass) {
		EmailPropertiesConfig.emailpass = emailpass;
	}

	public static String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		EmailPropertiesConfig.hostname = hostname;
	}

	public static int getHostport() {
		return hostport;
	}

	public void setHostport(int hostport) {
		EmailPropertiesConfig.hostport = hostport;
	}

	public static boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		EmailPropertiesConfig.ssl = ssl;
	}

	public static boolean isTls() {
		return tls;
	}

	public void setTls(boolean tls) {
		EmailPropertiesConfig.tls = tls;
	}

	public Configuration getConfig(){
		return configuration;
	}
	
	
}
