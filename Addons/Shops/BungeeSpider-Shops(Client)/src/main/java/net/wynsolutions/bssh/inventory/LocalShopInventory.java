package net.wynsolutions.bssh.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.wynsolutions.bsc.BSC;
import net.wynsolutions.bssh.BSSHAddon;
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
public class LocalShopInventory implements Listener{

	private String cat;
	private Player p;
	private Inventory inv;
	
	private List<ShopItem> shopList = new ArrayList<ShopItem>();
	
	public LocalShopInventory(Player player, String catPar) {
		
		this.p = player;
		this.cat = catPar;
		
		for(ShopItem si : BSSHAddon.getLocalShopItems())
			if(si.getCategory().equalsIgnoreCase(this.cat))
				this.shopList.add(si);
		
		int invSize = 36;
		
		if(this.shopList.size() > 36){
			// Need to add pages
		}else if(this.shopList.size() <= 27){
			if(this.shopList.size() <= 18){
				if(this.shopList.size() <= 9){
					// is 9
					invSize = 9;
				}else{
					// is 18
					invSize = 18;
				}
			}else{
				//is 27
				invSize = 27;
			}
		}
		
		this.inv = Bukkit.createInventory(null, invSize, "Local " + this.cat + " Shop " + invSize + "/" + this.shopList.size());
		
		for(int i = 0; i != invSize; i++){
			this.inv.addItem(this.shopList.get(i).getShopItemStack());
		}
		BSC.getHandler().registerListener(this);
	}
	
	public void openInventory(){
		this.p.openInventory(this.inv);
	}
	
	@EventHandler public void onPlayerClick(InventoryClickEvent event){
		
		if(event.getInventory().getName().equalsIgnoreCase(this.inv.getName())){
			
			for(ShopItem si : BSSHAddon.getLocalShopItems()){
				if(si.getShopItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())){
					
					ItemPurchaceInventory ipi = new ItemPurchaceInventory(si, (Player)event.getWhoClicked(), this.inv);
					ipi.openInventory();
					BSC.getHandler().unregisterListener(this);
					break;
				}
			}
			
		}
		
	}
	
	
}
