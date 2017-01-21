package net.wynsolutions.bsc.api;

import net.wynsolutions.bsc.BSCPluginLoader;

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
public class BSCServerAPI {

	private BSCPluginLoader plug;
		
	public BSCServerAPI(BSCPluginLoader par1) {
		plug = par1;
	}
	
	/**
	 * @return The BungeeSpider Server ip as set in the BungeeSpider-Client settings file.
	 */
	public String getServerIp(){
		return plug.getServerIP();
	}
	
	/**
	 * @return The BungeeSpider Server port as set in the BungeeSpider-Client settings file.
	 */
	public int getServerPort(){
		return plug.getServerPort();
	}
	
	/**
	 * @return The server name as set in the BungeeSpider-Client settings file.
	 */
	public String getServerName(){
		return plug.getServerName();
	}
	
	/**
	 * @return The server timeout as set in the BungeeSpider-Client settings file.
	 */
	public int getServerTimeout(){
		return plug.getServerTimeout();
	}
	
}
