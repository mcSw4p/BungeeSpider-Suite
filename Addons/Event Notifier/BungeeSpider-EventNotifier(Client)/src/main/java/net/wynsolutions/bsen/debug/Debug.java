package net.wynsolutions.bsen.debug;

import java.util.logging.Logger;
import net.wynsolutions.bsen.BSENAddon;

public class Debug {

	private static Logger logger = Logger.getLogger("BSEN Debug");
	
	public static void info(String msg){
		if(BSENAddon.isDebug())
			logger.info("[DEBUG] " + msg);
	}
	
	public static void warn(String msg){
		if(BSENAddon.isDebug())
			logger.warning("[DEBUG] " + msg);
	}
	
	public static void severe(String msg){
		if(BSENAddon.isDebug())
			logger.severe("[DEBUG] " + msg);
	}
	
}
