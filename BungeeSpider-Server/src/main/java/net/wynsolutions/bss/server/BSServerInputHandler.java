package net.wynsolutions.bss.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.wynsolutions.bss.BSSPluginLoader;
import net.wynsolutions.bss.email.Email;

public class BSServerInputHandler {

	private String input;
	private PrintWriter out;
	private BufferedReader in = null;
	
	private Socket clientSocket;
	
	public BSServerInputHandler(Socket par1) {
		
		this.clientSocket = par1;
		
		try {
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			this.input = in.readLine();

			this.handle();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	private void handle() throws IOException{
		
		// Track the server ip addresses and if a new one is trying to access this server
		//Prompt the user before adding to server list. Allow this "lock" to be enabled and disabled
		// in the server properties.
		
		
		if(input.equalsIgnoreCase("hello")){
			//Keepalive message - set server to active
			BSSPluginLoader.triggerActiveServer(in.readLine());
			
			System.out.println("Recieved hello command");
			
		}else if(input.equalsIgnoreCase("Fhello")){
			//Server Forced Keepalive message - set server to active
			String serverName = in.readLine();
			BSSPluginLoader.triggerActiveServer(serverName);
			BSSPluginLoader.instance.getProxy().getLogger().info("Server " + serverName + " has sent a forced update.");
			
			System.out.println("Recieved fhello command");
			
		}else if(input.equalsIgnoreCase("shutdown")){
			//Server Shutdown message - set server to inactive
			BSSPluginLoader.triggerInactiveServer(in.readLine());
			
			System.out.println("Recieved shutdown command");
			
		}else if(input.equalsIgnoreCase("activeload")){
			//Server overloaded message - set server to overloaded
		}else if(input.equalsIgnoreCase("message")){
			
			String to = in.readLine(), subject = in.readLine(), msg = in.readLine();
			new Email(subject, msg, to);
		}
		
		//Send the message back
		out.println(input);
		
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
