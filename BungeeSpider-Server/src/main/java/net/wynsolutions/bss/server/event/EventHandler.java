package net.wynsolutions.bss.server.event;

import java.util.ArrayList;
import java.util.List;

import net.wynsolutions.bss.debug.Debug;

public class EventHandler {
	private static List<BungeeSpiderListener> listeners = new ArrayList<BungeeSpiderListener>();

	public static void addMessageEventListener(BungeeSpiderListener listener) {
		listeners.add(listener);
	}

	public static void removeMessageEventListener(BungeeSpiderListener listener) {
		listeners.remove(listener);
	}

	public static MessageRecieveEvent fireMessageEvent(MessageRecieveEvent evt) {
		for(BungeeSpiderListener bsl : listeners){
			Debug.info("Running message through listener " + bsl.toString());
			bsl.messageRecieved(evt);
		}
		return evt;
	}

}
