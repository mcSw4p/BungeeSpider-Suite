package net.wynsolutions.bsecon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

import net.wynsolutions.bss.config.Configuration;
import net.wynsolutions.bss.config.ConfigurationProvider;
import net.wynsolutions.bss.config.YamlConfiguration;

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
public class EconomyHandler {

	private HashMap<UUID, Double> playerBalances = new HashMap<UUID, Double>();
	private File configFile;
	private Configuration configuration;
	private BSCEconAddon plug;
	
	private double initialBal = 20.0;
	
	public EconomyHandler(BSCEconAddon par) {
		this.plug = par;
		this.loadPlayerBalances();
	}
	
	public void loadPlayerBalances(){
		configFile = new File(this.plug.getDataFolder(), "economydb.yml");

        if (!configFile.exists()) {
            try (InputStream in = this.plug.getResourceAsStream("resources/economydb.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
			
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void savePlayerBalances(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePlayerBalance(UUID uid){
		
		if(!this.playerBalances.containsKey(uid)){
			this.playerBalances.put(uid, this.initialBal);
		}
		
		this.configuration.set(uid.toString(), this.playerBalances.get(uid));
		this.savePlayerBalances();
	}
	
	public void addToPlayerBalance(UUID uid, double bal){
		
		if(!this.playerBalances.containsKey(uid)){
			this.playerBalances.put(uid, this.initialBal);
		}
		
		double b = this.playerBalances.get(uid);
		b = (b + bal);
		this.playerBalances.replace(uid, b);
		this.updatePlayerBalance(uid);
	}
	
	public void removeFromPlayerBalance(UUID uid, double bal){
		
		if(!this.playerBalances.containsKey(uid)){
			this.playerBalances.put(uid, this.initialBal);
		}
		
		double b = this.playerBalances.get(uid);
		b = (b - bal);
		this.playerBalances.replace(uid, b);
		this.updatePlayerBalance(uid);
	}
	
	public double getPlayerBalance(UUID uid){
		
		if(!this.playerBalances.containsKey(uid)){
			this.playerBalances.put(uid, this.initialBal);
		}
		
		return this.playerBalances.get(uid);
	}
	
}
