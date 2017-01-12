package net.wynsolutions.bssh;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

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
public class EntityUtils {
	
	/**
	 * Spawn Entity at location with no AI and Metadata
	 * @param loc
	 * @param type
	 * @param data
	 * @return
	 */
	public static Entity spawnEntityWithDataNoAI(Location loc, EntityType type, String data){
		Entity e = loc.getWorld().spawnEntity(loc, type);
		EntityUtils.toggleAI(e, false);
		e.setMetadata(data, null);
		return e;
	}
	
	/**
	 * Spawn Entity at location and no AI
	 * @param loc
	 * @param name
	 * @param type
	 * @return
	 */
	public static Entity spawnEntityNoAI(Location loc, EntityType type){
		Entity e = loc.getWorld().spawnEntity(loc, type);
		EntityUtils.toggleAI(e, false);
		return e;
	}

	/**
	 * Spawn Entity at location with name and no AI
	 * @param loc
	 * @param name
	 * @param type
	 * @return
	 */
	public static Entity spawnEntityNoAI(Location loc, String name, EntityType type){
		Entity e = loc.getWorld().spawnEntity(loc, type);
		e.setCustomName(name);
		EntityUtils.toggleAI(e, false);
		return e;
	}
	
	/**
	 * Spawn Entity at location 
	 * @param loc
	 * @param name
	 * @param type
	 * @return
	 */
	public static Entity spawnEntity(Location loc, EntityType type){
		Entity e = loc.getWorld().spawnEntity(loc, type);
		return e;
	}

	/**
	 * Spawn Entity at location with name
	 * @param loc
	 * @param name
	 * @param type
	 * @return
	 */
	public static Entity spawnEntity(Location loc, String name, EntityType type){
		Entity e = loc.getWorld().spawnEntity(loc, type);
		e.setCustomName(name);
		return e;
	}
	
	/**
	 * Spawns a villager at location with no AI.
	 * @param loc
	 * @param name
	 */
	public static Entity spawnVillagerNoAI(Location loc, String name){
		Entity v = loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		v.setCustomName(name);
		EntityUtils.toggleAI(v, false);
		return v;
	}
	
	/**
	 * Spawns a villager at location with AI.
	 * @param loc
	 * @param name
	 */
	public static Entity spawnVillager(Location loc, String name){
		Entity v = loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		v.setCustomName(name);
		return v;
	}
	
	/**
	 * Spawns a villager at location with no AI.
	 * @param loc
	 * @param name
	 */
	public static Entity spawnVillagerNoAI(Location loc){
		Entity v = loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		EntityUtils.toggleAI(v, false);
		return v;
	}
	
	/**
	 * Spawns a villager at location with AI.
	 * @param loc
	 * @param name
	 */
	public static Entity spawnVillager(Location loc){
		Entity v =loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		return v;
	}
	
	/**
	 * Toggles a Entity's AI via reflection.
	 * @param entity
	 * @param b
	 */
	public static void toggleAI(Entity entity, boolean b){
	    String serverVersion = null;
	    Method getHandle = null;
	    Method getNBTTag = null;
	    Class<?> nmsEntityClass = null;
	    Class<?> nbtTagClass = null;
	    Method c = null;
	    Method setInt = null;
	    Method f = null;
		try {
            if (serverVersion == null) {
                String name = Bukkit.getServer().getClass().getName();
                String[] parts = name.split("\\.");
                serverVersion = parts[3];
            }
            if (getHandle == null) {
                Class<?> craftEntity = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftEntity");
                getHandle = craftEntity.getDeclaredMethod("getHandle");
                getHandle.setAccessible(true);
            }
            Object nmsEntity = getHandle.invoke(entity);
            if (nmsEntityClass == null) {
                nmsEntityClass = Class.forName("net.minecraft.server." + serverVersion + ".Entity");
            }
            if (getNBTTag == null) {
                getNBTTag = nmsEntityClass.getDeclaredMethod("getNBTTag");
                getNBTTag.setAccessible(true);
            }
            Object tag = getNBTTag.invoke(nmsEntity);
            if (nbtTagClass == null) {
                nbtTagClass = Class.forName("net.minecraft.server." + serverVersion + ".NBTTagCompound");
            }
            if (tag == null) {
                tag = nbtTagClass.newInstance();
            }
            if (c == null) {
                c = nmsEntityClass.getDeclaredMethod("c", nbtTagClass);
                c.setAccessible(true);
            }
            c.invoke(nmsEntity, tag);
            if (setInt == null) {
                setInt = nbtTagClass.getDeclaredMethod("setInt", String.class, Integer.TYPE);
                setInt.setAccessible(true);
            }
            int value = b ? 0 : 1;
            setInt.invoke(tag, "NoAI", value);
            if (f == null) {
                f = nmsEntityClass.getDeclaredMethod("f", nbtTagClass);
                f.setAccessible(true);
            }
            f.invoke(nmsEntity, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
