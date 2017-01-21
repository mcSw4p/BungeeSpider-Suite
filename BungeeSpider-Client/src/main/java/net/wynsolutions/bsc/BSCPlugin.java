package net.wynsolutions.bsc;

import org.bukkit.plugin.java.JavaPlugin;
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
public class BSCPlugin extends JavaPlugin{
	
	@Override public void onDisable() {
		this.getServer().getLogger().info("[BSC] BungeeSpider Client is disabling.");
		super.onDisable();
	}
	
	@Override public void onEnable() {
		this.getServer().getLogger().info("[BSC] BungeeSpider Client is enabling.");
		super.onEnable();
	}
	
	@Override public void onLoad() {
		this.getServer().getLogger().info("[BSC] BungeeSpider Client is loading...");
		super.onLoad();
	}
	
}
