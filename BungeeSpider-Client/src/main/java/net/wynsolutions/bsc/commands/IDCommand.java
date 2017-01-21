package net.wynsolutions.bsc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.wynsolutions.bsc.api.BSC;

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
public class IDCommand extends Command{

	public IDCommand() {
		super("id");
	}

	@Override public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
		
		if(arg1.equalsIgnoreCase("id")){
			
			if(!(arg0 instanceof Player)){
				arg0.sendMessage(ChatColor.GREEN + "Server\'s name is: " + ChatColor.AQUA + BSC.getBSCServerProperties().getServerName());
			}else{
				arg0.sendMessage(ChatColor.RED + "You are not allowed to do that!");
			}
			
		}
		
		return false;
	}

}
