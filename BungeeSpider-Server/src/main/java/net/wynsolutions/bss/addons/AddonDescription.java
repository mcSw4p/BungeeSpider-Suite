package net.wynsolutions.bss.addons;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @author SpigotMc devs
 * @modified by Sw4p
 *
 */
public class AddonDescription {
	/**
     * Friendly name of the plugin.
     */
    private String name;
    
    /**
     * Plugin main class. Needs to extend {@link Plugin}.
     */
    private String main;
    
    /**
     * Plugin version.
     */
    private String version;
    
    /**
     * Plugin author.
     */
    private String author;
    
    /**
     * Addon server tag.
     */
    private String server;
    
    /**
     * Plugin hard dependencies.
     */
    private Set<String> depends = new HashSet<>();
    
    /**
     * Plugin soft dependencies.
     */
    private Set<String> softDepends = new HashSet<>();
    
    /**
     * File we were loaded from.
     */
    private File file = null;
    
    /**
     * Optional description.
     */
    private String description = null;
	
    /**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the main
	 */
	public String getMain() {
		return main;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the depends
	 */
	public Set<String> getDepends() {
		return depends;
	}

	/**
	 * @return the softDepends
	 */
	public Set<String> getSoftDepends() {
		return softDepends;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param main the main to set
	 */
	public void setMain(String main) {
		this.main = main;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param depends the depends to set
	 */
	public void setDepends(Set<String> depends) {
		this.depends = depends;
	}

	/**
	 * @param softDepends the softDepends to set
	 */
	public void setSoftDepends(Set<String> softDepends) {
		this.softDepends = softDepends;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}
}
