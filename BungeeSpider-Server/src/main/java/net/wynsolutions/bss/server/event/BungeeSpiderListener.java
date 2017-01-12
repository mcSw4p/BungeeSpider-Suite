package net.wynsolutions.bss.server.event;

import java.util.EventListener;

public interface BungeeSpiderListener extends EventListener{

	void messageRecieved(MessageRecieveEvent evt);
	
}
