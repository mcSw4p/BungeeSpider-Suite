package net.wynsolutions.bsc.addons;

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
    
	public String getName() {
		return name;
	}
	
	public String getMain() {
		return main;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public Set<String> getDepends() {
		return depends;
	}
	
	public Set<String> getSoftDepends() {
		return softDepends;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file){
		this.file = file;
	}
	
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
}
