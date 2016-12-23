package net.wynsolutions.bss.server.event;

import java.io.BufferedReader;
import java.util.EventObject;

@SuppressWarnings("serial")
public class MessageRecieveEvent extends EventObject{

	private boolean canceled = false;
	private BufferedReader input;
	private String clientIp;
	
	public MessageRecieveEvent(BufferedReader io, String parIp) {
		super(io);
		this.setInput(io);
		this.setClientIp(parIp);
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public BufferedReader getInput() {
		return input;
	}

	public void setInput(BufferedReader input) {
		this.input = input;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}
