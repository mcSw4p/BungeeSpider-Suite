package net.wynsolutions.bst.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.wynsolutions.bsc.addons.AddonHandler;

public class TicketCommand extends Command{
	
	private AddonHandler handler;
	
	public TicketCommand(AddonHandler hand) {
		super("ticket");
		this.handler = hand;
	}

	@Override public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
		
		if(arg0 instanceof Player){
			
			Player player = (Player) arg0;
			
			if(arg2.length > 0){
				//Args are greater than 0
				
				if(arg2[0].equalsIgnoreCase("create") || arg2[0].equalsIgnoreCase("cr") || arg2[0].equalsIgnoreCase("c")){
					//Create new ticket
					
					if(player.hasPermission("bst.ticket.create")){
						
					}
					
					
				}
				
			}else{
				//Display menu
			}
			
		}else{
			//Entered from console. Staff Ticket.
		}
		
		return false;
	}
}
