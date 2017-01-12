package net.wynsolutions.bss.server.event;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.EventObject;

@SuppressWarnings("serial")
public class MessageRecieveEvent extends EventObject{

	private boolean canceled = false;
	private BufferedReader input;
	private PrintWriter output;
	private String clientIp, sockInput;
	private Socket client;
	
	public MessageRecieveEvent(BufferedReader io, PrintWriter out, String pInput, String parIp, Socket sock) {
		super(io);
		this.setInput(io);
		this.setClientIp(parIp);
		this.setClient(sock);
		this.setSockInput(pInput);
		this.setOutput(out);
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

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public String getSockInput() {
		return sockInput;
	}

	public void setSockInput(String sockInput) {
		this.sockInput = sockInput;
	}

	/**
	 * @return the output
	 */
	public PrintWriter getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(PrintWriter output) {
		this.output = output;
	}

}
