package net.wynsolutions.bsecon;

import net.wynsolutions.bsecon.listeners.MessageListener;
import net.wynsolutions.bss.addons.Addon;

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
public class BSCEconAddon extends Addon{

	private MessageListener messageListener;
	private EconomyHandler econHandler;
	
	@Override public void onEnable() {
		this.getLogger().info("[BSCEcon] Starting BungeeSpider-Economy(Server)");
		this.setupConfig(this.getResourceAsStream("config.yml"));
		this.messageListener = new MessageListener(this);
		this.econHandler = new EconomyHandler(this);
		this.getHandler().addMessageListener(this.messageListener);
		super.onEnable();
	}
	
	@Override public void onDisable() {
		this.getLogger().info("[BSCEcon] Disabling BungeeSpider-Economy(Server)");
		this.getHandler().removeMessageListener(this.messageListener);
		this.econHandler.savePlayerBalances();
		super.onDisable();
	}

	/**
	 * @return the econHandler
	 */
	public EconomyHandler getEconHandler() {
		return econHandler;
	}
	
}
