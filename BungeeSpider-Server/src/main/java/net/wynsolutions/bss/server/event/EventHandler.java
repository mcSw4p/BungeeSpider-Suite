package net.wynsolutions.bss.server.event;

import javax.swing.event.EventListenerList;

public class EventHandler {

	protected static EventListenerList listenerList = new EventListenerList();

	public void addMessageEventListener(BungeeSpiderListener listener) {
		listenerList.add(BungeeSpiderListener.class, listener);
	}

	public void removeMessageEventListener(BungeeSpiderListener listener) {
		listenerList.remove(BungeeSpiderListener.class, listener);
	}

	public static MessageRecieveEvent fireMessageEvent(MessageRecieveEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i+2) {
			if (listeners[i] == BungeeSpiderListener.class) {
				((BungeeSpiderListener) listeners[i+1]).myEventOccurred(evt);
			}
		}
		return evt;
	}

}
