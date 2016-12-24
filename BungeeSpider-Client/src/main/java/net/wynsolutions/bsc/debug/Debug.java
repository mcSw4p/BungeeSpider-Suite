package net.wynsolutions.bsc.debug;

import java.util.logging.Logger;

import net.wynsolutions.bsc.BSCPluginLoader;

public class Debug {

	private static Logger logger = Logger.getLogger("BSS Debug");
	
	public static void info(String msg){
		if(BSCPluginLoader.isDebug())
			logger.info("[DEBUG] " + msg);
	}
	
	public static void warn(String msg){
		if(BSCPluginLoader.isDebug())
			logger.warning("[DEBUG] " + msg);
	}
	
	public static void severe(String msg){
		if(BSCPluginLoader.isDebug())
			logger.severe("[DEBUG] " + msg);
	}
	
}
