package net.wynsolutions.bssh.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.BSC;
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
public class ItemPurchaceInventory implements Listener{

	private ItemStack itemPurchace, cancelItem, purchaceItem;
	private Player p;
	private Inventory inv, parent;
	
	public ItemPurchaceInventory(ShopItem item, Player player, Inventory parentInv) {
		this.itemPurchace = item.getShopItemStack();
		this.p = player;
		this.parent = parentInv;
		this.inv = Bukkit.createInventory(null, 36, "Purchase " + item.getItemStack().getItemMeta().getDisplayName());
		this.cancelItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
		this.purchaceItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
		ItemMeta meta1 = this.cancelItem.getItemMeta(), meta2 = this.purchaceItem.getItemMeta();
		meta1.setDisplayName(ChatColor.RED + "Cancel Transaction");
		List<String> lore = new ArrayList<String>();
		lore.add("Takes you back to previous.");
		meta1.setLore(lore);
		meta2.setDisplayName(ChatColor.GREEN + "Purchase Item");
		lore = new ArrayList<String>();
		lore.add("Puts the item in your inventory and take the price from your account.");
		meta2.setLore(lore);
		this.cancelItem.setItemMeta(meta1);
		this.purchaceItem.setItemMeta(meta2);
		this.inv.setItem(14, this.itemPurchace);
		this.inv.setItem(22, this.cancelItem);
		this.inv.setItem(24, this.purchaceItem);
		BSC.getHandler().registerListener(this);
	}
	
	public void openInventory(){
		this.p.openInventory(this.inv);
	}
	
	@EventHandler public void onPlayerClickInventory(InventoryClickEvent event){
		if(event.getClickedInventory().getName().equalsIgnoreCase(this.inv.getName())){
			event.setCancelled(true);
			if(event.getCurrentItem().getType() == this.cancelItem.getType()){
				this.p.openInventory(this.parent);
				BSC.getHandler().unregisterListener(this);
			}else if(event.getCurrentItem().getType() == this.purchaceItem.getType()){
				// buy item
				BSC.getHandler().unregisterListener(this);
			}
		}
	}
	
}
