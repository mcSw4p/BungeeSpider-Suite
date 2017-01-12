package net.wynsolutions.bssh;

import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bsc.addons.Addon;
import net.wynsolutions.bssh.item.ShopItem;

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
public class BSSHAddon extends Addon{
	
	/*
	 * List all shop items in an inventory 
	 * Player clicks item. 
	 * Open inventory with item clicked, a purchase button, and a cancel button(go back to list)
	 * When player clicks purchase button the transaction is started and the player will go back to the list when done
	 * If transaction cannot be made then the item turns into red item and its lore = error message
	 * 
	 * ****
	 * Network Shop
	 * 
	 * Make the player wait for it to grab all the data
	 * Config will have a cache-update-threshold to determine if the client should cache and update only when it reaches its threshold or just pull from the server shop each time the 
	 *    player opens the shop. Doing this will require the clients to talk to each other or make the server push updates for the cache rather than the clients request them.
	 * 
	 * Perhaps for network shops, to get around all the caching issues, We delay players access to the shop 60 sec after they have opened it once. And only allow one player at a time
	 *    to connect from a single merchant. Multiple merchants for the network shops are allowed on a single server aswell. This helps limit the amount data flowing over the sockets
	 *    as we DO NOT want to stress them the amount that this addon could generate. 
	 *    
	 *    To do this and still have the server run well with other traffic from other addons and such, we need to keep in mind when creating the server addon to try and handle everything
	 *    distributed over multiple threads to help with the potential of huge shopping lists to multiple clients at once. Each needs to be handled separately and perhaps run the message back
	 *    in another thread aswell. 
	 * 
	 */
	
	private static List<ShopItem> localShopItems = new ArrayList<ShopItem>(), serverShopItems = new ArrayList<ShopItem>(), networkShopItems = new ArrayList<ShopItem>();

	@Override public void onDisable() {
		
		super.onDisable();
	}
	
	@Override public void onEnable() {
		
		super.onEnable();
	}

	/**
	 * @return the localShopItems
	 */
	public static List<ShopItem> getLocalShopItems() {
		return localShopItems;
	}

	/**
	 * @return the serverShopItems
	 */
	public static List<ShopItem> getServerShopItems() {
		return serverShopItems;
	}

	/**
	 * @return the networkShopItems
	 */
	public static List<ShopItem> getNetworkShopItems() {
		return networkShopItems;
	}
	
}
