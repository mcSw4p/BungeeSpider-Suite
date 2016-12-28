package net.wynsolutions.bsen.debug;
/**
 * Copyright (C) 2017  Sw4p
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Sw4p
 *
 */
import java.util.logging.Logger;
import net.wynsolutions.bsen.BSENAddon;

public class Debug {

	private static Logger logger = Logger.getLogger("BSEN Debug"); // Load Logger
	
	/**
	 * Sends a message to the console as info.
	 * @param msg
	 */
	public static void info(String msg){
		if(BSENAddon.isDebug())
			logger.info("[DEBUG] " + msg);
	}
	
	/**
	 * Sends a message to the console as warning.
	 * @param msg
	 */
	public static void warn(String msg){
		if(BSENAddon.isDebug())
			logger.warning("[DEBUG] " + msg);
	}
	
	/**
	 * Sends a message to the console as severe.
	 * @param msg
	 */
	public static void severe(String msg){
		if(BSENAddon.isDebug())
			logger.severe("[DEBUG] " + msg);
	}
	
}
