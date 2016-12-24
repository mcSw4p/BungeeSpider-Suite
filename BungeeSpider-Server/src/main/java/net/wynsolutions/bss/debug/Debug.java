package net.wynsolutions.bss.debug;

import java.util.logging.Logger;

import net.wynsolutions.bss.config.ServerPropertiesConfig;

public class Debug {

	private static Logger logger = Logger.getLogger("BSS Debug");
	
	public static void info(String msg){
		if(ServerPropertiesConfig.isDebug())
			logger.info("[DEBUG] " + msg);
	}
	
	public static void warn(String msg){
		if(ServerPropertiesConfig.isDebug())
			logger.warning("[DEBUG] " + msg);
	}
	
	public static void severe(String msg){
		if(ServerPropertiesConfig.isDebug())
			logger.severe("[DEBUG] " + msg);
	}
	
}
