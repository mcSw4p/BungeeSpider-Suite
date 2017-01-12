package net.wynsolutions.bssh.item;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

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
public class ShopItem {

	private UUID playerOwner = null;
	private String serverOwner = null;
	
	private ItemStack itemStack, shopItemStack;
	private double price;
	private String description, name, category;
	
	/**
	 * @return the playerOwner
	 */
	public UUID getPlayerOwner() {
		return playerOwner;
	}
	
	/**
	 * @return the serverOwner
	 */
	public String getServerOwner() {
		return serverOwner;
	}
	
	/**
	 * @return the itemStack
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	/**
	 * @return the shopItemStack
	 */
	public ItemStack getShopItemStack() {
		return shopItemStack;
	}
	
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
}
