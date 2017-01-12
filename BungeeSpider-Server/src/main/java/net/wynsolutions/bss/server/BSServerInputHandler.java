package net.wynsolutions.bss.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.wynsolutions.bss.BSSLaunch;
import net.wynsolutions.bss.config.IpTableConfig;
import net.wynsolutions.bss.config.RecipientsConfig;
import net.wynsolutions.bss.debug.Debug;
import net.wynsolutions.bss.email.Email;
import net.wynsolutions.bss.server.event.EventHandler;
import net.wynsolutions.bss.server.event.MessageRecieveEvent;

public class BSServerInputHandler {

	private String input;
	private PrintWriter out;
	private BufferedReader in = null;
	
	private Socket clientSocket;
	
	public BSServerInputHandler(Socket par1) {

		this.clientSocket = par1;
		
		try {
			this.handle();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	private void handle() throws IOException{
		
		String ip = this.clientSocket.getInetAddress().getHostAddress();
		
		if(IpTableConfig.getBlockedIps().contains(ip)){
			Debug.info("Client \'" + ip + "\' was blocked.");
			return;
		}
		
		if(!IpTableConfig.getAllowedIps().contains(ip)){
			if(IpTableConfig.isAllowUnrecognizedClients()){
				if(!BSServer.tempUnrecognizedIps.contains(ip)){
					System.out.println("Client with ip " + ip + " has connected and is not in the ip table.");
					BSServer.addUnrecognizedIp(ip);
				}else{
					Debug.info("Client unrecognized, ip: " + ip);
				}
			}else{
				return;
			}
		}
		Debug.info("Client \'" + ip + "\' made it past firewall.");
		
		this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.input = in.readLine();
		
		Debug.info("Handling client " + ip + ", input command: " + this.input);
		
		if(this.input == null){
			Debug.severe("Client \'" + ip + "\' sent a null subcommand!");
			this.clientSocket.close();
			return;
		}
		
		// Client was accepted, now handle the input
		
		if(input.equalsIgnoreCase("hello")){
			//Keepalive message - set server to active
			BSSLaunch.triggerActiveServer(in.readLine());
			
			Debug.info("Recieved hello command");
			
		}else if(input.equalsIgnoreCase("Fhello")){
			//Server Forced Keepalive message - set server to active
			String serverName = in.readLine();
			BSSLaunch.triggerActiveServer(serverName);
			
			Debug.info("Recieved fhello command");
			
		}else if(input.equalsIgnoreCase("shutdown")){
			//Server Shutdown message - set server to inactive
			BSSLaunch.triggerInactiveServer(in.readLine());
			
			Debug.info("Recieved shutdown command");
			
		}else if(input.equalsIgnoreCase("activeload")){
			//Server overloaded message - set server to overloaded
			
			Debug.info("Recieved activeload command");
			
		}else if(input.equalsIgnoreCase("message")){
			String to = in.readLine(), subject = in.readLine(), msg = in.readLine();
			new Email(subject, msg, to);
			
			Debug.info("Recieved message command");
		}else if(input.equalsIgnoreCase("gmessage")){
			String to = in.readLine(), subject = in.readLine(), msg = in.readLine();
			for(String s : RecipientsConfig.getRecipientGroup(to)){
				new Email(subject, msg, s);
			}
			Debug.info("Recieved gmessage command");
		}else if(input.equalsIgnoreCase("amessage")){
			String subject = in.readLine(), msg = in.readLine();
			for(String s : RecipientsConfig.getRecipientsEmails()){
				new Email(subject, msg, s);
			}
			Debug.info("Recieved amessage command");
		}else{
			// Add Event listener for addon hooks
			
			Debug.info("Firing Message recieve event.");
			MessageRecieveEvent event = EventHandler.fireMessageEvent(new MessageRecieveEvent(in, out, this.input, ip, clientSocket));
			if(event.isCanceled()){
				Debug.warn("Event Canceled by another addon.");
			}
		}
		
		//Send the message back
		out.println(input);
		
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// End of input handling
		
	}
	
}
