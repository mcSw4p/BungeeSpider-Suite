package net.wynsolutions.bst.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import net.wynsolutions.bss.server.event.BungeeSpiderListener;
import net.wynsolutions.bss.server.event.MessageRecieveEvent;
import net.wynsolutions.bst.ticket.Ticket;

public class MessageListener implements BungeeSpiderListener{

	@Override public void messageRecieved(MessageRecieveEvent evt) {

		String input = evt.getSockInput();
		try {
			if(input.equalsIgnoreCase("ticket")){
				BufferedReader in = evt.getInput();
				PrintWriter out = new PrintWriter(evt.getClient().getOutputStream(), true);
				//Handle ticket related messages over the server sockets
				new Ticket(in);
				
				out.println(input);
				evt.setCanceled(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
