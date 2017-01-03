package net.wynsolutions.bsecon.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import net.wynsolutions.bsecon.BSCEconAddon;
import net.wynsolutions.bss.debug.Debug;
import net.wynsolutions.bss.server.event.BungeeSpiderListener;
import net.wynsolutions.bss.server.event.MessageRecieveEvent;

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
public class MessageListener implements BungeeSpiderListener{

	private BSCEconAddon plug;
	
	public MessageListener(BSCEconAddon par) {
		this.plug = par;
	}
	
	@Override public void messageRecieved(MessageRecieveEvent evt) {
		
		String input = evt.getSockInput();
		Debug.info("Recieved \"" + input + "\" command.");
		try {
			if(input.equalsIgnoreCase("econbal")){
				BufferedReader in = evt.getInput();
				PrintWriter out = evt.getOutput();
				String id = in.readLine();
				UUID uid = UUID.fromString(id);
				out.println(this.plug.getEconHandler().getPlayerBalance(uid));
				
				evt.setCanceled(true);
			}else if(input.equalsIgnoreCase("econbaladd")){
				BufferedReader in = evt.getInput();
				String id = in.readLine();
				UUID uid = UUID.fromString(id);
				double b = Double.parseDouble(in.readLine());
				this.plug.getEconHandler().addToPlayerBalance(uid, b);
				
				evt.setCanceled(true);
			}else if(input.equalsIgnoreCase("econbalsub")){
				BufferedReader in = evt.getInput();
				String id = in.readLine();
				UUID uid = UUID.fromString(id);
				double b = Double.parseDouble(in.readLine());
				this.plug.getEconHandler().removeFromPlayerBalance(uid, b);
				
				evt.setCanceled(true);
			}
		} catch (IOException e) {
			Debug.info(e.getMessage());
		}
		
	}

}
