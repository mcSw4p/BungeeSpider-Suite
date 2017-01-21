package net.wynsolutions.bsc.api;

import java.io.File;

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
public class ServerAPI {

	private BSCPluginLoader plug;
	
	public ServerAPI(BSCPluginLoader par1) {
		plug = par1;
	}
	
	/**
	 * @return The Current player count on the server.
	 */
	public int getCurrentPlayerCount(){
		return plug.getServer().getOnlinePlayers().size();
	}
	
	/**
	 * @return The Max player count on the server.
	 */
	public int getMaxPlayers(){
		return plug.getServer().getMaxPlayers();
	}
	
	/**
	 * @return The Data folder of the BungeeSpider-Client plugin
	 */
	public File getDataFolder(){
		return plug.getDataFolder();
	}
	
}
